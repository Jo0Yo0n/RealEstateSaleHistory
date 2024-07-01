package com.kosa.realestate.comments.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosa.realestate.comments.model.dto.CommentDTO;
import com.kosa.realestate.comments.model.form.CommentForm;

@Mapper
@Repository
public interface CommentRepository {

    // 댓글 아이디 기준 조회
    CommentDTO selectCommentByCommentId(Long commentId);
    
    // 게시물 기준 조회
    List<CommentDTO> selectCommentByPostId(Long postId);

    // 댓글 등록
    int insertComment(CommentDTO cdto);

    // 댓글 수정
    int modifyComment(
        @Param("commentId") Long commentId, @Param("commentForm") CommentForm commentForm);
    
    // 댓글 삭제 수정
    int modifyCommentDelete(Long commentId);

}
