package com.sample.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Data
@AllArgsConstructor
@RedisHash(value = "foo")
public class FooEntity implements Serializable {

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
