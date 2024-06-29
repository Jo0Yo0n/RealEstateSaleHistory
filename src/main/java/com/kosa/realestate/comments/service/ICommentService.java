package com.kosa.realestate.comments.service;

import java.util.List;

import com.kosa.realestate.comments.model.dto.CommentDTO;
import com.kosa.realestate.comments.model.form.CommentForm;

public interface ICommentService {
	
	// 댓글 조회
	List<CommentDTO> findCommentByPostId(Long postId);

	// 댓글 등록
	void addComments(Long postId, String email, CommentForm commentForm);

	// 댓글 수정
	void modifyComment(Long commentId, Long postId, String email, CommentForm commentForm);
	
	// 댓글 삭제 수정
	void modfiyDeleteComment(Long commentId, String email);
}
