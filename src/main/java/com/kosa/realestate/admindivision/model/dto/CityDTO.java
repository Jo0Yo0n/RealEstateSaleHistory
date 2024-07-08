package com.kosa.realestate.admindivision.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * CityDTO
 *
 * @author 오동건
 */
@Getter
@Setter
@Builder
public class CityDTO {

  private Long cityId;
  private String cityName;
}
