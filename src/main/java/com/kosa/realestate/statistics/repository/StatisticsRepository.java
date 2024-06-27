package com.kosa.realestate.statistics.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kosa.realestate.statistics.model.dto.DistrictAvgSaleDTO;
import com.kosa.realestate.statistics.model.dto.NeighborhoodMostFavoriteDTO;
import com.kosa.realestate.statistics.model.dto.RealEstateChangeFiveYearDTO;

@Mapper
@Repository
public interface StatisticsRepository {
  
  // 각 지역구 기준으로 전체 매매 평균 순위
  List<DistrictAvgSaleDTO> selectAvgSaleDistrict();
  
  // 즐겨찾기가 많이 된 동 순위 (시, 구, 동)
  List<NeighborhoodMostFavoriteDTO> selectMostFavoriteNeighborhood();
  
  // 즐겨찾기가 많이 된 동 순위 (시, 구, 동)
  List<RealEstateChangeFiveYearDTO> selectRealEstateChangeFive(String orderBy);
}
