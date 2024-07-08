package com.kosa.realestate.favorites.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * FavoriteDetailListDTO
 *
 * @author 오동건
 */
@Getter
@Setter
@Builder
public class FavoriteDetailListDTO {

  private Long real_estate_id;
  private String complex_name;
  private Long sales_id;
  private String contract_date;
  private LocalDateTime registration_date;
  private String building_name;
  private String floor;
  private String exclusive_area;
  private String sale_price;
}
