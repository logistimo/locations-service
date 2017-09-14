package com.logistimo.locations.exception;

import com.google.gson.JsonSyntaxException;

/**
 * Created by kumargaurav on 14/09/17.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(JsonSyntaxException e) {
    super(e.getMessage(), e);
  }
}
