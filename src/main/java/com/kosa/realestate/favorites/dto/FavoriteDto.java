package com.kosa.realestate.favorites.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteDto {

  private Long favoriteId;
  private Long userId;
  private Long realEstateId;
}
