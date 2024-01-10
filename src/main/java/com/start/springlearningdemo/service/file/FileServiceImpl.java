package com.start.springlearningdemo.service.file;

import static com.start.springlearningdemo.constants.ApplicationConstants.*;

import com.start.springlearningdemo.service.model.FileSplitting;
import com.start.springlearningdemo.utils.FileUtils;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

  @Override
  public FileSplitting splitFileBySize(
      final InputStream inputStream, final int maxChunkSize, final Path targetDirectoryPath) {
    log.info("Start splitting file by chunks. Chunk size: {}", maxChunkSize);
    final FileSplitting result = new FileSplitting();
    List<Path> files;
    String encodedPath = null;
    try {
      log.info("Created temp directory for file chunks: {}", targetDirectoryPath.toAbsolutePath());
      encodedPath =
          URLEncoder.encode(
              targetDirectoryPath.toAbsolutePath().toString(), StandardCharsets.UTF_8);

      files = FileUtils.splitBySize(inputStream, maxChunkSize, targetDirectoryPath);
      log.info(
          "End splitting file by chunks. Temp directory: {}", targetDirectoryPath.toAbsolutePath());
    } catch (final Exception e) {
      log.error("Error occurred while file splitting. Error: {}", e.getMessage(), e);
      files = new ArrayList<>();
    }

    result.setFiles(files);
    result.setEncodedPathDirectory(encodedPath);
    return result;
  }
}
