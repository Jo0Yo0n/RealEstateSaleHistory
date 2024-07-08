package com.kosa.realestate.admindivision.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DistrictDTO
 *
 * @author 오동건
 */
@Getter
@Setter
@Builder
public class DistrictDTO {

  private Long districtId;
  private Long cityId;
  private String districtName;
}
