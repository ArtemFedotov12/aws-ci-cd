package com.start.springlearningdemo.service;

import com.start.springlearningdemo.service.model.FileSplitting;
import com.start.springlearningdemo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.start.springlearningdemo.constants.ApplicationConstants.*;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public FileSplitting splitFileBySize(final InputStream inputStream, final int maxChunkSize) {
        log.info("Start splitting file by chunks. Chunk size: {}", maxChunkSize);
        final FileSplitting result = new FileSplitting();
        List<Path> files;
        String encodedPath = null;
        try {
            final Path tempDirectoryPath = FileUtils.createTempDirectory(FILE_SPLITTING_DIRECTORY_PREFIX);
            log.info("Created temp directory for file chunks: {}", tempDirectoryPath.toAbsolutePath());
            encodedPath = URLEncoder.encode(tempDirectoryPath.toAbsolutePath().toString(), StandardCharsets.UTF_8);

            files = FileUtils.splitBySize(inputStream, maxChunkSize, tempDirectoryPath);
            log.info("End splitting file by chunks. Temp directory: {}", tempDirectoryPath.toAbsolutePath());
        } catch (final Exception e) {
            log.error("Error occurred while file splitting. Error: {}", e.getMessage(), e);
            files = new ArrayList<>();
        }

        result.setFiles(files);
        result.setEncodedPathDirectory(encodedPath);
        return result;
    }

}
