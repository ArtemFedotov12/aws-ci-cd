package com.start.springlearningdemo.controller;

import com.start.springlearningdemo.service.FileMessageSender;
import com.start.springlearningdemo.service.FileService;
import com.start.springlearningdemo.service.model.FileSplitting;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileController {

  @Value("${file.chunk.max.size}")
  private String maxChunkSize;

  private final FileService fileService;
  private final FileMessageSender fileMessageSender;

  public FileController(final FileService fileService, final FileMessageSender fileMessageSender) {
    this.fileService = fileService;
    this.fileMessageSender = fileMessageSender;
  }

  @PostMapping("/file")
  void processFile(@RequestParam("file") MultipartFile file) throws IOException {
    final FileSplitting fileSplitting =
        fileService.splitFileBySize(file.getInputStream(), Integer.parseInt(maxChunkSize));
    fileMessageSender.sendFiles(fileSplitting.getEncodedPathDirectory(), fileSplitting.getFiles());
  }
}
