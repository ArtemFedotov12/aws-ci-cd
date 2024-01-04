package com.start.springlearningdemo.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.start.springlearningdemo.constants.ApplicationConstants.*;

@UtilityClass
@Slf4j
public class FileUtils {

    /**
     * @param buffer            source byte array
     * @param length            The number of bytes to write from the beginning of the buffer.
     *                          This value should not exceed the buffer's length.
     * @param tempDirectoryPath The path to the directory where the file chunk will be created. The directory must exist.
     * @param splitPart         The part number of the chunk. This is used in naming the file and
     *                          helps in identifying the order of the chunks.
     * @return Path to the created file chunk.
     * @throws IOException IOException If an I/O error occurs writing to the file system
     */
    public static Path createFileChunk(final byte[] buffer,
                                       final int length,
                                       final Path tempDirectoryPath,
                                       final int splitPart) throws IOException {

        final Path tempFilePath = tempDirectoryPath.resolve(String.format("%s_%d_%s", SPLIT_PREFIX, splitPart, JSON_SUFFIX));
        final byte[] dataToWrite = Arrays.copyOf(buffer, length);
        Files.write(tempFilePath, dataToWrite, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        return tempFilePath;
    }

    /**
     *
     * @param largeFile input stream of the file to be split
     * @param maxChunkSize chunk size
     * @param tempDirectoryPath temp directory path
     * @return list of create file paths
     * @throws IOException IOException If an I/O error occurs writing to the file system
     */
    public static List<Path> splitBySize(final InputStream largeFile,
                                         final int maxChunkSize,
                                         final Path tempDirectoryPath) throws IOException {

        final List<Path> fileList = new ArrayList<>();

        try (final InputStream in = largeFile) {
            byte[] buffer = new byte[maxChunkSize];
            int dataRead = in.read(buffer);
            int splitPart = 0;

            while (dataRead > -1) {
                final Path fileChunk = createFileChunk(buffer, dataRead, tempDirectoryPath, splitPart);
                log.info("Created part: {}. File name: {}", splitPart, fileChunk.toAbsolutePath());
                fileList.add(fileChunk);
                dataRead = in.read(buffer);
                splitPart++;
            }

        }

        return fileList;
    }

    /**
     *
     * @param prefix prefix for temp directory
     * @return path of the temp directory
     * @throws IOException IOException If an I/O error occurs writing to the file system
     */
    public static Path createTempDirectory(final String prefix) throws IOException {
        final String tempDir = System.getProperty(JAVA_TEMP_DIR_PROPERTY);
        return Files.createTempDirectory(Path.of(tempDir), prefix + UUID.randomUUID());
    }

    /**
     * Join a list of files together into a single output file.
     *
     * @param list              List of files to be joined together
     * @param tempDirectoryPath Directory path where the output file should be located
     * @return Joined file
     * @throws IOException If an I/O error occurs
     */
    public static File join(final List<File> list, final Path tempDirectoryPath) throws IOException {
        final Path outputFile = Files.createTempFile(tempDirectoryPath, FILE_UNSPLIT_DIRECTORY_PREFIX, JSON_SUFFIX);

        try (final FileChannel outChannel = FileChannel.open(outputFile, StandardOpenOption.WRITE)) {
            for (final File file : list) {
                try (final FileChannel inChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                }
            }
        }

        return outputFile.toFile();
    }

}
