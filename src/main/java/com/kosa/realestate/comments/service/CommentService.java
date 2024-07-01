package com.kosa.realestate.comments.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosa.realestate.comments.model.dto.CommentDTO;
import com.kosa.realestate.comments.model.form.CommentForm;
import com.kosa.realestate.comments.repository.CommentRepository;
import com.kosa.realestate.community.service.ICommunityService;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {


    private final ICommunityService communityService;

    private final CommentRepository commentRepository;

    
    // 댓글 조회
    public List<CommentDTO> findCommentByPostId(Long postId) {
    	
    	communityService.findPostInfo(postId);
    	
    	return commentRepository.selectCommentByPostId(postId);
    }
    

    // 댓글 등록
    @Transactional
    public void addComments(CommentDTO cdto) {
        commentRepository.insertComment(cdto);
    }


    // 댓글 수정
    @Transactional
    public void modifyComment(Long commentId, Long postId, String email, CommentForm commentForm) {

//        UserDTO user = userService.findUserByEmail(email);
//        CommentDTO comment = commentRepository.selectCommentByCommentId(commentId);
//        
//        if (!user.getUserId().equals(comment.getUserId())) {
//            throw new RuntimeException("댓글 작성자가 아닙니다.");
//        }
//        communityService.findPostInfo(postId);

        commentRepository.modifyComment(commentId, commentForm);
    }
    
    
    // 댓글 삭제 수정
    public void modfiyDeleteComment(Long commentId, String email) {
    	
//    	UserDTO user = userService.findUserByEmail(email);
//    	CommentDTO comment = commentRepository.selectCommentByCommentId(commentId);
//    	
//    	if (!user.getUserId().equals(comment.getUserId())) {
//            throw new RuntimeException("댓글 작성자가 아닙니다.");
//        }
    	
    	commentRepository.modifyCommentDelete(commentId);
    }
}
