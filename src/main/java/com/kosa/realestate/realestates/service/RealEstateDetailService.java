package com.kosa.realestate.realestates.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kosa.realestate.realestates.model.RealEstateSale;
import com.kosa.realestate.realestates.model.RealEstateWithSale;
import com.kosa.realestate.realestates.repository.IRealEstateDetailRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RealEstateDetailService implements IRealEstateDetailService {

  private final IRealEstateDetailRepository realEstateDetailRepository; 
  
  @Override
  public List<RealEstateWithSale> getRealEstateDetail(int salesId) {
    return realEstateDetailRepository.getRealEstateDetail(salesId);
  }

  @Override
  public List<RealEstateSale> getRealEstatePrice(int salesId) {
    return realEstateDetailRepository.getRealEstatePrice(salesId);
  }
}
