package com.kosa.realestate.users.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User 클래스
 * 
 * @author 이주윤
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
  private Long userId;

  private String email;

  private String password;

  private String nickname;

  private char isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private String accountType;
}
