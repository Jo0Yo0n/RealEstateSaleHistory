package com.kosa.realestate.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ExceptionControllerAdvice
 *
 * @author 오동건
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

  // RuntimeException
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ExceptionResponse> globalRequestException(
      final GlobalException globalException) {

    return ResponseEntity.badRequest().body(
        new ExceptionResponse(globalException.getMessage(), globalException.getErrorCode()));
  }


  @Getter
  @AllArgsConstructor
  public class ExceptionResponse {

    private String message;
    private String errorCode;
  }
}
