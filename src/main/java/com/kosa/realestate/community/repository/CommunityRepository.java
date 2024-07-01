package com.kosa.realestate.community.repository;

import com.kosa.realestate.community.dto.PostInfoDTO;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;

@Mapper
@Repository
public interface CommunityRepository {

  public int insertPost(PostDTO pdto);

  public void fileUpload(FileMetaDataDTO fdto);

  public List<PostDTO> postList();

  public PostDTO communityCard(Long postId);

  // 게시글 조회
  PostInfoDTO selectPostInfo(Long postId);

  public List<FileMetaDataDTO> selectFiles(Long postId);

  public void deleteFile(int fileId);

  public void postUpdate(PostDTO pdto);

  public void postDelete(Long postId);

  public List<PostDTO> loadPostsByDistrict(int districtId);

  public void updateViewCount(Long postId);

  public List<PostDTO> loadPostsByView(int districtId);

  public List<PostDTO> postViewList();


	List<PostInfoDTO> selectNewPostList();
}
