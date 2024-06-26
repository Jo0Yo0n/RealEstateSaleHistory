package com.kosa.realestate.favorites.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteDTO {

  private Long favoriteId;
  private Long userId;
  private Long realEstateId;
}
