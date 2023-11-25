package com.start.springlearningdemo.exception;

import com.start.springlearningdemo.responsedto.ApplicationExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param exception exception
     * @return ApplicationExceptionResponseDto
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApplicationExceptionResponseDto> handleUnauthorizedException(final UnauthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ApplicationExceptionResponseDto.builder()
                .message(UNAUTHORIZED.getReasonPhrase())
                .status(UNAUTHORIZED.value())
                .errors(Map.of())
                .build(), UNAUTHORIZED);
    }

    /**
     * @param exception exception
     * @return ApplicationExceptionResponseDto
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApplicationExceptionResponseDto> handleThrowableException(final Throwable exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ApplicationExceptionResponseDto.builder()
                .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .status(INTERNAL_SERVER_ERROR.value())
                .errors(Map.of())
                .build(), INTERNAL_SERVER_ERROR);
    }

    /**
     * Error handling for invalid request body or incorrect request body structure.
     * Example:
     * {"urls: "string" - invalid request body
     * {"urll" : "someValue"} - invalid request body structure. Expected: {"url" : "someValue"}
     *
     * @param exception exception
     * @return ApplicationExceptionResponseDto
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApplicationExceptionResponseDto> handleHttpMessageConversionException(final HttpMessageConversionException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ApplicationExceptionResponseDto.builder()
                .message(UNPROCESSABLE_ENTITY.getReasonPhrase())
                .status(UNPROCESSABLE_ENTITY.value())
                .errors(Map.of())
                .build(), INTERNAL_SERVER_ERROR);
    }

}
