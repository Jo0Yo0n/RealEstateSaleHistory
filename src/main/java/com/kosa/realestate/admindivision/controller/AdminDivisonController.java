package com.kosa.realestate.admindivision.controller;

import com.kosa.realestate.admindivision.model.dto.AutoSearchDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kosa.realestate.admindivision.model.dto.CityDTO;
import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.admindivision.model.dto.NeighborhoodDTO;
import com.kosa.realestate.admindivision.service.IAdminDivisionService;
import lombok.RequiredArgsConstructor;

/**
 * AdminDivisonController
 *
 * @author 오동건
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admindivison")
public class AdminDivisonController {

    private final IAdminDivisionService admindivisionService;

    // 행정구역 조회
    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> cityList() {

        return ResponseEntity.ok(admindivisionService.findCityList());
    }

    
    // 자치구 조회
    @GetMapping("/cities/districts")
    public ResponseEntity<List<DistrictDTO>> districtList(@RequestParam("cityId") Long cityId) {

        return ResponseEntity.ok(admindivisionService.findDistrictList(cityId));
    }

    
    // 행정동 조회
    @GetMapping("/districts/neighborhoods")
    public ResponseEntity<List<NeighborhoodDTO>> neighborhoodList(@RequestParam("districtId") Long districtId) {

        return ResponseEntity.ok(admindivisionService.findNeighborhoodList(districtId));
    }


    // 자동 완성 조회
    @GetMapping("/auto/search")
    public ResponseEntity<List<AutoSearchDTO>> autoSearshList() {
System.out.println("여기 컨트롤러");
        return ResponseEntity.ok(admindivisionService.findAutoSearshList());
    }
}
