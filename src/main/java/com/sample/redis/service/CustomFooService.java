package com.sample.redis.service;

import com.sample.redis.entity.FooEntity;
import com.sample.redis.repository.CustomFooRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@Service
@Transactional
public class CustomFooService {

  private CustomFooRepository customFooRepository;

  public CustomFooService(CustomFooRepository customFooRepository) {
    this.customFooRepository = customFooRepository;
  }


  public void create(FooEntity entity) {
    //--
    this.customFooRepository.put("foo::data", entity.getId(), entity);
    //--
  }


  public FooEntity retrieveById(String id)  {
    //--
    return     this.customFooRepository.get("foo::data", id);
    //--
  }


}
