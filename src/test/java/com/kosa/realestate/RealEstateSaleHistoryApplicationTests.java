package com.kosa.realestate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;
import com.kosa.realestate.realestates.service.IRealEstateSaleService;

@SpringBootTest
class RealEstateSaleHistoryApplicationTests {
 
  @Autowired
  IRealEstateSaleService estateSaleService;
  
  @Test
  void selectList() {
    List<RealEstateWithSaleDTO> list = estateSaleService.selectRealEstateWithSales(1, 7, 10);
    System.out.println(list);
    int result = list.size();
    assertEquals(2, result);
  }
}
