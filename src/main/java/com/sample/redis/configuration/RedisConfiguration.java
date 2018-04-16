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
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
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
@EnableRedisRepositories
public class RedisConfiguration {

  @Autowired
  private RedisConfigurationProperties redisConfigurationProperties;


  /**
   *
   * @return
   */
  @Bean
  public RedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setDatabase(redisConfigurationProperties.getDatabase());
    jedisConnectionFactory.setHostName(redisConfigurationProperties.getHost());
    jedisConnectionFactory.setPassword(redisConfigurationProperties.getPassword());
    jedisConnectionFactory.setUseSsl(redisConfigurationProperties.isSsl());
    jedisConnectionFactory.setTimeout(redisConfigurationProperties.getTimeout());
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxIdle(redisConfigurationProperties.getJedis().getPool().getMaxIdle());
    jedisPoolConfig.setMinIdle(redisConfigurationProperties.getJedis().getPool().getMinIdle());
    jedisPoolConfig.setMaxWaitMillis(redisConfigurationProperties.getJedis().getPool().getMaxWait());
    jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
    return jedisConnectionFactory;
  }


  /**
   *
   * @return
   */
  @ConditionalOnProperty(value="spring.redis.cluster.nodes")
  @Bean
  public RedisConnectionFactory jedisConnectionFactoryCluster() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigurationProperties.getCluster().getNodes());
    redisClusterConfiguration.setMaxRedirects(redisConfigurationProperties.getCluster().getMaxRedirects().intValue());
    return new JedisConnectionFactory(redisClusterConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value="spring.redis.sentinel.nodes")
  @Bean
  public RedisConnectionFactory jedisConnectionFactorySentinel() {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    redisSentinelConfiguration.setMaster(redisConfigurationProperties.getSentinel().getMaster());
    for (String node : redisConfigurationProperties.getSentinel().getNodes()) {
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
  @Bean
  @Primary
  public RedisConnectionFactory lettuceConnectionFactory() {
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
    lettuceConnectionFactory.setDatabase(redisConfigurationProperties.getDatabase());
    lettuceConnectionFactory.setHostName(redisConfigurationProperties.getHost());
    lettuceConnectionFactory.setPassword(redisConfigurationProperties.getPassword());
    lettuceConnectionFactory.setUseSsl(redisConfigurationProperties.isSsl());
    lettuceConnectionFactory.setTimeout(redisConfigurationProperties.getTimeout());
    lettuceConnectionFactory.setShutdownTimeout(redisConfigurationProperties.getLettuce().getShutdownTimeout());
    DefaultLettucePool lettucePool = new DefaultLettucePool();

    return lettuceConnectionFactory;
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value="spring.redis.cluster.nodes")
  @Bean
  public RedisConnectionFactory lettuceConnectionFactoryCluster() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigurationProperties.getCluster().getNodes());
    redisClusterConfiguration.setMaxRedirects(redisConfigurationProperties.getCluster().getMaxRedirects().intValue());
    return new LettuceConnectionFactory(redisClusterConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value="spring.redis.sentinel.nodes")
  @Bean
  public RedisConnectionFactory lettuceConnectionFactorySentinel() {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    redisSentinelConfiguration.setMaster(redisConfigurationProperties.getSentinel().getMaster());
    for (String node : redisConfigurationProperties.getSentinel().getNodes()) {
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
  public RedisTemplate redisTemplate() {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(lettuceConnectionFactory());
    RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(redisSerializer);
    return redisTemplate;
  }

  /**
   *
   * @return
   */
  @Bean
  public StringRedisTemplate stringRedisTemplate() {
    StringRedisTemplate stringTemplate = new StringRedisTemplate();
    stringTemplate.setConnectionFactory(lettuceConnectionFactory());
    RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    stringTemplate.setKeySerializer(redisSerializer);
    stringTemplate.setHashKeySerializer(redisSerializer);
    stringTemplate.setValueSerializer(redisSerializer);
    return stringTemplate;
  }

}
