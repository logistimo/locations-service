package com.logistimo.locations.exception.mapper;

import com.logistimo.locations.constants.LocationConstants;
import com.logistimo.locations.exception.BadRequestException;
import com.logistimo.locations.exception.ErrorResource;
import com.logistimo.locations.validation.LCValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by kumargaurav on 24/02/17.
 */
@ControllerAdvice
public class LSExceptionMapper extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(LCValidationException.class)
  public ResponseEntity<ErrorResource> handleException(LCValidationException exception) {
    return new ResponseEntity<>(new ErrorResource(exception.getMessage(), exception.getCode(), HttpStatus.BAD_REQUEST.value()),
    HttpStatus.BAD_REQUEST);
  }

  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResource> handleException(BadRequestException exception) {

    return new ResponseEntity<>(
        new ErrorResource(exception.getMessage(), LocationConstants.DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);

  }
}
