package com.sample.redis.repository;

import com.sample.redis.entity.FooEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooRepository extends CrudRepository<FooEntity, String> {

}
