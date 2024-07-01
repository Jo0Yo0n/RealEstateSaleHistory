package com.kosa.realestate.community.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UserPostDTO 클래스
 * 유저가 작성한 게시글에 대한 정보를 가져오는 DTO
 * 
 * @author 이주윤
 */
@Getter
@Setter
@Builder
public class UserPostDTO {
  private Long postId;
  private String content;
  private Long userId;
  private Long districtId;
  private String title;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long postView;
  private String nickname;
  private String districtName;
  private String cityName;
}
