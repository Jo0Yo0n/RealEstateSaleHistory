package com.kosa.realestate.favorites.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.kosa.realestate.favorites.model.dto.FavoriteDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteDetailListDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteListDTO;
import com.kosa.realestate.favorites.repository.FavoriteRepostiory;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.service.IUserService;
import lombok.RequiredArgsConstructor;

/**
 * FavoriteService
 *
 * @author 오동건
 */
@Service
@RequiredArgsConstructor
public class FavoriteService implements IFavoriteService {

  private final IUserService userService;
  private final FavoriteRepostiory favoriteRepostiory;

  // 즐겨찾기 되어 있는지 확인
  public FavoriteDTO findFavoriteCheck(Long realEstateId, Long userId) {
    
    return favoriteRepostiory.selectFavoriteCheck(realEstateId, userId);
  }

  // 즐겨찾기 아파트 리스트 조회
  public List<FavoriteListDTO> findFavoriteList(int page, String email) {
    
    Pageable pageable = PageRequest.of(page, 6);
    
    UserDTO userDto = userService.findUserByEmail(email);

    return favoriteRepostiory.selectFavoriteList(userDto.getUserId(), pageable.getOffset(), pageable.getPageSize());
  }


  // 즐겨찾기 아파트 상세 조회
  public List<FavoriteDetailListDTO> findFavoriteDetailList(int page, Long realEstateId) {

    Pageable pageable = PageRequest.of(page, 10);
    
    return favoriteRepostiory.selectFavoriteDetailList(realEstateId, pageable.getOffset(), pageable.getPageSize());
  }


  // 즐겨찾기 추가
  public String addFavorite(Long realEstateId, String email) {

    UserDTO userDto = userService.findUserByEmail(email);
    
    FavoriteDTO favorite = findFavoriteCheck(realEstateId, userDto.getUserId());
    
    if(favorite == null) {
      
      favoriteRepostiory.insertFavorite(realEstateId, userDto.getUserId());
      return "즐겨찾기 추가되었습니다.";
    } else {
      
      removeFavorite(favorite.getFavoriteId());
      return "즐겨찾기 삭제되었습니다.";
    }
  }


  // 즐겨찾기 삭제
  public void removeFavorite(Long favoriteId) {
    
    favoriteRepostiory.removeFavorite(favoriteId);    
  }

  // 한 유저가 즐겨찾기한 매물 개수 반환
  public int getTotalFavoritesCountByEmail(Long userId) {
    return favoriteRepostiory.getTotalFavoritesCountByEmail(userId);
  }
}
