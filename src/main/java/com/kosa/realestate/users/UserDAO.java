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
  UserDTO getUserById(Long id);

  List<UserDTO> getUserList();

  int createUser(UserDTO user);

  int updateUser(UserDTO user);

  int deleteUser(Long id);
}
