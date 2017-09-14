package com.logistimo.locations.exception.mapper;

import com.logistimo.locations.exception.BadRequestException;
import com.logistimo.locations.exception.ErrorResource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Locale;

import javax.validation.ValidationException;

/**
 * Created by kumargaurav on 24/02/17.
 */
@ControllerAdvice
public class LSExceptionMapper extends ResponseEntityExceptionHandler {

  static final String ECODE = "LSE001";
  @ResponseBody
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResource> handleException(ValidationException exception, Locale locale)
      throws IOException {

    return new ResponseEntity<>(new ErrorResource(exception.getMessage(), ECODE, HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);

  }

  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResource> handleException(BadRequestException exception, Locale locale)
      throws IOException {

    return new ResponseEntity<>(
        new ErrorResource(exception.getMessage(), ECODE, HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);

  }
}
