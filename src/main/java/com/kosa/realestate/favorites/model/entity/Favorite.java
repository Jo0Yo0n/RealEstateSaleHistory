package com.kosa.realestate.favorites.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
  
  private Long favoriteId;
  private Long userId;
  private Long realEstateId;
}
