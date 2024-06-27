package com.kosa.realestate.agent.service;

import com.kosa.realestate.agent.model.AgentCreateFormDTO;

public interface IAgentService {
  int getRealEstateId(String addressStreet);
  void insertRealEstate(AgentCreateFormDTO form);
  void insertRealEstateSale(AgentCreateFormDTO form);
  void processRealEstateAndSales(AgentCreateFormDTO form) throws Exception;
  void updateRealEstateSale(AgentCreateFormDTO form);
  void deleteRealEstateSale(int salesId);
}
