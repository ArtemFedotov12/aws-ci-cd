package com.start.springlearningdemo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This exception is thrown to indicate, that something happened on our side or we got wrong
 * response from another service(if we have interactions with other external services).
 */
@NoArgsConstructor
public class InternalServerException extends RuntimeException {

  @Getter private String errorDescription;

  /** Constructor. */
  public InternalServerException(final String message, final Throwable cause) {
    super(message);
  }
}
