package com.kosa.realestate.favorites.service;

import java.util.List;
import com.kosa.realestate.favorites.model.dto.FavoriteDetailListDTO;
import com.kosa.realestate.favorites.model.dto.FavoriteListDTO;

public interface IFavoriteService {

  List<FavoriteListDTO> findFavoriteList(int page, String email);
  List<FavoriteDetailListDTO> findFavoriteDetailList(int page, Long realEstateId);
  void addFavorite(Long realEstateId, String email);
  void removeFavorite(Long favoriteId);
  int getTotalFavoritesCountByEmail(Long userId);
}
