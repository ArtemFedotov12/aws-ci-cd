package com.start.springlearningdemo.service;

import com.start.springlearningdemo.service.model.FileSplitting;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileServiceIT extends BaseServiceIT {

  @Test
  void whenSplitFileByChunksThenSuccess(@TempDir Path tempDirectory)
      throws IOException, URISyntaxException {
    try (final InputStream inputStream =
        testFileReader.readFileAsStream("service/fileservice/file_to_be_splitted.json")) {
      FileSplitting fileSplitting = fileService.splitFileBySize(inputStream, 1024, tempDirectory);
      List<Path> actualFiles = fileSplitting.getFiles();
      Assertions.assertEquals(7, actualFiles.size());

      final String firstFileContent =
          IOUtils.toString(Files.newInputStream(actualFiles.get(0)), StandardCharsets.UTF_8);
      Assertions.assertTrue(
          firstFileContent.contains(
              "Anim esse fugiat non consequat adipisicing mollit officia reprehenderit voluptate irure."));

      final String lastFileContent =
          IOUtils.toString(Files.newInputStream(actualFiles.get(6)), StandardCharsets.UTF_8);
      Assertions.assertTrue(
          lastFileContent.contains("Hello, Elva Church! You have 1 unread messages."));
    }
  }
}
