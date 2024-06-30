package com.kosa.realestate.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * AuthenticationFailureHandler 클래스
 * 인증 실패 시 처리를 담당
 * 다양한 인증 실패 상황에 대해 적절한 응답을 생성
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private static final Logger logger = LoggerFactory.getLogger(
      CustomAuthenticationFailureHandler.class);

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    String errorMessage;

    // CustomAuthenticationProvider 에서 던진 예외들을 받아서 errorMessage 에 담음
    if (exception instanceof UsernameNotFoundException) {
      errorMessage = "이메일 또는 비밀번호를 확인해 주세요.";
      logger.info("UsernameNotFoundException occurred: {}", exception.getMessage());
    } else if (exception instanceof DisabledException) {
      errorMessage = exception.getMessage();
      logger.info("DisabledException occurred: {}", errorMessage);
    } else if (exception instanceof BadCredentialsException) {
      errorMessage = "이메일 또는 비밀번호를 확인해 주세요.";
      logger.info("BadCredentialsException occurred: {}", exception.getMessage());
    } else {
      errorMessage = "예상치 못한 인증 오류";
      logger.error("Unexpected authentication exception", exception);
    }

    // 로그인 실패 시 이동하는 URL 지정 및 세션에 errorMessage 담아서 보냄
    setDefaultFailureUrl("/users/login?error");
    request.getSession().setAttribute("errorMessage", errorMessage);
    super.onAuthenticationFailure(request, response, exception);

    logger.info("Authentication failed: {}", errorMessage);
    logger.info("Exception type: {}", exception.getClass().getName());
    logger.info("Exception message: {}", exception.getMessage());
  }
}
