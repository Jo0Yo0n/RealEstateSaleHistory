package com.kosa.realestate.realestates.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RealEstateWithSale {

  private RealEstate realEstate;
  private RealEstateSale realEstateSale;
}
