package com.kosa.realestate.users;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @GetMapping("/signup")
  public String signup(UserCreateForm userCreateForm) {
    return "signup_form";
  }

  @PostMapping("/signup")
  public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "signup_form";
    }

    if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
      return "signup_form";
    }

    userService.createUser(userCreateForm.getEmail(), userCreateForm.getPassword1(),
        userCreateForm.getNickname());
    
    return "redirect:/";
  }

  // id로 회원조회(테스트)
  @GetMapping("/{userId}")
  @ResponseBody
  public String getUserById(@PathVariable("userId") Long userId) {
    UserDTO userDTO = userService.getUserById(userId);
    return "";
  }
}
