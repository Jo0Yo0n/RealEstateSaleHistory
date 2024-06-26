package com.kosa.realestate.realestates.service;

import java.util.List;
import java.util.Map;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;

public interface IRealEstateSaleService {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  List<RealEstateWithSaleDTO> selectRealEstateWithSales(int realEstateId, int pageNum, int pageSize);
  
  List<Map<String, Object>> getAllDestrictId();
  List<Map<String, Object>> getAllNeighborhood(int destrictId);
  // 검색조건으로 매매기록 조회하기
  List<RealEstateWithSaleDTO> selectRealEstateWithSalesByCondition(String districtName, String neighborhoodName, int minPrice, int maxPrice, int minExclusiveSize, int maxExclusiveSize);

  List<RealEstateWithSaleDTO> getRealEstateDetail(int salesId);
  List<RealEstateSaleDTO> getRealEstatePrice(int salesId);
}