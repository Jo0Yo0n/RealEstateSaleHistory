package com.kosa.realestate.favorites.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.kosa.realestate.favorites.model.dto.FavoriteDetailListDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteListDTO;
import com.kosa.realestate.favorites.repository.FavoriteRepostiory;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.service.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService implements IFavoriteService {

  private final IUserService userService;
  private final FavoriteRepostiory favoriteRepostiory;


  // 즐겨찾기 아파트 리스트 조회
  public List<FavoriteListDTO> findFavoriteList(int page, String email) {
    
    Pageable pageable = PageRequest.of(page, 10);
    
    UserDTO userDto = userService.findUserByEmail(email);

    return favoriteRepostiory.selectFavoriteList(userDto.getUserId(), pageable.getOffset(), pageable.getPageSize());
  }


  // 즐겨찾기 아파트 상세 조회
  public List<FavoriteDetailListDTO> findFavoriteDetailLst(int page, Long realEstateId) {

    Pageable pageable = PageRequest.of(page, 10);
    
    return favoriteRepostiory.selectFavoriteDetailList(realEstateId, pageable.getOffset(), pageable.getPageSize());
  }


  // 즐겨찾기 추가
  public void addFavorite(Long realEstateId, String email) {

    UserDTO userDto = userService.findUserByEmail(email);

    favoriteRepostiory.insertFavorite(realEstateId, userDto.getUserId());
  }


  // 즐겨찾기 삭제
  public void removeFavorite(Long favoriteId) {
    
    favoriteRepostiory.removeFavorite(favoriteId);    
  }
}
