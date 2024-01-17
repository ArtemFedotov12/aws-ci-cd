package com.start.springlearningdemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFileReader {

  public InputStream readFileAsStream(String fileName) throws IOException, URISyntaxException {
    // get url from the classpath
    URL resourceUrl = getClass().getClassLoader().getResource(fileName);
    if (resourceUrl == null) {
      throw new IOException("File not found: " + fileName);
    }

    String filePath = Paths.get(resourceUrl.toURI()).toString();
    return Files.newInputStream(Paths.get(filePath));
  }
}
