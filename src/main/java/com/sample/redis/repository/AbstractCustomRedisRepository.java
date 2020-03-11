package com.sample.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractCustomRedisRepository<K, T> implements CustomRepository<K, T> {

  @Autowired
  private RedisTemplate<String, T> redisTemplate;

  //  protected AbstractCustomRedisRepository(Class<T> targetClass) {
  //    if (targetClass == null) {
  //      throw new IllegalArgumentException("<Null>");
  //    }
  //  }

  public void put(String name, K key, T entity) {
    redisTemplate.opsForHash().put(name, key, entity);
  }

  public T get(String name, K key) {
    return (T) redisTemplate.opsForHash().get(name, key);
  }

  public List<T> getAll(String name) {
    List<T> list = new ArrayList<>();
    for (Object entity : redisTemplate.opsForHash().values(name)) {
      list.add((T) entity);
    }
    return list;
  }

  public void delete(String name, K key) {
    redisTemplate.opsForHash().delete(name, key);
  }
}
