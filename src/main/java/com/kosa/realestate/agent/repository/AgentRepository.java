package com.kosa.realestate.agent.repository;

import org.apache.ibatis.annotations.Mapper;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;

@Mapper
public interface AgentRepository {
  int getRealEstateId(String addressStreet);
  void insertRealEstate(AgentCreateFormDTO form);
  void insertRealEstateSale(AgentCreateFormDTO form);
  void updateRealEstateSale(AgentCreateFormDTO form);
  void deleteRealEstateSale(int salesId);
}
