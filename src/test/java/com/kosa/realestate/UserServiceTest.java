package com.kosa.realestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.kosa.realestate.users.repository.UserRepository;
import com.kosa.realestate.users.service.UserService;

@ExtendWith({SpringExtension.class, MockitoExtension.class}) 
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void updateUserAccountTypeTest() {
      // 테스트를 위한 사용자 ID 리스트 준비
      List<Long> userIds = Arrays.asList(1L, 2L, 3L);

      // 메서드 실행
      int updatedCount = userRepository.updateRoleToNormal(userIds);

      // 결과 확인
      assertEquals(userIds.size(), updatedCount);
    }
}
