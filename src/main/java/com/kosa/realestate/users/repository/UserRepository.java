package com.kosa.realestate.users.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.model.Users;

/**
 * UserDAO 인터페이스
 * 
 * @author 이주윤
 */
@Mapper
@Repository
public interface UserRepository {

  void createUser(Users user);

  Optional<Users> findUserByEmail(String email);

  Users getUserById(Long id);

  List<Users> getUserList();

  int updateUser(Users user);

  int deleteUser(String email);
  
  //사용자가 중개자 권한을 요청
  int requestPermission(@Param("userId")Long userId) throws PersistenceException ;
  
  //관리자가 권한요청 리스트를 조회
  List<Map<String, Object>> selectUpgradeRequests(@Param("offset") int offset, @Param("limit") int limit) throws PersistenceException;
  
  //중개자 리시트 조회
  List<UserDTO> searchAgentList(@Param("nickname") String nickname , @Param("offset") int offset, @Param("limit") int limit) throws PersistenceException;
  
  //권한 UPDATE nomal -> agent
  int updateUserAccountType(@Param("userId")List<Long> userId) throws PersistenceException ;
  
  // 권한요청 삭제
  void deleteUpgradeRequest(@Param("userId") Long userId) throws PersistenceException;
  
  //권한 UPDATE agent -> nomal
  int updateRoleToNormal(@Param("userId") List<Long> userId)throws PersistenceException;
}
