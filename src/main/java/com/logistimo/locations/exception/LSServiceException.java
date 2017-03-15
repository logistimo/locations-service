package com.logistimo.locations.exception;

/**
 * Created by kumargaurav on 24/02/17.
 */
public final class LSServiceException extends RuntimeException {

  private final String message;

  public LSServiceException (String message) {
    super(message);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
