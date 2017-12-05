package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MultipleDataSourceTest {

  private JdbcTemplate legacyJdbcTemplate;

  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DataSource dataSource;

  @Resource(name = "legacyDataSource")
  private DataSource legacyDataSource;

  @Test
  public void test() {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    Long count = jdbcTemplate.queryForObject("select count(1) from mt_user", Long.class);
    log.debug("userCount:{}", count);
  }


  @Test
  public void testLegacy() {
    this.legacyJdbcTemplate = new JdbcTemplate(legacyDataSource);
    Long count = legacyJdbcTemplate.queryForObject("select count(1) from mt_user", Long.class);
    log.debug("legacyUserCount:{}", count);
  }


}
