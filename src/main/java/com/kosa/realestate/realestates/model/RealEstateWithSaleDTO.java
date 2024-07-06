package com.kosa.realestate.realestates.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * @author 백재우
 */
@Getter
@Setter
@ToString
public class RealEstateWithSaleDTO {

  private RealEstateDTO realEstate;
  private RealEstateSaleDTO realEstateSale;
  private RealEstateExtraDTO estateExtra;
}
