package com.kosa.realestate.users.service;

import com.kosa.realestate.users.model.UserDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {
  void createUser(String email, String password, String nickname);

  UserDTO getUserById(Long userId);

  List<UserDTO> getUserList();

  boolean updateUser(String email, String password);

  boolean deleteUser(String email, String password);

  UserDTO findUserByEmail(String email) throws UsernameNotFoundException;
  
  //사용자 중개자 권한 요청
  boolean requestPermission(Long userId);
  //관리자가 권한요청 리스트를 조회
  List<Map<String, Object>> selectUpgradeRequests(int startPage, int limit);
  //중개자 리시트 조회
  List<UserDTO> searchAgentList(String nickname, int startNum);
  //권한 UDATE
  int updateUserAccountType(List<Long> userId);
}
