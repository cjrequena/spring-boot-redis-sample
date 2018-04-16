package com.sample.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash
@AllArgsConstructor
public class FooEntity {

  /**
   * Entity id
   */
  @Id
  protected String id;
  /**
   *
   */
  private String name;

}
