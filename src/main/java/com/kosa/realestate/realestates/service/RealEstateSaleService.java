package com.kosa.realestate.realestates.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kosa.realestate.realestates.model.RealEstateWithSale;
import com.kosa.realestate.realestates.repository.IRealEstateSaleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RealEstateSaleService implements IRealEstateSaleService {

  private final IRealEstateSaleRepository estateSaleRepository; 
  
  @Override
  public int getRealEstateSaleCount() {
    return estateSaleRepository.getRealEstateSaleCount();
  }

  @Override
  public int getRealEstateSaleCount(int realEstateId) {
    return estateSaleRepository.getRealEstateSaleCount(realEstateId);
  }

  @Override
  public List<RealEstateWithSale> selectRealEstateWithSales(int realEstateId, int pageNum,
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
  public List<Map<String, Object>> getAllDestrictId() {
    return estateSaleRepository.getAllDestrictId();
  }



  
}