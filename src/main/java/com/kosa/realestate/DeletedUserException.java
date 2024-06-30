package com.kosa.realestate;

import org.springframework.security.core.AuthenticationException;

public class DeletedUserException extends AuthenticationException {

  public DeletedUserException(String msg) {
    super(msg);
  }
}
