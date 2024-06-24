package com.kosa.realestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.kosa.realestate.realestates.controller.RealEstateSaleController;
import com.kosa.realestate.realestates.model.RealEstateWithSale;
import com.kosa.realestate.realestates.service.IRealEstateSaleService;
import com.kosa.realestate.realestates.service.RealEstateSaleService;

@SpringBootTest
class RealEstateSaleHistoryApplicationTests2 {
 
  @Autowired
  RealEstateSaleController controller;
  
  @Test
  void selectList() {
    System.out.println(controller.estateCount(0));
  }
}
