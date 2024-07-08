package com.kosa.realestate.admindivision.service;

import com.kosa.realestate.admindivision.model.dto.AutoSearchDTO;
import java.util.List;
import com.kosa.realestate.admindivision.model.dto.CityDTO;
import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.admindivision.model.dto.NeighborhoodDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * IAdminDivisionService
 *
 * @author 오동건
 */
public interface IAdminDivisionService {

  // 행정구역 조회
  List<CityDTO> findCityList();

  // 자치구 조회
  List<DistrictDTO> findDistrictList(Long cityId);

  // 행정동 조회
  List<NeighborhoodDTO> findNeighborhoodList(Long districtId);


  // 자동 완성 조회
  List<AutoSearchDTO> findAutoSearshList();
}
