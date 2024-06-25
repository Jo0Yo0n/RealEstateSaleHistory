package com.kosa.realestate.users.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDTO 클래스
 * 
 * @author 이주윤
 */
@Getter
@Setter
@Builder
public class UserDTO {
  
  private Long userId;
  private String email;
//  private String password;  // 보안을 위해 DTO에 password 멤버변수 삭제
  private String nickname;
  private char isDeleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
