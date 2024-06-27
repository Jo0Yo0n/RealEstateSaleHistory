package com.kosa.realestate.statistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kosa.realestate.statistics.model.dto.DistrictAvgSaleDTO;
import com.kosa.realestate.statistics.model.dto.NeighborhoodMostFavoriteDTO;
import com.kosa.realestate.statistics.model.dto.RealEstateChangeFiveYearDTO;
import com.kosa.realestate.statistics.repository.StatisticsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService implements IStatisticsService{

  private final StatisticsRepository statisticsRepository;
  
  
  // 각 지역구 기준으로 전체 매매 평균 순위
  public List<DistrictAvgSaleDTO> findAvgSaleDistrict() {
    
    return statisticsRepository.selectAvgSaleDistrict();
  }
  
  
  // 즐겨찾기가 많이 된 동 순위 (시, 구, 동)
  public List<NeighborhoodMostFavoriteDTO> findMostFavoriteNeighborhood() {
    
    return statisticsRepository.selectMostFavoriteNeighborhood();
  }
  
  
  // 5년간 부동산 등락 차이
  public List<RealEstateChangeFiveYearDTO> findRealEstateChangeFive(String orderBy) {
    
    return statisticsRepository.selectRealEstateChangeFive(orderBy);
  }
}
