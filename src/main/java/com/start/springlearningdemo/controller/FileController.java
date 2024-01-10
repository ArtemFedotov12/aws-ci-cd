package com.start.springlearningdemo.controller;

import com.start.springlearningdemo.service.file.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileController {

  private final FileProcessor fileService;

  public FileController(FileProcessor fileService) {
    this.fileService = fileService;
  }

  @PostMapping("/file")
  void processFile(@RequestParam("file") MultipartFile file) {
    fileService.processFile(file);
  }
}
