package com.kosa.realestate.realestates.service;

import org.springframework.stereotype.Service;
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

}
