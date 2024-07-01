package com.kosa.realestate.community.service;

import com.kosa.realestate.community.dto.PostInfoDTO;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;

public interface ICommunityService {

	//게시글 등록
	int insertPost(PostDTO pdto);
	
	//파일 업로드
	void fileUpload(FileMetaDataDTO fdto);

	List<PostDTO> postList();

	PostDTO communityCard(int postId);

	// 게시글 조회
	void findPostInfo(Long postId);

	List<FileMetaDataDTO> selectFiles(int postId);

	void deleteFile(int fileId);

	void fileTest(int idx, MultipartFile[] files);

	void postUpdate(PostDTO pdto);

	void postDelete(int postId);

	List<PostDTO> loadPostsByDistrict(int districtId);

	// 게시글 메인 최신글 조회
	List<PostInfoDTO> findNewPostList();
}
