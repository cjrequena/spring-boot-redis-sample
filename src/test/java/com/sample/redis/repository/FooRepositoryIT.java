package com.sample.redis.repository;

import com.sample.redis.MainApplication;
import com.sample.redis.entity.FooEntity;
import com.sample.redis.repository.instance1.FooRepositoryInstance1;
import com.sample.redis.repository.instance2.FooRepositoryInstance2;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FooRepositoryIT {

//    @Autowired
//    private FooRepositoryInstance1 fooRepositoryInstance1;
//
//    @Autowired
//    private FooRepositoryInstance2 fooRepositoryInstance2;

    @Resource(name = "redisTemplateInstance1")
    private RedisTemplate<String, Object> redisTemplateInstance1;

    @Resource(name = "redisTemplateInstance2")
    private RedisTemplate<String, Object> redisTemplateInstance2;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void redis_test_instance1(){
        //fooRepositoryInstance1.save(new FooEntity("1", "FooInstance1"));
        redisTemplateInstance1.opsForValue().set("1", new FooEntity("1", "FooInstance1"));
    }

    @Test
    public void redis_test_instance2(){
        //fooRepositoryInstance2.save(new FooEntity("1", "FooInstance2"));
        redisTemplateInstance2.opsForValue().set("1", new FooEntity("1", "FooInstance2"));
    }

}