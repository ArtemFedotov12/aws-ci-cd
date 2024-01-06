package com.start.springlearningdemo.service;

import com.start.springlearningdemo.model.FilePart;
import com.start.springlearningdemo.utils.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaFileConsumer {

  @Value("${kafka.file-splitting.topic}")
  private String topicName;

  @Value("${kafka.group.id}")
  private String groupId;

  // key - unique file name
  // value - list of file parts(names of the files stored in the temp directory)
  private final Map<String, List<FilePart>> fileParts = new HashMap<>();

  // key - unique file name
  // path - temp directory, where all parts of the file located
  private final Map<String, Path> tempDirecotryMap = new HashMap<>();

  @KafkaListener(topics = "${kafka.file-splitting.topic}", groupId = "${kafka.group.id}")
  public void consumeFileParts(
      @Payload byte[] fileContent,
      @Header(name = "currentFilePart") String currentFilePart,
      @Header(name = "lastFilePart") String lastFilePart,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      @Header(KafkaHeaders.RECEIVED_KEY) String key) {

    try {
      if (fileParts.containsKey(key)) {

        final Path tempDirectory = tempDirecotryMap.get(key);
        final File tempFile =
            Files.createTempFile(tempDirectory, "split_" + currentFilePart + "_", ".json").toFile();
        FileOutputStream fos = new FileOutputStream(tempFile);
        try (fos) {
          fos.write(fileContent);
        }

        fileParts
            .get(key)
            .add(new FilePart(tempFile.getAbsolutePath(), Integer.parseInt(currentFilePart)));

      } else {
        final Path tempDirectory = FileUtils.createTempDirectory("file-parts");
        final File tempFile =
            Files.createTempFile(tempDirectory, "split_" + currentFilePart + "_", ".json").toFile();
        FileOutputStream fos = new FileOutputStream(tempFile);
        try (fos) {
          fos.write(fileContent);
        }

        fileParts.put(
            key,
            new ArrayList<>(
                List.of(
                    new FilePart(tempFile.getAbsolutePath(), Integer.parseInt(currentFilePart)))));
        tempDirecotryMap.put(key, tempDirectory);
      }

      if (Objects.equals(currentFilePart, lastFilePart)) {
        log.info("Process file");
        List<File> fileList =
            fileParts
                .get(key)
                .stream()
                .sorted(Comparator.comparingInt(FilePart::getPartNumber))
                .map(filePart -> new File(filePart.getFileName()))
                .toList();
        FileUtils.join(fileList, tempDirecotryMap.get(key));
      }

    } catch (final Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
