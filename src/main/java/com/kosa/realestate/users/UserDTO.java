package com.kosa.realestate.users;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDTO 클래스
 * 
 * @author 이주윤
 */
@Getter
@Setter
public class UserDTO {
  private Long userId;
  private String email;
  private String password;
  private String nickname;
  private char isDeleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
