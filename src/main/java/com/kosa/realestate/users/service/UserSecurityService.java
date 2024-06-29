package com.kosa.realestate.users.service;

import com.kosa.realestate.users.UserContext;
import com.kosa.realestate.users.UserRole;
import com.kosa.realestate.users.model.Users;
import com.kosa.realestate.users.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserSecurityService 클래스
 *
 * @author 이주윤
 */

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Users> _user = userRepository.findUserByEmail(email);
    if (_user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    if (_user.get().getIsDeleted() == 'Y') {
      throw new UsernameNotFoundException("탈퇴한 회원입니다.");
    }

    Users user = _user.get();
    String accountType = user.getAccountType();
    List<GrantedAuthority> authorities = new ArrayList<>();

    if("admin".equals(accountType)) {
      authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
    } else if ("agent".equals(accountType)) {
      authorities.add(new SimpleGrantedAuthority(UserRole.AGENT.getValue()));
    } else {
      authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
    }

    return new UserContext(user, authorities);
  }
}
