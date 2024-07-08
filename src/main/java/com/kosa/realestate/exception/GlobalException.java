package com.kosa.realestate.exception;

import lombok.Getter;

/**
 * GlobalException
 *
 * @author 오동건
 */
@Getter
public class GlobalException extends RuntimeException {

  private final String errorCode;

  public GlobalException(ErrorCode errorCode) {
    super(errorCode.getMESSAGE());
    this.errorCode = errorCode.toString();
  }
}
