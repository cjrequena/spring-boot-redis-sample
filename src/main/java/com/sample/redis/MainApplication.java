package com.sample.redis;

import com.sample.redis.service.IRedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
  private IRedisService service;

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
    boolean hasName = service.hasKey("name");
    System.out.println("=======================================");
    System.out.println("Is redis has a 'neme' key? -> " + hasName);
    service.saveObject("name", "Hello redis cluster");
    hasName = service.hasKey("name");
    System.out.println("After add key 'name', Is redis has a 'neme' key? -> " + hasName);
    if (hasName) {
      String val = service.get("name", String.class);
      System.out.println("The value of key 'neme' is " + val);
    }
    service.remove("name");
    hasName = service.hasKey("name");
    System.out.println("After remove the 'name' key , is redis has a 'neme' key? -> " + hasName);
    System.out.println("=======================================");
  }
}

