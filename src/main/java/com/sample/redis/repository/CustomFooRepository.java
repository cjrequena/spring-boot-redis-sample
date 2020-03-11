package com.sample.redis.repository;

import com.sample.redis.entity.FooEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomFooRepository extends AbstractCustomRedisRepository<String, FooEntity> implements CustomRepository<String, FooEntity> {

//  protected CustomFooRepository(Class<FooEntity> targetClass) {
//    super(targetClass);
//  }
}
