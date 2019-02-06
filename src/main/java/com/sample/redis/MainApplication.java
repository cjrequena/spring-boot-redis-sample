package com.sample.redis;

import com.sample.redis.entity.FooEntity;
import com.sample.redis.repository.FooRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

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
@Log4j2
@SpringBootApplication
public class MainApplication implements CommandLineRunner {


  @Autowired
  private FooRepository fooRepository;

  @Autowired
  private RedisTemplate<String, FooEntity> redisTemplate;


  private static Class<MainApplication> mainApplicationClass = MainApplication.class;

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    try {
      SpringApplication springApplication = new SpringApplication(mainApplicationClass);
      springApplication.setBannerMode(Banner.Mode.OFF);
      springApplication.run(args);
    } catch (Exception ex) {
      log.error("Error: " + ex);
      throw ex;
    }
  }

  @Override public void run(String... strings) throws Exception {

    boolean fooExist = fooRepository.exists("1");
    System.out.println("Is there a Foo with id 1 -> " + fooExist);
    fooRepository.save(new FooEntity("1", "Foo"));
    System.out.println("After add a Foo");
    fooExist = fooRepository.exists("1");
    System.out.println("Is there a Foo with id 1 -> " + fooExist);
    System.out.println(fooRepository.findOne("1").getName());
    fooRepository.save(new FooEntity("1", "Foo updated"));
    System.out.println("After Foo was updated");
    System.out.println(fooRepository.findOne("1").getName());
    fooRepository.delete(new FooEntity("1", "Foo"));
    System.out.println("After Foo was removed");
    fooExist = fooRepository.exists("1");
    System.out.println("Is there a Foo with id 1 -> " + fooExist);
    //--
//    redisTemplate.opsForHash().put("foo", "1", new FooEntity("1", "Foo") );
//    FooEntity foo = (FooEntity)redisTemplate.opsForHash().get("foo", "1");
//    List<Object> fooes = redisTemplate.opsForHash().values("foo");
//    redisTemplate.opsForHash().delete("foo", 1);
    
  }
}

