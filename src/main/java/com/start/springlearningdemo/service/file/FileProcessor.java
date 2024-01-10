package com.start.springlearningdemo.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessor {

  /**
   * Split file by parts, 1Kb each and send all file parts to kafka file splitting topic
   *
   * @param file uploaded file
   */
  void processFile(final MultipartFile file);
}
