package com.kosa.realestate.realestates.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
<<<<<<< HEAD
import com.kosa.realestate.realestates.model.RealEstate;
import com.kosa.realestate.realestates.model.RealEstateSale;
=======

>>>>>>> 6aac81bb9e284b715efacedbe7d4bf108b2c5540
import com.kosa.realestate.realestates.model.RealEstateWithSale;

@Mapper
public interface IRealEstateSaleRepository {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  
  List<RealEstateWithSale> selectRealEstateWithSales();
  List<RealEstateWithSale> selectRealEstateWithSales(Map<String, Object> param);
  
  List<Map<String, Object>> getAllDestrictId();

}
