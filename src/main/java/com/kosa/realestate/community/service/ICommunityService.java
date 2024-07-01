package com.kosa.realestate.community.service;

import com.kosa.realestate.community.dto.PostInfoDTO;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ICommunityService {

  // 게시글 등록
  int insertPost(PostDTO pdto);

  // 파일 업로드
  void fileUpload(FileMetaDataDTO fdto);

  List<PostDTO> postList();

  PostDTO communityCard(Long postId);

  // 게시글 조회
  void findPostInfo(Long postId);

  List<FileMetaDataDTO> selectFiles(Long postId);

  void deleteFile(int fileId);

  void fileTest(Long idx, MultipartFile[] files);

  void postUpdate(PostDTO pdto);

  void postDelete(Long postId);

  List<PostDTO> loadPostsByDistrict(int districtId);

  void updateViewCount(Long postId, HttpServletRequest request, HttpServletResponse response);

  List<PostDTO> loadPostsByView(int districtId);

  List<PostDTO> postViewList();
  

	// 게시글 메인 최신글 조회
	List<PostInfoDTO> findNewPostList();
}
