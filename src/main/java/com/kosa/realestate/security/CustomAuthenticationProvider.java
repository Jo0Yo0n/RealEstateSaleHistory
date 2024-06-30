package com.kosa.realestate.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 실제 인증 로직을 담당하는 AuthenticationProvier 클래스
 * UserSecurityService 를 통해 사용자 정보를 가져오고, PasswordEncoder 를 사용해 비밀번호를 검증
 * 
 * @author 이주윤
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserSecurityService userSecurityService;
  private final PasswordEncoder passwordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  // formLogin()에 UsernamePasswordAuthenticationFilter 가 추가되고 해당 필터가
  // HTTP 요청에서 username 과 password 를 추출하여 UsernamePasswordAuthenticationToken 을 생성 (인증 요청용)
  // 이 토큰은 AuthenticationManager 에 전달되고, 이는 다시 설정된 AuthenticationProvider 에 전달 됨.
  // AuthenticationProvider 는 인증이 성공하면 UsernamePasswordAuthenticationToken 을 반환함 (인증 성공 결과 알림)
  // (UsernamePasswordAuthenticationToken 은 Authentication 의 구현체)
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getName();
    String password = authentication.getCredentials().toString();

    try {
      UserContext userContext = (UserContext) userSecurityService.loadUserByUsername(email);

      // 등록된 계정(탈퇴 유무 관계 없이) & 틀린 비밀번호
      if (!passwordEncoder.matches(password, userContext.getPassword())) {
        throw new BadCredentialsException("이메일 또는 비밀번호를 확인해 주세요.");
      }

      // 탈퇴한 계정 & 맞는 비밀번호
      if (userContext.getIsDeleted() == 'Y') {
        logger.info("Deleted user attempted to log in: {}", email);
        throw new DisabledException("탈퇴한 회원입니다.");
      }

      // 탈퇴 안 한 계정 & 맞는 비밀번호 (로그인 성공)
      return new UsernamePasswordAuthenticationToken(userContext, password, userContext.getAuthorities());
    } catch (UsernameNotFoundException e) { // 존재하지 않는 이메일
      logger.info("User not found: {}", email);
      throw new BadCredentialsException("이메일 또는 비밀번호를 확인해 주세요."); // 보안상 구체적 오류 원인을 알리지 않음
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
