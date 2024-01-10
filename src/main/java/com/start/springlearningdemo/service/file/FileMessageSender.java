package com.start.springlearningdemo.service.file;

import java.nio.file.Path;
import java.util.List;

public interface FileMessageSender {

  /**
   * @param key message key
   * @param files files to be sent to broker
   */
  void sendFiles(final String key, final List<Path> files);
}
