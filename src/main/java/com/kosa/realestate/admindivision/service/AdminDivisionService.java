package com.kosa.realestate.admindivision.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.kosa.realestate.admindivision.model.dto.CityDTO;
import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.admindivision.model.dto.NeighborhoodDTO;
import com.kosa.realestate.admindivision.model.entity.City;
import com.kosa.realestate.admindivision.model.entity.District;
import com.kosa.realestate.admindivision.model.entity.Neighborhood;
import com.kosa.realestate.admindivision.repository.AdminDivisionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDivisionService implements IAdminDivisionService {

  private final AdminDivisionRepository adminDivisionRepository;


  // 행정구역 조회
  public List<CityDTO> findCityList() {

    List<City> cityList = adminDivisionRepository.selectCityList();

    return cityList.stream()
        .map(city -> CityDTO.builder()
            .cityId(city.getCityId())
            .cityName(city.getCityName())
            .build())
        .collect(Collectors.toList());
  }
  // 자치구 조회
  public List<DistrictDTO> findDistrictList(Long cityId) {

    List<District> districtList = adminDivisionRepository.selectDistrictList(cityId);
    
    return districtList.stream()
        .map(district -> DistrictDTO.builder()
            .districtId(district.getDistrictId())
            .cityId(district.getCityId())
            .districtName(district.getDistrictName())
            .build())
        .collect(Collectors.toList());
  }


  // 행정동 조회
  public List<NeighborhoodDTO> findNeighborhoodList(Long districtId) {
    
    List<Neighborhood> neighborhoodList = adminDivisionRepository.selectNeighborhoodList(districtId);

    return neighborhoodList.stream()
        .map(neighborhood -> NeighborhoodDTO.builder()
            .neighborhoodId(neighborhood.getNeighborhoodId())
            .districtId(neighborhood.getDistrictId())
            .neighborhoodName(neighborhood.getNeighborhoodName())
            .build())
        .collect(Collectors.toList());
  }
}
