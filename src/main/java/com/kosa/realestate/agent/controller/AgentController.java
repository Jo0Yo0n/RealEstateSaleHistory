package com.kosa.realestate.agent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;
import com.kosa.realestate.agent.service.IAgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentController {
  private final IAgentService agentService;
  
  @GetMapping("/createform")
  public String createForm(AgentCreateFormDTO form) {
    return "agent_create_form";
  }
  
  @PostMapping("/createform")
  public String createForm(@Valid AgentCreateFormDTO form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "signup_form";
    }
    
    Integer realEstateId = agentService.getRealEstateId(form.getAddressStreet());
    
    if (realEstateId == null) {
      agentService.insertRealEstate(form); // 도로명 주소로 매물이 없으면 삽입
      realEstateId = agentService.getRealEstateId(form.getAddressStreet()); // 삽입 후 realEstateId 다시 가져오기
    }
    
    return "signup_form";
  }
};
