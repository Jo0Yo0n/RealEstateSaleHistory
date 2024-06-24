package com.kosa.realestate;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.realestate.realestates.model.RealEstateSale;
import com.kosa.realestate.realestates.model.RealEstateWithSale;
import com.kosa.realestate.realestates.service.IRealEstateDetailService;

@SpringBootTest
class RealEstateDetailTest {
  @Autowired
  IRealEstateDetailService realEstateDetailService;
  
  @Test
  void detailTest() {
    List<RealEstateWithSale> realEstateDetail = realEstateDetailService.getRealEstateDetail(7723);
    System.out.println(realEstateDetail);
  }
  
  @Test
  void detailPriceTest() {
    List<RealEstateSale> realEstatePirce = realEstateDetailService.getRealEstatePrice(3313);
    System.out.println(realEstatePirce);
  }
}
