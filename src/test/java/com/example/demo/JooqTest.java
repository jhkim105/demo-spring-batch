package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.db.Tables.MT_PAYMENT;
import static com.example.demo.db.Tables.MT_USER;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JooqTest {


  @Autowired
  DSLContext dsl;

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }


  @Test
  public void fetch() {
    Result<Record> results = this.dsl.select().from(MT_USER).fetch();
    for (Record result : results) {
      String id = result.getValue(MT_USER.ID);
      String name = result.getValue(MT_USER.NAME);
      String usernmae = result.getValue(MT_USER.USERNAME);
      log.debug("id:{}, name:{}, username:{}", id, name, usernmae);
    }
  }


  @Test
  public void query() {
    Query query = this.dsl.select(MT_USER.ID, MT_USER.NAME, MT_USER.USERNAME)
        .from(MT_USER).join(MT_PAYMENT).on(MT_USER.ID.equal(MT_PAYMENT.USER_ID))
        .where(MT_USER.ENABLED.equal(Boolean.TRUE));
    Object[] bind = query.getBindValues().toArray(new Object[]{});
    List<Map<String, String>> list = this.jdbcTemplate.query(query.getSQL(), bind,  (resultSet,  i) -> {
      Map<String, String> result = new HashMap<>();
      result.put("id", resultSet.getString(1));
      result.put("name", resultSet.getString(2));
      result.put("username", resultSet.getString(3));
      return result;
    });

    log.debug("{}", list);
  }
}
