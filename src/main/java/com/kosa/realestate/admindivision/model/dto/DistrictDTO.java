package com.kosa.realestate.admindivision.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistrictDTO {

  private Long districtId;
  private Long cityId;
  private String districtName;
}
