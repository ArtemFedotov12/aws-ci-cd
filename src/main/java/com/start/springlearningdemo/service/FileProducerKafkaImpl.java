package com.start.springlearningdemo.service;

import static com.start.springlearningdemo.constants.ApplicationConstants.CURRENT_FILE_PART_HEADER;
import static com.start.springlearningdemo.constants.ApplicationConstants.LAST_FILE_PART_HEADER;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileProducerKafkaImpl implements FileMessageSender {

  @Value("${kafka.file-splitting.topic}")
  private String topicName;

  private final KafkaTemplate<String, byte[]> kafkaTemplate;

  public FileProducerKafkaImpl(
      @Qualifier("createKafkaTemplate") final KafkaTemplate<String, byte[]> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendFiles(final String key, final List<Path> files) {
    if (files.isEmpty()) {
      return;
    }
    try {
      int currentFilePart = 0;
      int lastFilePart = files.size() - 1;

      for (Path filePath : files) {
        byte[] fileContent = Files.readAllBytes(filePath);

        final Message<byte[]> message =
            MessageBuilder.withPayload(fileContent)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, key)
                .setHeader(KafkaHeaders.RECEIVED_KEY, key)
                .setHeader(CURRENT_FILE_PART_HEADER, currentFilePart)
                .setHeader(LAST_FILE_PART_HEADER, lastFilePart)
                .build();
        kafkaTemplate.send(message);
        currentFilePart++;
      }

    } catch (final Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
