package com.kosa.realestate.favorites.service;

import java.util.List;
import com.kosa.realestate.favorites.model.dto.FavoriteDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteDetailListDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteListDTO;

/**
 * IFavoriteService
 *
 * @author 오동건
 */
public interface IFavoriteService {
  
  // 즐겨찾기 되어 있는지 확인
  FavoriteDTO findFavoriteCheck(Long realEstateId, Long userId); 

  // 즐겨찾기 아파트 리스트 조회
  List<FavoriteListDTO> findFavoriteList(int page, String email);

  // 즐겨찾기 아파트 상세 조회
  List<FavoriteDetailListDTO> findFavoriteDetailList(int page, Long realEstateId);

  // 즐겨찾기 추가
  String addFavorite(Long realEstateId, String email);

  // 즐겨찾기 삭제
  void removeFavorite(Long favoriteId);

  // 한 유저가 즐겨찾기한 매물 개수 반환
  int getTotalFavoritesCountByEmail(Long userId);
}
