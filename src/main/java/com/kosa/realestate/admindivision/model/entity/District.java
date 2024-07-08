package com.kosa.realestate.admindivision.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * District
 *
 * @author 오동건
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class District {

  private Long districtId;
  private Long cityId;
  private String districtName;
}
