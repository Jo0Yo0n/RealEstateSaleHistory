package com.kosa.realestate.realestates.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRealEstateSaleRepository {
  int getRealEstateSaleCount();
  int getRealEstateSaleCount(int realEstateId);
}
