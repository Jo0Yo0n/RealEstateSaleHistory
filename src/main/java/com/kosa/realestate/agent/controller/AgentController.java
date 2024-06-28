package com.kosa.realestate.agent.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import com.kosa.realestate.users.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentController {
  private final IAgentService agentService;
  private final IAdminDivisionService admindivisionService;
  private final IUserService userService;

  // 중개사 마이페이지
  @GetMapping("/agentpage")
  public String agentPage(AgentCreateFormDTO form) {
    return "agent-page-tab";
  }

  // 매물 등록 폼
  // 실제 로그인 후 유저 아이디 받아서 테스트 해봐야 함
  @GetMapping("/createform")
  public String createForm(Principal principal, Model model) {
    /* 유저 권환 확인
     * 테스트 위해 주석 처리
     * if (principal == null) { 
     * return "access_denied"; 
     * }
    
    
    String email = principal.getName();
    UserDTO userDTO = userService.findUserByEmail(email);
    model.addAttribute("user", userDTO);*/
    model.addAttribute("AgentCreateFormDTO", new AgentCreateFormDTO());
    
    return "create-sales-form";
  }

  // 매물 등록 폼
  @PostMapping("/createform")
  public String createForm(@Valid AgentCreateFormDTO form, BindingResult bindingResult, Model model) throws Exception {
      if (bindingResult.hasErrors()) {
          return "create-sales-form";
      }
      // 테스트를 위한 유저 아이디 하드 코딩
      form.setUserId((long)21);
      Integer realEstateId = agentService.getRealEstateId(form.getAddressStreet());
      
      // 부동산 아이디가 0이면 존재하지 않는 부동산
      if (realEstateId == 0) {
          // 
          agentService.insertRealEstate(form);
          agentService.insertRealEstateSale(form);
      } else {
          // 이미 등록된 매물이므로 매매 기록 데이터에만 삽입
          agentService.insertRealEstateSale(form);
      }

      // 모델에 새로운 폼 객체를 추가하여 폼을 초기화합니다.
      model.addAttribute("form", new AgentCreateFormDTO());
      
      // 등록된 매물 리스트 페이지로 리다이렉트합니다.
      return "redirect:/agent/registeredlist";
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
  
  // 중개사 마이페이지 등록 매물 리스트 조회
  // 페이징 처리 필요
  @GetMapping("/registeredlist")
  public ResponseEntity<List<AgentCreateFormDTO>> getRegisteredList(
        Model model,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "6") int size) {
      Long userId = 21L; // 테스트를 위해 userId를 직접 설정
      int totalSalesCount = agentService.getTotalRegisteredListCount(userId); // 전체페이지를 위해 유저 작성 리스트 카운트
      model.addAttribute("totalSalesCount", totalSalesCount);
      int totalPages = (int) Math.ceil((double) totalSalesCount / size);
      List<AgentCreateFormDTO> registeredList = agentService.getRegisteredList(userId, page, size);
      return ResponseEntity.ok(registeredList);
  }
};
