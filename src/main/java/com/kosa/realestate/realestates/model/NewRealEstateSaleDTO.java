package com.kosa.realestate.realestates.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewRealEstateSaleDTO {

    private Long salesId;
    private Double salePrice;
    private Long floor;
    private String buildingName;
    private Double exclusiveArea;
    private Long realEstateId;
    private String complexName;
    private String address;
    private String addressStreet;
    private Long constructionYear;
    private Double lat;
    private Double lng;
    private String buildingTypeName;
    private String cityName;
    private String districtName;
    private String neighborhoodName;
}
