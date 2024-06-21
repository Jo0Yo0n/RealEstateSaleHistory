package com.kosa.realestate.users.repository;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.kosa.realestate.users.Users;

/**
 * UserDAO 인터페이스
 * 
 * @author 이주윤
 */
@Mapper
@Repository
public interface UserDAO {

  void createUser(Users user);

  Optional<Users> findUserByEmail(String email);

  Users getUserById(Long id);

  List<Users> getUserList();

  void updateUser(Users user);

  void deleteUser(Long id);
}
