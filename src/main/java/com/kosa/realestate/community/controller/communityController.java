package com.kosa.realestate.community.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.admindivision.service.IAdminDivisionService;
import com.kosa.realestate.comments.model.dto.CommentDTO;
import com.kosa.realestate.comments.service.ICommentService;
import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;
import com.kosa.realestate.community.dto.PostInfoDTO;
import com.kosa.realestate.community.dto.UserPostDTO;
import com.kosa.realestate.community.service.ICommunityService;
import com.kosa.realestate.pagination.dto.PageInfoDTO;
import com.kosa.realestate.pagination.service.PaginationService;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@Controller
public class communityController {


  @Autowired
  private ICommunityService communityService;
  @Autowired
  private IUserService userService;
  @Autowired
  private IAdminDivisionService adminDivisionService;
  @Autowired
  private ICommentService commentService;
  @Autowired
  private PaginationService paginationService;


  
  @GetMapping("/reply")
  public String reply() {
    return "reply";
  }

  
  @PostMapping("/postUpdate")
  @ResponseBody
  // 게시글 업로드(아이디,구아이디,제목,내용)
  public void postUpdate(@RequestParam("userId") int userId, @RequestParam("title") String title,
      @RequestParam("districtId") int districtId, @RequestParam("content") String content,
      @RequestParam(value = "uploadFile", required = false) MultipartFile[] files) {

    // 게시글 업로드
    PostDTO pdto = new PostDTO(userId, districtId, title, content);

    // insert한 idx값을 가져온다
    int n = communityService.insertPost(pdto);

    Long idx = pdto.getPostId();

    communityService.fileTest(idx, files);

  }

  // 커뮤니티 작성폼
  @GetMapping("/communityForm")
  public String communityForm(Principal principal, Model model) {
    if (principal == null) {
      return "access_denied";
    }

    String email = principal.getName();
    UserDTO udto = userService.findUserByEmail(email);
    model.addAttribute("userId", udto.getUserId());

    List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);
    model.addAttribute("districts", districts);

