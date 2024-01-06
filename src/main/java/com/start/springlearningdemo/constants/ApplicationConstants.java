package com.start.springlearningdemo.constants;

public final class ApplicationConstants {

  private ApplicationConstants() {
    throw new java.lang.UnsupportedOperationException(
        "This is a utility class and cannot be instantiated");
  }

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String CURRENT_FILE_PART_HEADER = "currentFilePart";
  public static final String LAST_FILE_PART_HEADER = "lastFilePart";

  public static final String KAFKA_SECURITY_PROTOCOL_PROPERTY = "security.protocol";
  public static final String KAFKA_SASL_JAAS_CONFIG_PROPERTY = "sasl.jaas.config";
  public static final String KAFKA_SASL_MECHANISM_PROPERTY = "sasl.mechanism";

  public static final String JAVA_TEMP_DIR_PROPERTY = "java.io.tmpdir";
  public static final String SPLIT_PREFIX = "split";
  public static final String UNDERSCORE = "_";
  public static final String JSON_SUFFIX = ".json";
  public static final String FILE_SPLITTING_DIRECTORY_PREFIX = "file-split_";
  public static final String FILE_UNSPLIT_DIRECTORY_PREFIX = "file-unsplit_";
}
