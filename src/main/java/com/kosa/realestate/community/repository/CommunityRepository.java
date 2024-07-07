package com.kosa.realestate.community.repository;

import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;
import com.kosa.realestate.community.dto.PostInfoDTO;
import com.kosa.realestate.community.dto.UserPostDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * CommunityRepository 클래스
 * 
 * @author 강희원
 */

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


  public List<PostDTO> searchPosts(String searchText);

  List<PostInfoDTO> selectNewPostList();

  List<UserPostDTO> getPostsByUserId(@Param("userId") Long userId, @Param("offset") int offset,
      @Param("pageSize") int pageSize); // 유저가 작성한 글 조회

  int getTotalPostsCountByUserId(@Param("userId") Long userId);

    List<PostDTO> searchOptionPosts(@Param("searchText") String searchText, @Param("districtId") int districtId);

}
