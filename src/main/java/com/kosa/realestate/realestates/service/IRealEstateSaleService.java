package com.kosa.realestate.realestates.service;

import java.util.List;
import java.util.Map;
<<<<<<< HEAD
import com.kosa.realestate.realestates.model.RealEstate;
=======

>>>>>>> 6aac81bb9e284b715efacedbe7d4bf108b2c5540
import com.kosa.realestate.realestates.model.RealEstateWithSale;

public interface IRealEstateSaleService {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  List<RealEstateWithSale> selectRealEstateWithSales(int realEstateId, int pageNum, int pageSize);
  
  List<Map<String, Object>> getAllDestrictId();

}
