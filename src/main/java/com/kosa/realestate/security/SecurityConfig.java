package com.kosa.realestate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SecurityConfig 클래스
 *
 * 애플리케이션이 실행될 때 @Configuration 어노테이션이 붙은 클래스들이 처리됨
 * 이때 filterChain() 메서드가 실행되면서 필터체인이 구성됨
 * 이후 HTTP 요청이 들어오면 미리 구성된 필터 체인을 통과하면서 정의된 설정에 따라 처리됨
 * 즉, 구성 순서와 실행 순서는 다름
 * 
 * @author 이주윤
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomAuthenticationProvider authenticationProvider;
  private final CustomAuthenticationFailureHandler failureHandler;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
        .headers((headers) -> headers
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
        .formLogin((formLogin) -> formLogin
            .loginPage("/users/login")
            .failureHandler(failureHandler)
            .defaultSuccessUrl("/"))
        .logout((logout) -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true))
        .csrf(csrf -> csrf.disable())
    ;
    return http.build();
  }

  // AuthenticationManager 는 스프링 시큐리티의 인증을 처리함.
  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    
    return authenticationConfiguration.getAuthenticationManager();
  }
}
