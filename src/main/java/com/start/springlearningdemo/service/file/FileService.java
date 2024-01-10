package com.start.springlearningdemo.service.file;

import com.start.springlearningdemo.service.model.FileSplitting;
import java.io.InputStream;
import java.nio.file.Path;

public interface FileService {

  /**
   * @param inputStream source data
   * @param maxChunkSize chunk size in bytes
   * @param targetDirectoryPath path to the directory where all file chunks will be located
   * @return split files
   */
  FileSplitting splitFileBySize(
      final InputStream inputStream, final int maxChunkSize, final Path targetDirectoryPath);
}
