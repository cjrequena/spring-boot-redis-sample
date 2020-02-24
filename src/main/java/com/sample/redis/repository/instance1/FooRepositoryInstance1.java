package com.sample.redis.repository.instance1;

import com.sample.redis.entity.FooEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
@Repository
public interface FooRepositoryInstance1 extends CrudRepository<FooEntity, String> {

}
