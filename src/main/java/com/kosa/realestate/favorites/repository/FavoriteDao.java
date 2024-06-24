package com.kosa.realestate.favorites.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.kosa.realestate.favorites.dto.FavoriteDetailListDto;
import com.kosa.realestate.favorites.dto.FavoriteListDto;

@Mapper
@Repository
public interface FavoriteDao {

  // 즐겨찾기 아파트 리스트 조회
  List<FavoriteListDto> selectFavoriteList(Long userId);

  // 즐겨찾기 아파트 상세 조회
  List<FavoriteDetailListDto> selectFavoriteDetailList(Long realEstateId);
}
