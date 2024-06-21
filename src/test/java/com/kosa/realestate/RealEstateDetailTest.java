package com.kosa.realestate;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}
