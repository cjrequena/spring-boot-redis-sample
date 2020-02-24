package com.sample.redis.configuration;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

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
@Log4j2
@Configuration
@EnableRedisRepositories(basePackages = {"com.sample.redis.repository.instance2"})
public class RedisConfigurationInstance2 {

  @Autowired
  private RedisConfigurationPropertiesInstance2 redisConfigurationPropertiesInstance2;

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = {"spring.redis.instance2.jedis.pool.max-active", "spring.redis.instance2.jedis.pool.max-idle", "spring.redis.instance2.jedis.pool.max-wait", "spring.redis.instance2.jedis.pool.min-idle"})
  @Bean
  public RedisConnectionFactory jedisConnectionFactoryInstance2() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxIdle(redisConfigurationPropertiesInstance2.getJedis().getPool().getMaxIdle());
    jedisPoolConfig.setMinIdle(redisConfigurationPropertiesInstance2.getJedis().getPool().getMinIdle());
    jedisPoolConfig.setMaxWaitMillis(redisConfigurationPropertiesInstance2.getJedis().getPool().getMaxWait());
    jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
    return jedisConnectionFactory;
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance2.cluster.nodes")
  @Bean
  public RedisConnectionFactory jedisConnectionFactoryClusterInstance2() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigurationPropertiesInstance2.getCluster().getNodes());
    redisClusterConfiguration.setMaxRedirects(redisConfigurationPropertiesInstance2.getCluster().getMaxRedirects().intValue());
    return new JedisConnectionFactory(redisClusterConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance2.sentinel.nodes")
  @Bean
  public RedisConnectionFactory jedisConnectionFactorySentinelInstance2() {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    redisSentinelConfiguration.setMaster(redisConfigurationPropertiesInstance2.getSentinel().getMaster());
    for (String node : redisConfigurationPropertiesInstance2.getSentinel().getNodes()) {
      String host = node.split(":")[0];
      Integer port = Integer.parseInt(node.split(":")[1]);
      redisSentinelConfiguration.sentinel(host, port);
    }
    return new JedisConnectionFactory(redisSentinelConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty({"spring.redis.instance2.lettuce.pool.max-active", "spring.redis.instance2.lettuce.pool.max-idle", "spring.redis.instance2.lettuce.pool.max-wait", "spring.redis.instance2.lettuce.pool.min-idle"})
  @Bean
  @Primary
  public RedisConnectionFactory lettuceConnectionFactoryInstance2() {
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
    lettuceConnectionFactory.setDatabase(redisConfigurationPropertiesInstance2.getDatabase());
    return lettuceConnectionFactory;
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance2.cluster.nodes")
  @Bean
  public RedisConnectionFactory lettuceConnectionFactoryClusterInstance2() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigurationPropertiesInstance2.getCluster().getNodes());
    redisClusterConfiguration.setMaxRedirects(redisConfigurationPropertiesInstance2.getCluster().getMaxRedirects().intValue());
    return new LettuceConnectionFactory(redisClusterConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance2.sentinel.nodes")
  @Bean
  public RedisConnectionFactory lettuceConnectionFactorySentinelInstance2() {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    redisSentinelConfiguration.setMaster(redisConfigurationPropertiesInstance2.getSentinel().getMaster());
    for (String node : redisConfigurationPropertiesInstance2.getSentinel().getNodes()) {
      String host = node.split(":")[0];
      Integer port = Integer.parseInt(node.split(":")[1]);
      redisSentinelConfiguration.sentinel(host, port);
    }
    return new LettuceConnectionFactory(redisSentinelConfiguration);
  }

  /**
   *
   * @return
   */
  @Bean
  public RedisTemplate redisTemplateInstance2() {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(lettuceConnectionFactoryInstance2());

    // lettuce
    redisTemplate.setConnectionFactory(lettuceConnectionFactoryInstance2());
    // redisTemplate.setConnectionFactory(lettuceConnectionFactoryCluster());
    // redisTemplate.setConnectionFactory(lettuceConnectionFactorySentinel());

    // jedis
    //redisTemplate.setConnectionFactory(jedisConnectionFactory());
    // redisTemplate.setConnectionFactory(jedisConnectionFactoryCluster());
    // redisTemplate.setConnectionFactory(jedisConnectionFactorySentinel());
    
    RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(redisSerializer);
    return redisTemplate;
  }

  /**
   *
   * @return
   */
  @Bean
  public StringRedisTemplate stringRedisTemplateInstance2() {
    StringRedisTemplate stringTemplate = new StringRedisTemplate();

    // lettuce
    stringTemplate.setConnectionFactory(lettuceConnectionFactoryInstance2());
    // stringTemplate.setConnectionFactory(lettuceConnectionFactoryCluster());
    // stringTemplate.setConnectionFactory(lettuceConnectionFactorySentinel());

    // jedis
    //stringTemplate.setConnectionFactory(jedisConnectionFactory());
    // stringTemplate.setConnectionFactory(jedisConnectionFactoryCluster());
    // stringTemplate.setConnectionFactory(jedisConnectionFactorySentinel());

    RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    stringTemplate.setKeySerializer(redisSerializer);
    stringTemplate.setHashKeySerializer(redisSerializer);
    stringTemplate.setValueSerializer(redisSerializer);
    return stringTemplate;
  }

}
