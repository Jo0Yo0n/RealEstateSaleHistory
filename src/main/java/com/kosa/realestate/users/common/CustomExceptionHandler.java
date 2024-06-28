package com.kosa.realestate.users.common;

public class CustomExceptionHandler extends RuntimeException {
  public CustomExceptionHandler(String message) {
    super(message);
  }
}