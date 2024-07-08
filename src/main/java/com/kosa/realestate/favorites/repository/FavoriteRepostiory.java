package com.kosa.realestate.favorites.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.kosa.realestate.favorites.model.dto.FavoriteDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteDetailListDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteListDTO;   

/**
 * FavoriteRepostiory
 *
 * @author 오동건
 */
@Mapper
@Repository
public interface FavoriteRepostiory {
  
  // 즐겨찾기 되어 있는지 확인
  FavoriteDTO selectFavoriteCheck(@Param("realEstateId") Long realEstateId,  @Param("userId") Long userId);
  
  // 즐겨찾기 아파트 리스트 조회
  List<FavoriteListDTO> selectFavoriteList(@Param("userId") Long userId,
                                           @Param("offset") Long offset,
                                           @Param("pageSize") int pageSize);

  // 즐겨찾기 아파트 상세 조회
  List<FavoriteDetailListDTO> selectFavoriteDetailList(@Param("realEstateId") Long realEstateId,
                                                       @Param("offset") Long offset,
                                                       @Param("pageSize") int pageSize);
  
  // 즐겨찾기 등록
  void insertFavorite(@Param("realEstateId") Long realEstateId,  @Param("userId") Long userId);
  
  // 즐겨찾기 삭제
  void removeFavorite(Long favoriteId);

  // 사용자가 즐겨찾기한 매물 개수 반환
  int getTotalFavoritesCountByEmail(Long userId);
}
