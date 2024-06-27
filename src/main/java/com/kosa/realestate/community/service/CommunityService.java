package com.kosa.realestate.community.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;
import com.kosa.realestate.community.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService implements ICommunityService{
	
    private final CommunityRepository communityRepository;
    
	@Override
	public int insertPost(PostDTO pdto) {
		int generatedPostId = communityRepository.insertPost(pdto);
		return generatedPostId;
	}

	@Override
	public void fileUpload(FileMetaDataDTO fdto) {
		communityRepository.fileUpload(fdto);
	}

	@Override
	public List<PostDTO> postList() {
		List<PostDTO> postList = communityRepository.postList();
		return postList;
	}

	@Override
	public PostDTO communityCard(int postId) {
		PostDTO commuCard = communityRepository.communityCard(postId);
		return commuCard;
		
	}
    
}
