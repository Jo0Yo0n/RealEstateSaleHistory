package com.kosa.realestate.admindivision.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * NeighborhoodDTO
 *
 * @author 오동건
 */
@Getter
@Setter
@Builder
public class NeighborhoodDTO {

  private Long neighborhoodId;
  private Long districtId;
  private String neighborhoodName;
}
