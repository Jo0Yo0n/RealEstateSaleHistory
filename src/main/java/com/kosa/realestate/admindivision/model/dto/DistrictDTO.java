package com.kosa.realestate.admindivision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistrictDTO {

  private Long districtId;
  private Long cityId;
  private String districtName;
}
