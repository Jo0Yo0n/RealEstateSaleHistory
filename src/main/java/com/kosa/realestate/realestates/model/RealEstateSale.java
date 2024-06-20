package com.kosa.realestate.realestates.model;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RealEstateSale {
  
  private int salesId;  //매매아이디
  private int realEstateId; //부동산아이디
  private int salePrice;    //거래금액
  private String contractDate;  //계약년월일
  private Date registrationDate;    //등기일자
  private String buildingName;  //아파트 동
  private String floor; //층
  private int exclusiveArea;    //전용면적
  
}
