package com.kosa.realestate.users.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDTO 클래스
 * 
 * @author 이주윤
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  
  private Long userId;
  private String email;
  private String password;
  private String nickname;
  private char isDeleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String accountType;
}
