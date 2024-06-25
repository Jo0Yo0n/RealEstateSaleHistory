package com.kosa.realestate.realestates.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;

@Mapper
public interface RealEstateSaleRepository {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  
  List<RealEstateWithSaleDTO> selectRealEstateWithSales();
  List<RealEstateWithSaleDTO> selectRealEstateWithSales(Map<String, Object> param);
  
  List<Map<String, Object>> getAllDestrictId();
  
  List<RealEstateWithSaleDTO> getRealEstateDetail(int salesId);
  List<RealEstateSaleDTO> getRealEstatePrice(int salesId);
}