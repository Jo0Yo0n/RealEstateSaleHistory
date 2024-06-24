package com.kosa.realestate.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosa.realestate.users.Users;
import com.kosa.realestate.users.repository.UserDAO;

import lombok.RequiredArgsConstructor;

/**
 * UserSecurityService 클래스
 *
 * @author 이주윤
 */

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserDAO userDAO;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Users> _user = userDAO.findUserByEmail(email);
    if (_user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    Users user = _user.get();

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("USER")); // 현재는 USER 권한만 존재

    return new User(user.getEmail(), user.getPassword(), authorities);
  }
}
