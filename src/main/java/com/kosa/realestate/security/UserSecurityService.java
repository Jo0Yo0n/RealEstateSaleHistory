package com.kosa.realestate.security;

import com.kosa.realestate.users.model.Users;
import com.kosa.realestate.users.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserSecurityService 클래스
 * 사용자 정보를 DB에서 가져오는 역할
 * loadUserByUsername 메서드를 구현하여 사용자 정보를 UserDetails 객체로 반환
 *
 * @author 이주윤
 */

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;
  private static final Logger logger = LoggerFactory.getLogger(UserSecurityService.class);

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    logger.info("Attempting to load user by email: {}", email);

    Users user = userRepository.findUserByEmail(email)
        .orElseThrow(() -> {  // 입력한 이메일에 매칭되는 User 가 없을 때 UsernameNotFoundException 던짐
          logger.info("User not found for email: {}", email);
          return new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        });

    String accountType = user.getAccountType();
    List<GrantedAuthority> authorities = new ArrayList<>();

    if ("admin".equals(accountType)) {
      authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
    } else if ("agent".equals(accountType)) {
      authorities.add(new SimpleGrantedAuthority(UserRole.AGENT.getValue()));
    } else {
      authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
    }

    logger.info("User loaded successfully: {}", email);
    return new UserContext(user, authorities);
  }
}
