package com.kosa.realestate.statistics.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NeighborhoodMostFavoriteDTO {
  
  private Long cityId;
  private String cityName;
  private Long districtId;
  private String districtName;
  private Long neighborhoodId;
  private String neighborhoodName;
  private Long favoriteCnt;
}
