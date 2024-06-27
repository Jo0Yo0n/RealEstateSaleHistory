package com.kosa.realestate.community.dto;

import java.time.LocalDateTime;

import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.users.model.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class PostDTO {

	private int postId;
    private int userId;
    private int districtId;
    private String title;
    private String content;
    private int postView;
    private String isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String nickname;
    private String districtName;
	public PostDTO(int postId, int userId, String title, String content) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.title = title;
		this.content = content;
	}
    
    // 모든 필드를 초기화하는 생성자
    public PostDTO(int postId, int userId, int districtId, String title, String content,
                   int postView, String isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt,
                   String nickname, String districtName) {
        this.postId = postId;
        this.userId = userId;
        this.districtId = districtId;
        this.title = title;
        this.content = content;
        this.postView = postView;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.nickname = nickname;
        this.districtName = districtName;
    }
    

}

