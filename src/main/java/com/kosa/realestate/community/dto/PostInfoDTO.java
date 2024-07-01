package com.kosa.realestate.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInfoDTO {

    private Long postId;
    private String title;
    private String content;
    private String createdAt;
}
