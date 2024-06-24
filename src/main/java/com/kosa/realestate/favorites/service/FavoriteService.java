package com.kosa.realestate.favorites.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.kosa.realestate.favorites.dto.FavoriteDetailListDto;
import com.kosa.realestate.favorites.dto.FavoriteDto;
import com.kosa.realestate.favorites.dto.FavoriteListDto;
import com.kosa.realestate.favorites.entity.Favorite;
import com.kosa.realestate.favorites.repository.FavoriteDao;
import com.kosa.realestate.favorites.repository.FavoriteRepostiory;
import com.kosa.realestate.users.DuplicateUserException;
import com.kosa.realestate.users.UserDTO;
import com.kosa.realestate.users.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final UserService userService;

  private final FavoriteDao favoriteDao;
  private final FavoriteRepostiory favoriteRepostiory;


  // 즐겨찾기 아파트 리스트 조회
  public List<FavoriteListDto> findFavoriteList(int page, String email) {
    
    Pageable pageable = PageRequest.of(page, 10);
    
    UserDTO userDto = userService.findByEmail(email);

    return favoriteDao.selectFavoriteList(userDto.getUserId(), pageable.getOffset(), pageable.getPageSize());
  }


  // 즐겨찾기 아파트 상세 조회
  public List<FavoriteDetailListDto> findFavoriteDetailLst(int page, Long realEstateId) {

    Pageable pageable = PageRequest.of(page, 10);
    
    return favoriteDao.selectFavoriteDetailList(realEstateId, pageable.getOffset(), pageable.getPageSize());
  }


  // 즐겨찾기 추가
  public FavoriteDto addFavorite(Long realEstateId, String email) {
    System.out.println(email);
    UserDTO userDto = userService.findByEmail(email);

    System.out.println("조회");
    Favorite favorite = favoriteRepostiory
        .save(Favorite.builder().userId(userDto.getUserId()).realEstateId(realEstateId).build());

    return FavoriteDto.builder().favoriteId(favorite.getFavoriteId()).userId(favorite.getUserId())
        .realEstateId(favorite.getFavoriteId()).build();
  }


  // 즐겨찾기 삭제
  public void removeFavorite(Long favoriteId) {
    
    Favorite favorite = favoriteRepostiory.findByFavoriteId(favoriteId)
        .orElseThrow(() -> new DuplicateUserException("not find favorite"));

    favoriteRepostiory.delete(favorite);
  }
}
