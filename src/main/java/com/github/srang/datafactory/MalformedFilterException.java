package com.github.srang.datafactory;

public class MalformedFilterException extends Exception {
  public MalformedFilterException(String message) {
    super(message);
  }
  public MalformedFilterException(String message, Exception ex) {
    super(message,ex);
  }
}
