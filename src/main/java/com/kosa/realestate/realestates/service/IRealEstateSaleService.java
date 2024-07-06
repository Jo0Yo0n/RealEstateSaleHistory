package com.kosa.realestate.realestates.service;

import com.kosa.realestate.realestates.model.NewRealEstateSaleDTO;
import java.util.List;
import java.util.Map;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;
/*
 * @author 백재우
 */
public interface IRealEstateSaleService {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  int estateCountByCriteria(String districtName, String neighborhoodName, Double minPrice, Double maxPrice, int realEstateId);
  
  List<RealEstateWithSaleDTO> selectRealEstateWithSales(int realEstateId, int pageNum, int pageSize);
  

  // 검색조건으로 매매기록 조회하기
  List<RealEstateWithSaleDTO> selectRealEstateWithSalesByCondition(String districtName, String neighborhoodName,
      Double minPrice, Double maxPrice, int currentPage, int realEstateId);

  List<RealEstateWithSaleDTO> getRealEstateDetail(int salesId);
  List<RealEstateSaleDTO> getRealEstatePrice(int salesId);
  
  //아파트 중복 없이 검색
  List<Map<String, Object>> selectRealEstate(int districtName, String neighborhoodName, int minPrice, int maxPrice, int minExclusiveSize, int maxExclusiveSize, int currentPage);
  int selectRealEstateCount(int districtName, String neighborhoodName, int minPrice, int maxPrice, int minExclusiveSize, int maxExclusiveSize);
  /*
   * @author 강안수
   */
  // 최근 등록 매물 (메인화면)
  List<NewRealEstateSaleDTO> findNewRealEstateSale();
}