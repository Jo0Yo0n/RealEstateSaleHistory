package com.kosa.realestate.statistics.service;

import java.util.List;

import com.kosa.realestate.statistics.model.dto.DistrictAvgSaleDTO;
import com.kosa.realestate.statistics.model.dto.NeighborhoodMostFavoriteDTO;
import com.kosa.realestate.statistics.model.dto.RealEstateChangeFiveYearDTO;

public interface IStatisticsService {

  // 각 지역구 기준으로 전체 매매 평균 순위
  List<DistrictAvgSaleDTO> findAvgSaleDistrict();
  
  // 즐겨찾기가 많이 된 행정동 순위 (시, 구, 동)
  List<NeighborhoodMostFavoriteDTO> findMostFavoriteNeighborhood();
  
  // 5년간 부동산 등락 차이
  List<RealEstateChangeFiveYearDTO> findRealEstateChangeFive(String orderByForm);
}
