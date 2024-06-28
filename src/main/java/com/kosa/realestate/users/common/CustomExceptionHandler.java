package com.kosa.realestate.users.common;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(PersistenceException.class)
  public ResponseEntity<Object> handlePersistenceException(PersistenceException ex) {
    // 로그 남기기
    logger.error("PersistenceException 발생", ex);
    log.info("😊😊😊😊😊😊😊😊😊😊PersistenceException 발생", ex);
    // 사용자에게 반환할 에러 메시지
    String userMessage = "서버 내부에서 문제가 발생했습니다. 관리자에게 문의하세요.";

    // 사용자에게 반환할 응답 생성
    ApiError apiError =
        new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), userMessage);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}


// API 에러 정보를 담는 클래스
@Getter
@Setter
@ToString 
class ApiError {

  private HttpStatus status;
  private String message;
  private String userMessage;

  public ApiError(HttpStatus status, String message, String userMessage) {
    this.status = status;
    this.message = message;
    this.userMessage = userMessage;
  }


}
