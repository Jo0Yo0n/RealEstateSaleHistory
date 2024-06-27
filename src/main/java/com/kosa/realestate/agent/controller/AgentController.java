package com.kosa.realestate.agent.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kosa.realestate.admindivision.model.dto.CityDTO;
import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.admindivision.model.dto.NeighborhoodDTO;
import com.kosa.realestate.admindivision.service.IAdminDivisionService;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;
import com.kosa.realestate.agent.service.IAgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentController {
  private final IAgentService agentService;
  private final IAdminDivisionService admindivisionService;

  @GetMapping("/agentpage")
  public String agentPage(AgentCreateFormDTO form) {
    return "agent-page-tab";
  }

  @GetMapping("/createform")
  public String createForm(AgentCreateFormDTO form) {
    return "create-sales-form";
  }

  @PostMapping("/createform")
  public String createForm(@Valid AgentCreateFormDTO form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "create-sales-form";
    }

    Integer realEstateId = agentService.getRealEstateId(form.getAddressStreet());

    if (realEstateId == null) {
      agentService.insertRealEstate(form); // 도로명 주소로 매물이 없으면 삽입
      realEstateId = agentService.getRealEstateId(form.getAddressStreet()); // 삽입 후 realEstateId 다시
                                                                            // 가져오기
    }

    return "create-sales-form";
  }

  // 시 리스트를 제공하는 엔드포인트
  @GetMapping("/cities")
  public ResponseEntity<List<CityDTO>> cityList() {
    return ResponseEntity.ok(admindivisionService.findCityList());
  }

  // 구 리스트를 제공하는 엔드포인트
  @GetMapping("/cities/districts")
  public ResponseEntity<List<DistrictDTO>> districtList(@RequestParam("cityId") Long cityId) {
    return ResponseEntity.ok(admindivisionService.findDistrictList(cityId));
  }

  // 동 리스트를 제공하는 엔드포인트
  @GetMapping("/districts/neighborhoods")
  public ResponseEntity<List<NeighborhoodDTO>> neighborhoodList(
      @RequestParam("districtId") Long districtId) {
    return ResponseEntity.ok(admindivisionService.findNeighborhoodList(districtId));
  }
};
