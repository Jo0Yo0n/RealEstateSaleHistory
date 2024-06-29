package com.kosa.realestate.agent.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AgentCreateFormDTO {
  
  private int realEstateId;
  
  private int salesId;
  
  private Long userId;
  
  @NotNull(message = "건물 유형을 필수 값입니다.")
  private String buildingType;

  @NotNull(message = "건물 이름은 필수 값입니다.")
  private String complexName;

  @NotNull(message = "시 이름은 필수 값입니다.")
  private String cityName;

  @NotNull(message = "구 이름은 필수 값입니다.")
  private String districtName;
  
  @NotNull(message = "동 이름은 필수 값입니다.")
  private String neighborhoodName;

  @NotNull(message = "번지 주소는 필수 값입니다.")
  private String address;

  @NotNull(message = "도로명 주소는 필수 값입니다.")
  private String addressStreet;

  @NotNull(message = "매물 가격은 필수 값입니다.")
  private int salePrice;

  @NotNull(message = "건축 년도는 필수 값입니다.")
  private int constructionYear;
  
  private String contractDate;

  private String buildingName;

  @NotNull(message = "층수는 필수 값입니다.")
  private String floor;

  @NotNull(message = "전용 면적은 필수 값입니다.")
  private int exclusiveArea;
}
