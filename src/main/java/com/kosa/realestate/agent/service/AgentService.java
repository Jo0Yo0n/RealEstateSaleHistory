package com.kosa.realestate.agent.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;
import com.kosa.realestate.agent.repository.AgentRepository;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService implements IAgentService {
  
  private final AgentRepository agentRepository;
  
  @Override
  public Integer getRealEstateId(String addressStreet) {
      Integer realEstateId = agentRepository.getRealEstateId(addressStreet);
      return realEstateId != null ? realEstateId : 0;
  }
  
  @Override
  public void insertRealEstate(AgentCreateFormDTO form) {
    agentRepository.insertRealEstate(form);
  }

  @Override
  public void insertRealEstateSale(AgentCreateFormDTO form) {
    agentRepository.insertRealEstateSale(form);
  }

  @Transactional(rollbackFor = Exception.class)
  public void processRealEstateAndSales(AgentCreateFormDTO form) throws Exception {
    try {
      insertRealEstate(form);
      insertRealEstateSale(form);
    } catch (Exception e) {
      // 예외 처리
      throw new Exception("Failed to process real estate and sales", e);
    }
  }

  @Override
  public void updateRealEstateSale(AgentCreateFormDTO form) {
    agentRepository.updateRealEstateSale(form);
  }

  @Override
  public void deleteRealEstateSale(int salesId) {
    agentRepository.deleteRealEstateSale(salesId);
  }

  // 페이징 처리 해야 함
  @Override
  public List<AgentCreateFormDTO> getRegisteredList(Long userId, int page, int size) {
      int offset = (page - 1) * size;
      return agentRepository.getRegisteredList(userId, offset, size);
  }
  
  @Override
  public int getTotalRegisteredListCount(Long userId) {
      return agentRepository.getTotalRegisteredListCount(userId);
  }

}
