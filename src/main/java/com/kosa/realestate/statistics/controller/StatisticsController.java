package com.kosa.realestate.statistics.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosa.realestate.statistics.model.dto.DistrictAvgSaleDTO;
import com.kosa.realestate.statistics.model.dto.NeighborhoodMostFavoriteDTO;
import com.kosa.realestate.statistics.model.dto.RealEstateChangeFiveYearDTO;
import com.kosa.realestate.statistics.service.IStatisticsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

  private final IStatisticsService statisticsService;

  @GetMapping()
  public String test() {

    return "user_main_page";
  }
  
  
  // 각 지역구 기준으로 전체 매매 평균 순위
  @GetMapping("/districts/avg-sales-prices")
  public ResponseEntity<List<DistrictAvgSaleDTO>> avgSaleDistrictList() {

    List<DistrictAvgSaleDTO> districtAvgSaleList = statisticsService.findAvgSaleDistrict();

    return ResponseEntity.ok(districtAvgSaleList);
  }


  // 즐겨찾기가 많이 된 동 순위 (시, 구, 동)
  @GetMapping("/neighborhoods/most-favorite")
  public ResponseEntity<List<NeighborhoodMostFavoriteDTO>> mostFavoriteNeighborhoodList() {
    
    List<NeighborhoodMostFavoriteDTO> neighborhoodMostFavoriteList = statisticsService.findMostFavoriteNeighborhood();
    
    return ResponseEntity.ok(neighborhoodMostFavoriteList);
  }
  
  
  // 5년간 부동산 등락 차이
  @GetMapping("/real-estates/changes/five-years")
  public ResponseEntity<List<RealEstateChangeFiveYearDTO>> realEstateChangeFiveYearList(
		  @RequestParam("orderBy") String orderBy) {
    
    List<RealEstateChangeFiveYearDTO> realEstateChangeFiveYearList = 
        statisticsService.findRealEstateChangeFive(orderBy);
    
    return ResponseEntity.ok(realEstateChangeFiveYearList);
  }
}
