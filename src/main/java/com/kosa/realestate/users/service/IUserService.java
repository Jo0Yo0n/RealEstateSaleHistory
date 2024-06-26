package com.kosa.realestate.users.service;

import com.kosa.realestate.users.model.UserDTO;
import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {
  void createUser(String email, String password, String nickname);

  UserDTO getUserById(Long userId);

  List<UserDTO> getUserList();

  boolean updateUser(String email, String password);

  boolean deleteUser(String email, String password);

  UserDTO findUserByEmail(String email) throws UsernameNotFoundException;
}
