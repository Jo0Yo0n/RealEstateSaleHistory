package com.kosa.realestate.realestates.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * @author 백재우
 */
@Getter
@Setter
@ToString
public class RealEstateExtraDTO {
  private String cityName;
  private String buildingTypeName;
  private String districtName;
  private String neighborhoodName;
  private String allPrice;
}
