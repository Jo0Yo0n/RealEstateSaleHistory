package com.kosa.realestate.users.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.realestate.users.DuplicateUserException;
import com.kosa.realestate.users.InvalidPasswordException;
import com.kosa.realestate.users.UserMapper;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.model.Users;
import com.kosa.realestate.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * UserService 클래스
 *
 * @author 이주윤
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  // User 생성
  public void createUser(String email, String password, String nickname) {

    Users user = Users.builder().email(email).password(passwordEncoder.encode(password)) // 비밀번호 암호화
        .nickname(nickname).build();

    // user가 작성한 email, nickname이 DB에 있는 데이터와 중복되는지 확인
    try {
      userRepository.createUser(user);
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

  // userId로 User 반환
  public UserDTO getUserById(Long userId) {
    Users user = userRepository.getUserById(userId);
    return UserMapper.toDTO(user);
  }

  // 모든 User 반환
  public List<UserDTO> getUserList() {
    List<Users> usersList = userRepository.getUserList();

    return usersList.stream().map(UserMapper::toDTO).collect(Collectors.toList());
  }

  // User 업데이트
  public boolean updateUser(String email, String password) throws UsernameNotFoundException {
    Optional<Users> _user = userRepository.findUserByEmail(email);
    if(_user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
    Users user = _user.get();
    user.setPassword(passwordEncoder.encode(password));

    int rowsAffected = userRepository.updateUser(user);

    return rowsAffected > 0;
  }

  // User 삭제 (soft delete)
  public boolean deleteUser(String email, String password) throws UsernameNotFoundException {
    Optional<Users> _user = userRepository.findUserByEmail(email);
    if(_user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
    Users user = _user.get();

    if(!passwordEncoder.matches(password, user.getPassword())) {
      throw new InvalidPasswordException("비밀번호가 잘못되었습니다.");
    }

    int rowsAffected = userRepository.deleteUser(email);
    return rowsAffected > 0;
  }

  // 이메일 기준 사용자 정보 조회 (MyBatis)
  public UserDTO findUserByEmail(String email) throws UsernameNotFoundException {
    Optional<Users> _user = userRepository.findUserByEmail(email);
    if(_user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    return UserMapper.toDTO(_user.get()); // Users 객체를 UserDTO 로 변환 후 반환
  }
  
  //사용자 중개자 권한 요청
  public boolean requestPermission(Long userId) {
      try{
          int result = userRepository.requestPermission(userId);
          return result > 0;
      } catch (PersistenceException e) {
          log.error("Error requesting permission for user with ID {}: ", userId, e);
          return false;
      }
  }
  
  //관리자가 권한요청 리스트를 조회
  @Override
  public List<Map<String, Object>> selectUpgradeRequests(int startPage, int limit) {
    int offset = (startPage - 1) * limit;
    try {
      List<Map<String,Object>> resultList = userRepository.selectUpgradeRequests(offset, limit);
      return resultList;
    } catch (PersistenceException e) {
      // 로그에 예외 정보 기록
      log.error("Error accessing the database", e);
    }
    return null;
  }
  //중개자 리시트 조회
  @Override
  public List<UserDTO> searchAgentList(String nickname, int startNum) {
      int limit = 10;
      int offset = (startNum-1) * limit;
    return userRepository.searchAgentList(nickname, offset, limit);
  }
  //권한 UDATE
  @Override
  @Transactional(rollbackFor = PersistenceException.class)
  public int updateUserAccountType(List<Long> userId) {
    int requestCount = userId.size();
    int resultCount = userRepository.updateUserAccountType(userId);
 
    if (requestCount != resultCount) {
      String errorMessage = String.format("요청된 개수(%d)와 업데이트된 개수(%d)가 일치하지 않습니다.", requestCount, resultCount);
      throw new PersistenceException(errorMessage);
    }
    return resultCount;
  }
  
}
