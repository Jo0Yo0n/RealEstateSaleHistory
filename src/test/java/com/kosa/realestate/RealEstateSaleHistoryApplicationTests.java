package com.kosa.realestate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class RealEstateSaleHistoryApplicationTests {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void testConnection() {
    assertThat(jdbcTemplate).isNotNull();
  }

  @Test
  void contextLoads() {
    String sql = "SELECT 1 FROM DUAL";
    Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
    assertThat(result).isEqualTo(1);
  }

}
