package com.kosa.realestate.comments.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {

    private Long commentId;
    private Long upperCommentId;
    private Long postId;
    private Long userId;
    private String comments;
    private String createdAt;
    private Long parentComment;
    
    private String nickname;
}
