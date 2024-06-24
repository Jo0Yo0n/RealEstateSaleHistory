package com.kosa.realestate.realestates.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kosa.realestate.realestates.model.RealEstateSale;
import com.kosa.realestate.realestates.model.RealEstateWithSale;
import com.kosa.realestate.realestates.service.IRealEstateDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
  private final IRealEstateDetailService realEstateDetailService;
  
  @GetMapping("/budongsan")
  public String budongsan(Model model) {
    return "map";
  }
  
  @GetMapping("/budongsan/detail/{salesId}")
  public String detail(@PathVariable("salesId") int salesId, Model model) {
      // realEstateId에 맞는 데이터를 가져와서 모델에 추가하는 로직
      List<RealEstateWithSale> realEstateDetail = realEstateDetailService.getRealEstateDetail(salesId);
      List<RealEstateSale> realEstatePrice = realEstateDetailService.getRealEstatePrice(salesId);
      model.addAttribute("realEstateDetail", realEstateDetail);
      model.addAttribute("realEstatePrice", realEstatePrice);
      return "detail";
  }
}
