package com.kosa.realestate.admindivision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NeighborhoodDTO {

  private Long neighborhoodId;
  private Long districtId;
  private String neighborhoodName;
}
