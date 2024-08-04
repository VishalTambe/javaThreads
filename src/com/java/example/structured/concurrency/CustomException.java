package com.java.example.structured.concurrency;

/**
 * @author Vishal Tambe
 */
public class CustomException extends Exception {

  private static final long serialVersionUID = 1L;

  private final String errorCode;
  private final String message;

  public CustomException(String errorCode, String message) {
    super();
    this.errorCode = errorCode;
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

}
