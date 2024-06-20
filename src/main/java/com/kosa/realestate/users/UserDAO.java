package com.kosa.realestate.users;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * UserDAO 클래스
 * 
 * @author 이주윤
 */
@Mapper
@Repository
public interface UserDAO {
  void createUser(Users user);
  
  // Optional<UserDTO> 
  
  Users getUserById(Long id);

  List<Users> getUserList();

  void updateUser(Users user);

  void deleteUser(Long id);
}
