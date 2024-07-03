package com.kosa.realestate.comments.service;

import java.util.List;

import com.kosa.realestate.comments.model.dto.CommentDTO;
import com.kosa.realestate.comments.model.form.CommentForm;

public interface ICommentService {
	
	// 댓글 조회
	List<CommentDTO> findCommentByPostId(Long postId);

	// 댓글 등록
	void addComments(CommentDTO cdto);

	// 댓글 수정
	void modifyComment(Long commentId);
	
	// 댓글 삭제 수정
	void modfiyDeleteComment(Long commentId);
}
