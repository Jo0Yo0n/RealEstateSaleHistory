package com.kosa.realestate.users;

import com.kosa.realestate.users.model.Users;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * UserContext 클래스
 * Principle 객체에 더 많은 정보를 넣기 위한 클래스
 *
 * @author 이주윤
 */
public class UserContext extends User {

  private final Long userId;
  private final String nickname;
  private final char isDeleted;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public UserContext(Users user, List<GrantedAuthority> authorities) {
    super(user.getEmail(), user.getPassword(), authorities);
    this.userId = user.getUserId();
    this.nickname = user.getNickname();
    this.isDeleted = user.getIsDeleted();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
  }
}
