package com.kosa.realestate.realestates.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.kosa.realestate.realestates.model.NewRealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;

@Mapper
public interface RealEstateSaleRepository {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
  int estateCountByCriteria(@Param("districtName") int districtName, @Param("neighborhoodName")String neighborhoodName,
      @Param("salePrice") Map<String, Object> salePrice, @Param("exclusiveArea") Map<String, Object> exclusiveArea);
  
  List<RealEstateWithSaleDTO> selectRealEstateWithSales();
  List<RealEstateWithSaleDTO> selectRealEstateWithSales(Map<String, Object> param);

  
  //검색조건으로 매매기록 조회하기
  List<RealEstateWithSaleDTO> selectRealEstateWithSalesByCondition(@Param("districtName") int districtName,
      @Param("neighborhoodName")String neighborhoodName,@Param("salePrice") Map<String, Object> salePrice,
      @Param("exclusiveArea") Map<String, Object> exclusiveArea, @Param("offset") int offset, @Param("limit") int limit, @Param("realEstateId") int realEstateId);
  
  List<RealEstateWithSaleDTO> getRealEstateDetail(int salesId);
  List<RealEstateSaleDTO> getRealEstatePrice(int salesId);
  
  //아파트 중복 없이 검색
  List<Map<String, Object>> selectRealEstate(@Param("districtName") int districtName,
      @Param("neighborhoodName")String neighborhoodName,@Param("salePrice") Map<String, Object> salePrice,
      @Param("exclusiveArea") Map<String, Object> exclusiveArea, @Param("offset") int offset, @Param("limit") int limit);
  //아파트 중복 없이 총 개수 
  int selectRealEstateCount(@Param("districtName") int districtName,
      @Param("neighborhoodName")String neighborhoodName,@Param("salePrice") Map<String, Object> salePrice,
      @Param("exclusiveArea") Map<String, Object> exclusiveArea);
  
  // 최근 등록 매물 (메인화면)
  List<NewRealEstateSaleDTO> selectNewRealEstateSale();
}