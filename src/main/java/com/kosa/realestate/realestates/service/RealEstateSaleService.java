package com.kosa.realestate.realestates.service;

import com.kosa.realestate.realestates.model.NewRealEstateSaleDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;
import com.kosa.realestate.realestates.repository.RealEstateSaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RealEstateSaleService implements IRealEstateSaleService {

  private final RealEstateSaleRepository estateSaleRepository; 
  
  @Override
  public int getRealEstateSaleCount() {
    return estateSaleRepository.getRealEstateSaleCount();
  }

  @Override
  public int getRealEstateSaleCount(int realEstateId) {
    return estateSaleRepository.getRealEstateSaleCount(realEstateId);
  }
  
  @Override
  public int estateCountByCriteria(String districtName, String neighborhoodName, Double minPrice,
      Double maxPrice, int realEstateId) {
    Map<String, Object> salePrice = new HashMap();
    salePrice.put("min", minPrice);
    salePrice.put("max", maxPrice);

    return estateSaleRepository.estateCountByCriteria(districtName, neighborhoodName, salePrice, realEstateId);
  }
  
  @Override
  public List<RealEstateWithSaleDTO> selectRealEstateWithSales(int realEstateId, int pageNum,
      int pageSize) {
    int offset = (pageNum - 1) * pageSize;
    int limit = pageSize;
    
    Map<String, Object> params = new HashMap<>();
    params.put("realEstateId", realEstateId);
    params.put("offset", offset);
    params.put("limit", limit);
    
    return estateSaleRepository.selectRealEstateWithSales(params);
  }


  @Override
  public List<RealEstateWithSaleDTO> getRealEstateDetail(int salesId) {
    return estateSaleRepository.getRealEstateDetail(salesId);
  }

  @Override
  public List<RealEstateSaleDTO> getRealEstatePrice(int salesId) {
    return estateSaleRepository.getRealEstatePrice(salesId);
  }



  @Override
  public List<RealEstateWithSaleDTO> selectRealEstateWithSalesByCondition(String districtName,
      String neighborhoodName, Double minPrice, Double maxPrice, int currentPage, int realEstateId) {

    final int limit = 6;
    int offset = (currentPage - 1) * limit;
    Map<String, Object> salePrice = new HashMap();
    salePrice.put("min", minPrice);
    salePrice.put("max", maxPrice);


    return estateSaleRepository.selectRealEstateWithSalesByCondition(districtName, neighborhoodName, salePrice,   offset, limit, realEstateId);
  }


  // 최근 등록 매물 (메인화면)
  public List<NewRealEstateSaleDTO> findNewRealEstateSale() {

    return estateSaleRepository.selectNewRealEstateSale();
  }
  
  //아파트 중복 없이 검색
  @Override
  public List<Map<String, Object>> selectRealEstate(int districtName, String neighborhoodName,
      int minPrice, int maxPrice, int minExclusiveSize, int maxExclusiveSize, int currentPage) {
    final int limit = 6;
    int offset = (currentPage - 1) * limit;
    
    Map<String, Object> salePrice = new HashMap();
    salePrice.put("min", minPrice);
    salePrice.put("max", maxPrice);
    Map<String, Object> exclusiveArea = new HashMap();
    exclusiveArea.put("min", minExclusiveSize);
    exclusiveArea.put("max", maxExclusiveSize);
    return estateSaleRepository.selectRealEstate(districtName, neighborhoodName, salePrice, exclusiveArea,  offset, limit);
  }
  
  //아파트 중복 없이 총 개수 조회
  @Override
  public int selectRealEstateCount(int districtName, String neighborhoodName, int minPrice,
      int maxPrice, int minExclusiveSize, int maxExclusiveSize) {
    Map<String, Object> salePrice = new HashMap();
    salePrice.put("min", minPrice);
    salePrice.put("max", maxPrice);
    Map<String, Object> exclusiveArea = new HashMap();
    exclusiveArea.put("min", minExclusiveSize);
    exclusiveArea.put("max", maxExclusiveSize);
    return estateSaleRepository.selectRealEstateCount(districtName, neighborhoodName, salePrice, exclusiveArea);
  }


}