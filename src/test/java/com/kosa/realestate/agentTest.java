package com.kosa.realestate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;
import com.kosa.realestate.agent.service.IAgentService;

@SpringBootTest
class agentTest {

  @Autowired
  IAgentService agentService;
  
  @Test
  void getRealEstateId() {
    int realEstateId = agentService.getRealEstateId("양천로 656");
    System.out.println(realEstateId);
  }
  
  @Test
  void testProcessRealEstateAndSales() {
      AgentCreateFormDTO dto = new AgentCreateFormDTO();
      dto.setBuildingType(1);
      dto.setComplexName("예담주택");
      dto.setCityName("서울시");
      dto.setDistrictName("강남구");
      dto.setNeighborhoodName("화곡동");
      dto.setAddress("느그집 느그동 18번지");
      dto.setAddressStreet("느그로 18");
      dto.setSalePrice(12);
      dto.setConstructionYear(2020);
      dto.setContractDate("2020-02-04");
      dto.setBuildingName("102동");
      dto.setFloor("3층");
      dto.setExclusiveArea(85);

      try {
        agentService.processRealEstateAndSales(dto);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      int generatedRealEstateId = dto.getRealEstateId();
      int generatedSalesId = dto.getSalesId();
      
      System.out.println("Inserted realEstateId: " + generatedRealEstateId);
      System.out.println("Inserted salesId: " + generatedSalesId);
  }
  
  @Test
  @Transactional
  void insert1() {
    AgentCreateFormDTO dto = new AgentCreateFormDTO();
    dto.setBuildingType(1);
    dto.setComplexName("예담주택");
    dto.setCityName("서울시");
    dto.setDistrictName("강남구");
    dto.setNeighborhoodName("화곡동");
    dto.setAddress("느그집 느그동 18번지");
    dto.setAddressStreet("느그로 18");
    dto.setSalePrice(12);
    dto.setConstructionYear(2020);
    dto.setContractDate(null);
    dto.setBuildingName("102동");
    dto.setFloor("3층");
    dto.setExclusiveArea(85);

    // Call service methods in one transaction
    agentService.insertRealEstate(dto);

    // Output generated IDs
    int generatedRealEstateId = dto.getRealEstateId();
    System.out.println("Inserted realEstateId: " + generatedRealEstateId);
  }
  
  @Test
  @Transactional
  void insertTest2() {
    AgentCreateFormDTO dto = new AgentCreateFormDTO();
    dto.setRealEstateId(1112);
    dto.setBuildingType(1);
    dto.setComplexName("예담주택");
    dto.setCityName("서울시");
    dto.setDistrictName("강남구");
    dto.setNeighborhoodName("화곡동");
    dto.setAddress("느그집 느그동 18번지");
    dto.setAddressStreet("느그로 18");
    dto.setSalePrice(12);
    dto.setConstructionYear(2020);
    dto.setContractDate("2020-02-04");
    dto.setBuildingName("102동");
    dto.setFloor("3층");
    dto.setExclusiveArea(85);
    
    try {
      agentService.insertRealEstateSale(dto);
      
      int generatedSalesId = dto.getSalesId();
      System.out.println("Inserted salesId: " + generatedSalesId);
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  void updateTest() {
    // 준비할 테스트 데이터
    AgentCreateFormDTO dto = new AgentCreateFormDTO();
    dto.setSalesId(253732); // 업데이트할 salesId 설정
    dto.setContractDate("2022-01-01"); // 업데이트할 contractDate 설정
    dto.setBuildingName("새로운 건물명");
    dto.setFloor("4층");
    dto.setExclusiveArea(100);
    dto.setSalePrice(150);

    // 매물 정보 업데이트 실행
    agentService.updateRealEstateSale(dto);
  }
  
  @Test
  void deleteTest() {
    agentService.deleteRealEstateSale(253732);
  }

}
