package com.kosa.realestate.users.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kosa.realestate.users.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByEmail(String email);
}
