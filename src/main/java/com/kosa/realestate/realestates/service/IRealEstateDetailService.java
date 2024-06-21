package com.kosa.realestate.realestates.service;

import java.util.List;
import com.kosa.realestate.realestates.model.RealEstateWithSale;

public interface IRealEstateDetailService {
  List<RealEstateWithSale> getRealEstateDetail(int salesId);
}
