package com.kosa.realestate.realestates.service;

import java.util.List;
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
  public List<RealEstateWithSale> selectRealEstateWithSales(int realEstateId) {
    return estateSaleRepository.selectRealEstateWithSales(realEstateId);
  }

  
}
