package com.logistimo.locations.validation;

import javax.validation.ValidationException;

/**
 * Created by yuvaraj on 28/03/18.
 */
public class LCValidationException extends ValidationException {

  private final String code;


  public LCValidationException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
