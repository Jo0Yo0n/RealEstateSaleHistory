package com.kosa.realestate.favorites.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteListDTO {

  private Long favoriteId;
  private Long realEstateId;
  private String complexName;
  private String address;
  private String addressStreet;
  private Long constructionYear;
  private Double minSalePrice;
  private Double maxSalePrice;
  private Double latelySalePrice;
  private String latelyContractDate;
  private Double latelyExclusiveArea;
  private Long latelyFloor;
  private Long buildingTypeId;
  private String buildingTypeName;
  private Long neighborhoodId;
  private String neighborhoodName;
  private Long districtId;
  private String districtName;
  private Long cityId;
  private String cityName;
}
