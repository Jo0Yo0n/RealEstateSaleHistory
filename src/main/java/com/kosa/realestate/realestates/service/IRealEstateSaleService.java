package com.kosa.realestate.realestates.service;

import java.util.List;
import com.kosa.realestate.realestates.model.RealEstateWithSale;

public interface IRealEstateSaleService {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  List<RealEstateWithSale> selectRealEstateWithSales(int realEstateId);
}
