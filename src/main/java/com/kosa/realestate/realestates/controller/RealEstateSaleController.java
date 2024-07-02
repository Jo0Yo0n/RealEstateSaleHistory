package com.kosa.realestate.realestates.controller;

import com.kosa.realestate.realestates.model.NewRealEstateSaleDTO;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.realestates.model.RealEstateWithSaleDTO;
import com.kosa.realestate.realestates.service.IRealEstateSaleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/realestate")
public class RealEstateSaleController {

  private final IRealEstateSaleService realEstateSaleService;
  
  @GetMapping("")
  public String estate() {
    return "map";
  }
  
  @PostMapping("/count")
  public ResponseEntity<?> estateCount (@RequestBody(required = false) Map<String, Object> searchCriteria) {
 // 지역구 조건
    Integer districtName = null;
    districtName = searchCriteria != null && searchCriteria.get("districtName") != null ? Integer.parseInt((String) searchCriteria.get("districtName")) : 0;
    // 동 조건의 여부
    String neighborhoodName = searchCriteria != null && searchCriteria.get("neighborhoodName") != null ? (String) searchCriteria.get("neighborhoodName") : null;
    // 가격 조건
    Integer minPrice = searchCriteria != null && searchCriteria.get("PriceMin") != null ? (Integer) searchCriteria.get("PriceMin") : null;
    Integer maxPrice = searchCriteria != null && searchCriteria.get("PriceMax") != null ? (Integer) searchCriteria.get("PriceMax") : null;
    //전용면적 조건
    Integer minExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMin") != null ? (Integer) searchCriteria.get("ExclusiveSizeMin") : null;
    Integer maxExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMax") != null ? (Integer) searchCriteria.get("ExclusiveSizeMax") : null;
    
    int result =  realEstateSaleService.selectRealEstateCount(districtName, neighborhoodName, minPrice, maxPrice, minExclusiveSize, maxExclusiveSize);
    return ResponseEntity.ok(result);
  }
  
  @PostMapping("/count/byCriteria")
  @ResponseBody
  public int estateCountByCriteria(@RequestBody(required = false) Map<String, Object> searchCriteria) {
    // 지역구 조건
    Integer districtName = null;
    districtName = searchCriteria != null && searchCriteria.get("districtName") != null ? Integer.parseInt((String) searchCriteria.get("districtName")) : 0;
    // 동 조건의 여부
    String neighborhoodName = searchCriteria != null && searchCriteria.get("neighborhoodName") != null ? (String) searchCriteria.get("neighborhoodName") : null;
    // 가격 조건
    Integer minPrice = searchCriteria != null && searchCriteria.get("PriceMin") != null ? (Integer) searchCriteria.get("PriceMin") : null;
    Integer maxPrice = searchCriteria != null && searchCriteria.get("PriceMax") != null ? (Integer) searchCriteria.get("PriceMax") : null;
    //전용면적 조건
    Integer minExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMin") != null ? (Integer) searchCriteria.get("ExclusiveSizeMin") : null;
    Integer maxExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMax") != null ? (Integer) searchCriteria.get("ExclusiveSizeMax") : null;
  
    return realEstateSaleService.estateCountByCriteria(districtName, neighborhoodName, minPrice, maxPrice, minExclusiveSize, maxExclusiveSize);
  }
  

  
  @GetMapping("/detail/{salesId}")
  public String detail(@PathVariable("salesId") int salesId, Model model) {
      // realEstateId에 맞는 데이터를 가져와서 모델에 추가하는 로직
      List<RealEstateWithSaleDTO> realEstateDetail = realEstateSaleService.getRealEstateDetail(salesId);
      List<RealEstateSaleDTO> realEstatePrice = realEstateSaleService.getRealEstatePrice(salesId);
      model.addAttribute("realEstateDetail", realEstateDetail);
      model.addAttribute("realEstatePrice", realEstatePrice);
      return "detail";
  }
  
  //검색 조건에 따른 매물 검색
  @PostMapping("/search/byCriteria")
  @ResponseBody
  public List<RealEstateWithSaleDTO> selectRealEstateWithSalesByCondition(
      @RequestBody(required = false) Map<String, Object> searchCriteria) {
    // 지역구 조건
    Integer districtName = null;
    districtName = searchCriteria != null && searchCriteria.get("districtName") != null ? Integer.parseInt((String) searchCriteria.get("districtName")) : 0;
    // 동 조건의 여부
    String neighborhoodName = searchCriteria != null && searchCriteria.get("neighborhoodName") != null ? (String) searchCriteria.get("neighborhoodName") : null;
    // 가격 조건
    Integer minPrice = searchCriteria != null && searchCriteria.get("PriceMin") != null ? (Integer) searchCriteria.get("PriceMin") : null;
    Integer maxPrice = searchCriteria != null && searchCriteria.get("PriceMax") != null ? (Integer) searchCriteria.get("PriceMax") : null;
    //전용면적 조건
    Integer minExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMin") != null ? (Integer) searchCriteria.get("ExclusiveSizeMin") : null;
    Integer maxExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMax") != null ? (Integer) searchCriteria.get("ExclusiveSizeMax") : null;
    //curremtPage
    Integer currentPage = searchCriteria != null && searchCriteria.get("page") != null ? (Integer) searchCriteria.get("page") : 1; // 기본값을 1로 설정
    Integer realEstateId = 0;
    return realEstateSaleService.selectRealEstateWithSalesByCondition(districtName, neighborhoodName, minPrice, maxPrice, minExclusiveSize, maxExclusiveSize, currentPage, realEstateId);
  }

  //아파트 중복 없이 검색
  @PostMapping("/search")
  @ResponseBody
  public ResponseEntity<List<Map<String, Object>>> selectRealEstate(@RequestBody(required = false) Map<String, Object> searchCriteria) {
    // 지역구 조건
    Integer districtName = null;
    districtName = searchCriteria != null && searchCriteria.get("districtName") != null ? Integer.parseInt((String) searchCriteria.get("districtName")) : 0;
    // 동 조건의 여부
    String neighborhoodName = searchCriteria != null && searchCriteria.get("neighborhoodName") != null ? (String) searchCriteria.get("neighborhoodName") : null;
    // 가격 조건
    Integer minPrice = searchCriteria != null && searchCriteria.get("PriceMin") != null ? (Integer) searchCriteria.get("PriceMin") : 0;
    Integer maxPrice = searchCriteria != null && searchCriteria.get("PriceMax") != null ? (Integer) searchCriteria.get("PriceMax") : 180;
    //전용면적 조건
    Integer minExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMin") != null ? (Integer) searchCriteria.get("ExclusiveSizeMin") : 0;
    Integer maxExclusiveSize = searchCriteria != null && searchCriteria.get("ExclusiveSizeMax") != null ? (Integer) searchCriteria.get("ExclusiveSizeMax") : 320;
    //curremtPage
    Integer currentPage = searchCriteria != null && searchCriteria.get("page") != null ? (Integer) searchCriteria.get("page") : 1; // 기본값을 1로 설정
    
    List<Map<String, Object>> results = realEstateSaleService.selectRealEstate(
        districtName, neighborhoodName, minPrice, maxPrice, minExclusiveSize, maxExclusiveSize, currentPage);

    return ResponseEntity.ok(results);  
    }
  
  // 최근 등록 매물 (메인화면)
  @GetMapping("/new")
  public ResponseEntity<List<NewRealEstateSaleDTO>> newRealEstateSaleList() {

    return ResponseEntity.ok(realEstateSaleService.findNewRealEstateSale());
  }
}