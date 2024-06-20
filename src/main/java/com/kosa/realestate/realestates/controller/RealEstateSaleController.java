package com.kosa.realestate.realestates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kosa.realestate.realestates.service.IRealEstateSaleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RealEstateSaleController {

  private final IRealEstateSaleService realEstateSaleService;

  @GetMapping("/estate/count")
  public String estateCount(
      @RequestParam(value = "realEstateId", required = false, defaultValue = "0") int realEstateId,
      Model model) {
    if (realEstateId == 0) {
      model.addAttribute("count",realEstateSaleService.getRealEstateSaleCount());
    } 
    else {
      model.addAttribute("count",realEstateSaleService.getRealEstateSaleCount(realEstateId));
    }
    return "estate/count";
  }
}
