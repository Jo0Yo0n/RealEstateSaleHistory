package com.kosa.realestate.realestates.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.realestate.realestates.model.RealEstateWithSale;
import com.kosa.realestate.realestates.service.IRealEstateSaleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/real-estate")
public class RealEstateSaleController {

  private final IRealEstateSaleService realEstateSaleService;

  @GetMapping("/count")
  @ResponseBody
  public int estateCount(
      @RequestParam(value = "realEstateId", required = false, defaultValue = "0") int realEstateId
      ) {
    return realEstateId == 0 ? realEstateSaleService.getRealEstateSaleCount() : realEstateSaleService.getRealEstateSaleCount(realEstateId);
  }
  
  @GetMapping("/select")
  public String estateSelect(Model model) {
     model.addAttribute("districtList",realEstateSaleService.getAllDestrictId());
//     model.addAttribute("",realEstateSaleService.)
    return "estate/map";
  }
  
  @GetMapping("/search")
  @ResponseBody
  public List<RealEstateWithSale> getEstateList(
      @RequestParam(value="realEstateId" ,defaultValue = "0") int realEstateId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
      ){
    return realEstateSaleService.selectRealEstateWithSales(realEstateId,page,size);
  }
  
  
  
  
}
