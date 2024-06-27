package com.kosa.realestate.community.service;

import java.util.List;

import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;

public interface ICommunityService {

	//게시글 등록
	int insertPost(PostDTO pdto);
	
	//파일 업로드
	void fileUpload(FileMetaDataDTO fdto);

	List<PostDTO> postList();

	PostDTO communityCard(int postId);
	
    
}
