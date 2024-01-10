package com.start.springlearningdemo.service.file;

import static com.start.springlearningdemo.constants.ApplicationConstants.FILE_SPLITTING_DIRECTORY_PREFIX;

import com.start.springlearningdemo.exception.InternalServerException;
import com.start.springlearningdemo.service.model.FileSplitting;
import com.start.springlearningdemo.utils.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileProcessorImpl implements FileProcessor {

  @Value("${file.chunk.max.size}")
  private String maxChunkSize;

  private final FileService fileService;
  private final FileMessageSender fileMessageSender;

  public FileProcessorImpl(FileService fileService, FileMessageSender fileMessageSender) {
    this.fileService = fileService;
    this.fileMessageSender = fileMessageSender;
  }

  @Override
  public void processFile(MultipartFile file) {
    try {
      final Path tempDirectoryPath = FileUtils.createTempDirectory(FILE_SPLITTING_DIRECTORY_PREFIX);
      final FileSplitting fileSplitting =
          fileService.splitFileBySize(
              file.getInputStream(), Integer.parseInt(maxChunkSize), tempDirectoryPath);
      fileMessageSender.sendFiles(
          fileSplitting.getEncodedPathDirectory(), fileSplitting.getFiles());
    } catch (final IOException e) {
      throw new InternalServerException(e.getMessage(), e);
    }
  }
}
