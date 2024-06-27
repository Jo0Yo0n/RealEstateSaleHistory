package com.kosa.realestate.agent.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;
import com.kosa.realestate.agent.repository.AgentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService implements IAgentService {
  
  private final AgentRepository agentRepository;
  
  @Override
  public int getRealEstateId(String addressStreet) {
    return agentRepository.getRealEstateId(addressStreet);
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

}
