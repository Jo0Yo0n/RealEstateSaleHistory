package com.kosa.realestate.admindivision.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Neighborhood
 *
 * @author 오동건
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Neighborhood {

  private Long neighborhoodId;
  private Long districtId;
  private String neighborhoodName;
}
