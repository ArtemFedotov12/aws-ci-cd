package com.start.springlearningdemo.service;

import com.start.springlearningdemo.service.model.FileSplitting;

import java.io.InputStream;

public interface FileService {

    /**
     *
     * @param inputStream source data
     * @param maxChunkSize chunk size
     * @return split files
     */
    FileSplitting splitFileBySize(final InputStream inputStream, final int maxChunkSize);
}
