package com.kosa.realestate.users.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kosa.realestate.users.DuplicateUserException;
import com.kosa.realestate.users.UserDAO;
import com.kosa.realestate.users.UserDTO;

/**
 * UserService 클래스
 * 
 * @author 이주윤
 */
@Service
public class UserService {
  private final UserDAO userDao;
  private final PasswordEncoder passwordEncoder;

  // 생성자 주입
  public UserService(UserDAO userDao, PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }

  // userId로 User 반환
  public UserDTO getUserById(Long userId) {
    return userDao.getUserById(userId);
  }

  // 모든 User 반환
  public List<UserDTO> getUserList() {
    return userDao.getUserList();
  }

  // User 생성
  public int createUser(String email, String password, String nickname) {
    UserDTO user = new UserDTO();

    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setNickname(nickname);

    // user가 작성한 email, nickname이 DB에 있는 데이터와 중복되는지 확인
    try {
      return userDao.createUser(user);
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage().contains("EMAIL_UNIQUE")) {
        throw new DuplicateUserException("Email is already in use.");
      } else if (e.getMessage().contains("NICKNAME_UNIQUE")) {
        throw new DuplicateUserException("Nickname is already in use.");
      } else {
        throw new RuntimeException("An unexpected error occurred.", e);
      }
    }
  }

  // User 업데이트
  public int updateUser(UserDTO userDTO) {
    return userDao.updateUser(userDTO);
  }

  // User 삭제 (soft delete)
  public int deleteUser(Long id) {
    return userDao.deleteUser(id);
  }

}