    return "communityForm";
  }

  @GetMapping("/communityList")
  public String communityList(Principal principal, Model model, @RequestParam(value = "page", defaultValue = "1") int currentPage) {
      List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);
      model.addAttribute("districts", districts);

      List<PostDTO> postList = communityService.postList();
      int totalPosts = postList.size();
      
      int postsPerPage = 5;
      int displayPageNum = 5;

      PageInfoDTO pageInfo = new PageInfoDTO(totalPosts, currentPage, postsPerPage, displayPageNum);
      paginationService.calculatePageInfo(pageInfo);

      int start = (currentPage - 1) * postsPerPage;
      int end = Math.min(start + postsPerPage, totalPosts);
      List<PostDTO> pagedPostList = postList.subList(start, end);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      for (PostDTO postDTO : pagedPostList) {
          LocalDateTime createdAt = postDTO.getCreatedAt();
          if (createdAt != null) {
              String dateOnly = createdAt.format(formatter);
              postDTO.setDateOnly(dateOnly);
          }
      }

      model.addAttribute("postList", pagedPostList);
      model.addAttribute("pageInfo", pageInfo);

      return "communityList";
  }


  // 게시판 상세
  @GetMapping("/communityCard")
  public String communityCard(@RequestParam("postId") Long postId, Model model, Principal principal,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

    // 글
    PostDTO commuCard = communityService.communityCard(postId);
    model.addAttribute("commuCard", commuCard);

    // 댓글
    List<CommentDTO> commentList = commentService.findCommentByPostId(postId);
    model.addAttribute("commentList", commentList);
    System.out.println("commentList"+commentList);

    // 사용자 검증
    if (principal != null) {
      String email = principal.getName();
      UserDTO udto = userService.findUserByEmail(email);
      Long userId = udto.getUserId();

      boolean isAuthor = commuCard.getUserId() == userId.intValue();
      model.addAttribute("isAuthor", isAuthor);

    }


    // 파일
    List<FileMetaDataDTO> files = communityService.selectFiles(postId);
    model.addAttribute("files", files);

    // 조회수
    communityService.updateViewCount(postId, request, response);

    return "communityCard";
  }

  // 게시판 수정 폼
  @GetMapping("/commuUpdate")
  public String commuUpdate(@RequestParam("postId") Long postId, Model model) {

    PostDTO commuCard = communityService.communityCard(postId);
    model.addAttribute("commuCard", commuCard);

    boolean isAuthor = true;
    model.addAttribute("update", isAuthor);

    List<FileMetaDataDTO> files = communityService.selectFiles(postId);
    model.addAttribute("files", files);


    List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);
    model.addAttribute("districts", districts);

    return "communityForm";
  }

  // 게시글 메인 최신글 조회
  @GetMapping("/community/new")
  public ResponseEntity<List<PostInfoDTO>> newPostList() {

    return ResponseEntity.ok(communityService.findNewPostList());
  }

  // 커뮤니티 파일 삭제
  @ResponseBody
  @PostMapping("/deleteFile")
  private List<FileMetaDataDTO> deleteFile(@RequestParam("fileId") int fileId,
      @RequestParam("postId") Long postId) {

    communityService.deleteFile(fileId);
    List<FileMetaDataDTO> files = communityService.selectFiles(postId);

    return files;
  }

  // 게시글 삭제 => 'Y'로 변경
  @ResponseBody
  @DeleteMapping("/postDelete")
  private void postDelete(@RequestParam("postId") Long postId) {

    communityService.postDelete(postId);

  }

  // 게시글 수정 등록
  @ResponseBody
  @PostMapping("/updateCommu")
  public void updateCommu(@RequestParam("userId") int userId, @RequestParam("title") String title,

      @RequestParam("postId") Long postId, @RequestParam("content") String content,
      @RequestParam("districtId") int districtId,
      @RequestParam(value = "uploadFile", required = false) MultipartFile[] files) {

    PostDTO pdto = new PostDTO();

    pdto.setUserId(userId);
    pdto.setTitle(title);
    pdto.setPostId(postId);
    pdto.setContent(content);
    pdto.setDistrictId(districtId);

    // 게시글 업데이트
    communityService.postUpdate(pdto);
    // 피일 업로드
    communityService.fileTest(postId, files);

  }


  // 게시판 지역구 필터링
  @GetMapping("/loadPostsByDistrict")
  @ResponseBody
  private List<PostDTO> loadPostsByDistrict(@RequestParam("districtId") int districtId, @RequestParam(value = "page", defaultValue = "1") int currentPage) {

    List<PostDTO> filterPostList = null;

    if (districtId == 0) {
      // 지역구 클릭(전체 데이터)
      filterPostList = communityService.postList();

    } else {

      filterPostList = communityService.loadPostsByDistrict(districtId);
    }

    // 각 게시물의 생성일자 날짜 부분만 추출하여 새로운 속성에 설정
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (PostDTO postDTO : filterPostList) {
      LocalDateTime createdAt = postDTO.getCreatedAt();
      if (createdAt != null) {
        String dateOnly = createdAt.format(formatter);
        postDTO.setDateOnly(dateOnly);
      }
    }
    return filterPostList;
  }

  // 게시판 필터링(조회수,최신순)
  @GetMapping("/filterOption")
  @ResponseBody
  private List<PostDTO> filterOption(@RequestParam("filterOption") String filterOption,
      @RequestParam(value = "districtId", required = false) int districtId) {

    System.out.println(districtId);
    List<PostDTO> filterOptionPostList = null;


    // 전체 최신순
    if (districtId == 0 && filterOption.equals("최신순")) {
      filterOptionPostList = communityService.postList();
    }
    // 전체 조회수
    else if (districtId == 0 && filterOption.equals("조회순")) {
      System.out.println("여기");
      filterOptionPostList = communityService.postViewList();
    }
    // 구별 최신순
    else if (districtId != 0 && filterOption.equals("최신순")) {

      filterOptionPostList = communityService.loadPostsByDistrict(districtId);

    } else if (districtId != 0 && filterOption.equals("조회순")) {

      filterOptionPostList = communityService.loadPostsByView(districtId);
    }

    // 각 게시물의 생성일자 날짜 부분만 추출하여 새로운 속성에 설정
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (PostDTO postDTO : filterOptionPostList) {
      LocalDateTime createdAt = postDTO.getCreatedAt();
      if (createdAt != null) {
        String dateOnly = createdAt.format(formatter);
        postDTO.setDateOnly(dateOnly);
      }
    }
    return filterOptionPostList;
  }


  // 게시글 검색
  @GetMapping("/searchPosts")
  @ResponseBody
  public List<PostDTO> searchPosts(@RequestParam("searchText") String searchText,
                                   @RequestParam(value = "districtId", required = false) int districtId) {
     System.out.println(districtId+searchText);
     
     List<PostDTO> posts =null;
      if(districtId==0) {        
        posts = communityService.searchPosts(searchText);
      }else {
        posts = communityService.searchOptionPosts(searchText,districtId);
      }

      // 각 게시물의 생성일자 날짜 부분만 추출하여 새로운 속성에 설정
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      
      for (PostDTO postDTO : posts) {
        LocalDateTime createdAt = postDTO.getCreatedAt();
        if (createdAt != null) {
          String dateOnly = createdAt.format(formatter);
          postDTO.setDateOnly(dateOnly);
        }
      }
      return posts;
  }

  // 사용자가 작성한 게시물 조회
  @GetMapping("/community/{userId}")
  public String getUserPosts(@PathVariable(name = "userId") Long userId,
      @RequestParam(name="page", defaultValue = "0") int page, Model model) {

    int pageSize = 6;
    List<UserPostDTO> posts = communityService.getPostsByUserId(userId, page, pageSize);
    int totalPosts = communityService.getTotalPostsCountByUserId(userId);
    int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

    model.addAttribute("posts", posts);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);

    return "post-list :: content";
  }
}