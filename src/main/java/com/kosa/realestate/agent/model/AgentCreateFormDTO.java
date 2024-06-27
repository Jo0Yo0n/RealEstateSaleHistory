package com.kosa.realestate.agent.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentCreateFormDTO {
  
  private int realEstateId;
  
  private int salesId;
  
  @NotNull(message = "건물 유형을 필수 값입니다.")
  private int buildingType;

  @NotEmpty(message = "건물 이름은 필수 값입니다.")
  private String complexName;

  @NotEmpty(message = "시 이름은 필수 값입니다.")
  private String cityName;

  @NotEmpty(message = "구 이름은 필수 값입니다.")
  private String districtName;
  
  @NotEmpty(message = "동 이름은 필수 값입니다.")
  private String neighborhoodName;

  @NotEmpty(message = "번지 주소는 필수 값입니다.")
  private String address;

  @NotEmpty(message = "도로명 주소는 필수 값입니다.")
  private String addressStreet;

  @NotNull(message = "매물 가격은 필수 값입니다.")
  private int salePrice;

  @NotEmpty(message = "건축 년도는 필수 값입니다.")
  private int constructionYear;
  
  private String contractDate;

  private String buildingName;

  @NotEmpty(message = "층수는 필수 값입니다.")
  private String floor;

  @NotNull(message = "전용 면적은 필수 값입니다.")
  private int exclusiveArea;
}
