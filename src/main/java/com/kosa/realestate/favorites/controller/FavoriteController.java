package com.kosa.realestate.favorites.controller;

import com.kosa.realestate.favorites.dto.FavoriteListDto;
import java.security.Principal;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import com.kosa.realestate.favorites.dto.FavoriteDto;
import com.kosa.realestate.favorites.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {

  private final FavoriteService favoriteService;


  // 즐겨찾기 아파트 리스트 조회
  @GetMapping
  public String favoriteList(
      Model model, @RequestParam(value="page", defaultValue = "0") int page, Principal principal) {

    Pageable pageable = PageRequest.of(page, 10);
    
    List<FavoriteListDto> favoriteListDto = favoriteService.findFavoriteList(pageable, "tot0119@naver.com");

    model.addAttribute("count", favoriteListDto.size());
    model.addAttribute("favorites", favoriteListDto);

    return "favorite_list";
  }


  // 즐겨찾기 아파트 상세 조회
  @GetMapping("/details/{realEstateId}")
  public String favoriteDetailList(
      @PathVariable("realEstateId") Long realEstateId,
      @RequestParam(value="page", defaultValue = "0") int page) {

    Pageable pageable = PageRequest.of(page, 10);
    
    favoriteService.findFavoriteDetailLst(pageable, realEstateId);

    return null;
  }


  // 즐겨찾기 추가
  @PostMapping("/{realEstateId}")
  public String favoriteAdd(@PathVariable("realEstateId") Long realEstateId, Principal principal) {
    
    FavoriteDto favoriteDto = favoriteService.addFavorite(realEstateId, principal.getName());
    
    return null;
  }


  // 즐겨찾기 삭제
  @DeleteMapping("/{favoriteId}")
  public String favoriteRemove(@PathVariable("favoriteId") Long favoriteId, Principal principal) {

    favoriteService.removeFavorite(favoriteId);


    return null;
  }
}
