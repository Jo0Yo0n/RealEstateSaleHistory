package com.kosa.realestate.admindivision.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.kosa.realestate.admindivision.model.entity.City;
import com.kosa.realestate.admindivision.model.entity.District;
import com.kosa.realestate.admindivision.model.entity.Neighborhood;

@Mapper
@Repository
public interface AdminDivisionRepository {

  List<City> selectCityList();

  List<District> selectDistrictList(Long cityId);

  List<Neighborhood> selectNeighborhoodList(Long districtId);
}
