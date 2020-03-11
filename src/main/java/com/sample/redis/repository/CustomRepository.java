package com.sample.redis.repository;

import java.util.List;

public interface CustomRepository<K, T> {

  void put(String name, K key, T entity);

  T get(String name, K key);

  List<T> getAll(String name);

  void delete(String name, K key);

  //void deleteAll(String name);

}
