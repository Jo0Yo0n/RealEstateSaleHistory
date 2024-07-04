package com.kosa.realestate.comments.model.dto;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentDTO {

    private Long commentId;
    private Long upperCommentId;
    private Long postId;
    private Long userId;
    private String comments;
    private LocalDateTime createdAt;
    private Long parentComment;
    
    private String nickname;
    private String dateOnly;
}
