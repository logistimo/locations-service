package com.logistimo.locations.exception.mapper;

import com.logistimo.locations.exception.LSServiceException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by kumargaurav on 24/02/17.
 */
@ControllerAdvice
public class LSExceptionMapper extends ResponseEntityExceptionHandler {

  @ExceptionHandler(LSServiceException.class)
  @ResponseBody
  public void handleException(HttpServletResponse response,LSServiceException ex) throws
      IOException {
    response.sendError(HttpServletResponse.SC_BAD_REQUEST,getMessage(ex));
  }

  private String getMessage(Throwable exception) {
    StringBuilder error = getErrorMessage(exception);
    int depth = 0;
    while(depth++ < 3 && (exception = exception.getCause()) != null) {
      error.append(getErrorMessage(exception));
    }
    return error.toString();
  }

  private StringBuilder getErrorMessage(Throwable exception) {
    StringBuilder message = new StringBuilder();
    message.append(exception.getClass().getName()).append(":");
    message.append(exception.getMessage()).append(";\n");
    return message;
  }
}
