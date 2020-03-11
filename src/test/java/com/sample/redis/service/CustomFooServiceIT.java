package com.sample.redis.service;

import com.sample.redis.MainApplication;
import com.sample.redis.entity.FooEntity;
import lombok.extern.log4j.Log4j2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomFooServiceIT {

  @Autowired
  private CustomFooService customFooService;

  @Test
  public void create() {
    FooEntity entity = new FooEntity("id::1","foo::1");
    this.customFooService.create(entity);
  }

  @Test
  public void retrieveById() {
    FooEntity entity = this.customFooService.retrieveById("id::1");
    log.debug("name: {}", entity.getName());
  }
}
