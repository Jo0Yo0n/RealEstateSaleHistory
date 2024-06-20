package com.kosa.realestate.users.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kosa.realestate.users.DuplicateUserException;
import com.kosa.realestate.users.UserCreateForm;
import com.kosa.realestate.users.UserDTO;
import com.kosa.realestate.users.service.UserService;
import jakarta.validation.Valid;

/**
 * UserController 클래스
 * 
 * @author 이주윤
 */

@Controller
@RequestMapping("/users")
public class UserController {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // 회원가입 페이지로 이동
  @GetMapping("/signup")
  public String signup(UserCreateForm userCreateForm) {
    return "signup_form";
  }

  // user가 작성한 폼을 DB에 저장
  @PostMapping("/signup")
  public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "signup_form";
    }

    // 확인 비밀번호 불일치
    if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
      return "signup_form";
    }

    // 중복 가입 방지
    try {
      userService.createUser(userCreateForm.getEmail(), userCreateForm.getPassword1(),
          userCreateForm.getNickname());
    } catch (DuplicateUserException e) {
      if(e.getMessage().contains("Email")) {
        bindingResult.rejectValue("email", "emailDuplicate", "이 이메일은 이미 사용중입니다.");
      } else if (e.getMessage().contains("Nickname")) {
        bindingResult.rejectValue("nickname", "nicknameDuplicate", "이 닉네임은 이미 사용중입니다.");
      }
      return "signup_form";
    }
    
    // TODO: redirect 위치를 메인 페이지로 변경하기
    return "redirect:/";
  }
  
  // 로그인 페이지로 이동
  @GetMapping("/login")
  public String login() {
    return "login_form";
  }

  // id로 회원조회(테스트)
  @GetMapping("/{userId}")
  @ResponseBody
  public UserDTO getUserById(@PathVariable("userId") Long userId) {
    UserDTO userDTO = userService.getUserById(userId);
    return userDTO;
  }
  
  // 전체 회원 조회(테스트)
  @GetMapping("/")
  @ResponseBody
  public List<UserDTO> getUserList() {
    return userService.getUserList();
  }
}
