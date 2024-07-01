package com.kosa.realestate.comments.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kosa.realestate.comments.model.dto.CommentDTO;
import com.kosa.realestate.comments.model.form.CommentForm;
import com.kosa.realestate.comments.service.ICommentService;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.service.IUserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

  private final IUserService userService;
  private final ICommentService commentService;


  // 댓글 조회
  @GetMapping("/posts/{postId}")
  public ResponseEntity<List<CommentDTO>> commentList(@PathVariable("postId") Long postId) {

    return ResponseEntity.ok(commentService.findCommentByPostId(postId));
  }

  // 댓글 등록
  @ResponseBody
  @PostMapping("/posts/{postId}")
  public List<CommentDTO>  commentSave(@RequestParam("postId") Long postId,
      @RequestParam("commentText") String commentText, Principal principal) {

    String email = principal.getName();
    UserDTO udto = userService.findUserByEmail(email);
    Long userId = udto.getUserId();

    CommentDTO cdto = new CommentDTO();
    cdto.setPostId(postId);
    cdto.setComments(commentText);
    cdto.setUserId(userId);

    commentService.addComments(cdto);
    
    List<CommentDTO> commentList = commentService.findCommentByPostId(postId);

    return commentList;
  }



  // 댓글 수정
  @PutMapping("{commentId}/posts/{postId}")
  public String commentModify(@PathVariable("commentId") Long commentId,
      @PathVariable("postId") Long postId, @RequestBody CommentForm commentForm, Model model,
      Principal principal) {

    commentService.modifyComment(commentId, postId, principal.getName(), commentForm);

    return "redirect:/";
  }


  // 댓글 삭제 수정
  @PutMapping("/{commentId}")
  public String commentDeleteModify(@PathVariable("commentId") Long commentId, Model model,
      Principal principal) {

    commentService.modfiyDeleteComment(commentId, principal.getName());

    return "redirect:/";
  }
}


