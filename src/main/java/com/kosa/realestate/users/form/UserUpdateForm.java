package com.kosa.realestate.users.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 이주윤
 */
@Getter
@Setter
public class UserUpdateForm {

  @NotEmpty(message = "비밀번호는 필수항목입니다.")
  private String password;

  @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
  private String passwordConfirm;
}
