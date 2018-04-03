package com.logistimo.locations.exception;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Yuvaraj
 */
public class ExceptionUtils {

  private static final ResourceBundle ERRORS = ResourceBundle.getBundle("errors");

  private ExceptionUtils() {}

  /**
   * @return error message
   */
  public static String constructMessage(String code, Object... params) {
    String message;
    try {
      message = ERRORS.getString(code);
      if (params != null && params.length > 0) {
        return MessageFormat.format(message, params);
      } else if (StringUtils.isNotEmpty(message)) {
        return message;
      }
    } catch (Exception ignored) {
      // ignored
    }
    return code;
  }

}
