package com.kosa.realestate.agent.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.kosa.realestate.agent.model.AgentCreateFormDTO;

@Mapper
public interface AgentRepository {
  // 부동산 id 불러오기
  Integer getRealEstateId(String addressStreet);
  
  // 부동산 테이블에 데이터 삽입
  void insertRealEstate(AgentCreateFormDTO form);
  
  // 매매 기록 테이블에 데이터 삽입
  void insertRealEstateSale(AgentCreateFormDTO form);
  
  // 매매 기록 테이블에 데이터 업데이트
  void updateRealEstateSale(AgentCreateFormDTO form);
  
  // 매매 기록 테이블 데이터 삭제(소프트)
  void deleteRealEstateSale(int salesId);
  
  // 중개사 등록 매물 리스트 조회 -- 페이징 처리 해야 함
  List<AgentCreateFormDTO> getRegisteredList(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);
  
  // 중개사 등록 매물 리스트 개수 조회
  int getTotalRegisteredListCount(@Param("userId") Long userId);
}
