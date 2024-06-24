package com.kosa.realestate.users;

/**
 * DuplicateUserException 클래스
 * 
 * @author 이주윤
 */
public class DuplicateUserException extends RuntimeException {
  public DuplicateUserException(String message) {
    super(message);
  }
}
