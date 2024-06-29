package com.kosa.realestate.agent.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;
import com.kosa.realestate.realestates.model.RealEstateSaleDTO;

public interface IAgentService {
//부동산 id 불러오기
 Integer getRealEstateId(String addressStreet);
 
 // 부동산 테이블에 데이터 삽입
 void insertRealEstate(AgentCreateFormDTO form);
 
 // 매매 기록 테이블에 데이터 삽입
 void insertRealEstateSale(AgentCreateFormDTO form);
 
 // 부동산 아이디 조회 가능시 부동산 테이블, 매매 기록 테이블에 데이터 한 번에 삽입
 void processRealEstateAndSales(AgentCreateFormDTO form) throws Exception;
 
 // 매매 기록 테이블에 데이터 업데이트
 void updateRealEstateSale(AgentCreateFormDTO form);
 
 // 매매 기록 테이블 데이터 삭제(소프트)
 void deleteRealEstateSale(int salesId);
 
 // 중개사 등록 매물 리스트 조회  -- 페이징 처리 해야 함
 List<AgentCreateFormDTO> getRegisteredList(Long userId, int offset, int limit);
 
 // 중개사 등록 매물 리스트 개수 조회
 int getTotalRegisteredListCount(@Param("userId") Long userId);
}
