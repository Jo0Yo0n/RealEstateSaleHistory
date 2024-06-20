package com.kosa.realestate.realestates.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kosa.realestate.realestates.model.RealEstateWithSale;

@Mapper
public interface IRealEstateSaleRepository {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  List<RealEstateWithSale> selectRealEstateWithSales();
  List<RealEstateWithSale> selectRealEstateWithSales(int realEstateId);
  
}
