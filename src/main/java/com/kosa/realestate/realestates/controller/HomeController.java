package com.kosa.realestate.realestates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  
  @GetMapping("/budongsan")
  public String budongsan(Model model) {
    return "map";
  }
  
  @GetMapping("/budongsan/detail")
  public String detail(Model model) {
    return "pricedetail";
  }
}
