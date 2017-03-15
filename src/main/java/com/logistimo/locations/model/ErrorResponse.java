package com.logistimo.locations.model;

import org.springframework.http.HttpStatus;

/**
 * Created by kumargaurav on 24/02/17.
 */
public final class ErrorResponse {

  private final HttpStatus status;

  private final String message;

  public ErrorResponse(HttpStatus status,String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
