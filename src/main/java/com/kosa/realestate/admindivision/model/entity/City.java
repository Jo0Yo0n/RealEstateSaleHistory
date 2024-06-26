package com.kosa.realestate.admindivision.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {

  private Long cityId;
  private String cityName;
}
