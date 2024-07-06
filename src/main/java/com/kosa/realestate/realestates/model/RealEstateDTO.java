package com.kosa.realestate.realestates.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * @author 백재우
 */
@Getter
@Setter
@ToString
public class RealEstateDTO {

  private int realEstateId; //부동산 아이디
  private int neighborhoodId;   //동아이디
  private String complexName;   //단지명
  private String address;   //번지주소
  private String addressStreet; //도로명 주소
  private int constructionYear; //건축년도
  private double lat;   //위도
  private double lng;   //경도
//  private String buildingFlag;  //건물유형
}
