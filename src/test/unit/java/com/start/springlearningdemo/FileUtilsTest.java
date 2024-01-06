package com.start.springlearningdemo;

import static org.junit.jupiter.api.Assertions.*;

import com.start.springlearningdemo.utils.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileUtilsTest {

  @Test
  void whenCreateFileChunkWithValidBufferThenSuccess(@TempDir final Path tempDir)
      throws IOException {
    byte[] buffer = "Hello, world!".getBytes();
    int length = buffer.length;

    final Path createdFile = FileUtils.createFileChunk(buffer, length, tempDir, 0);
    assertTrue(Files.exists(createdFile));
    assertArrayEquals(buffer, Files.readAllBytes(createdFile));
  }

  @Test
  void whenCreateFileChunkButTempDirDoNotExistThenThrowException(@TempDir final Path tempDir) {
    byte[] buffer = "Hello, world!".getBytes();
    int length = buffer.length;
    Path wrongPath = Path.of(tempDir.toAbsolutePath() + "/some");
    System.out.println(wrongPath);
    assertThrows(IOException.class, () -> FileUtils.createFileChunk(buffer, length, wrongPath, 0));
  }
}
