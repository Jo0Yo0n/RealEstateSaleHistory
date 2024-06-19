package com.kosa.realestate.users;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    
    return userDao.createUser(user);
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
