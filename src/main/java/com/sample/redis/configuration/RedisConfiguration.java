package com.sample.redis.configuration;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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
  @ConditionalOnProperty(value = {"spring.redis.jedis.pool.max-active", "spring.redis.jedis.pool.max-idle", "spring.redis.jedis.pool.max-wait", "spring.redis.jedis.pool.min-idle"})
  @Bean
  public RedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
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
  @ConditionalOnProperty(value = "spring.redis.cluster.nodes")
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
  @ConditionalOnProperty(value = "spring.redis.sentinel.nodes")
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
  @ConditionalOnProperty({"spring.redis.lettuce.pool.max-active", "spring.redis.lettuce.pool.max-idle", "spring.redis.lettuce.pool.max-wait", "spring.redis.lettuce.pool.min-idle"})
  @Bean
  @Primary
  public RedisConnectionFactory lettuceConnectionFactory() {
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisConfigurationProperties.getHost(), redisConfigurationProperties.getPort()));
    lettuceConnectionFactory.setDatabase(redisConfigurationProperties.getDatabase());
    return lettuceConnectionFactory;
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.cluster.nodes")
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
  @ConditionalOnProperty(value = "spring.redis.sentinel.nodes")
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

    // lettuce
    redisTemplate.setConnectionFactory(lettuceConnectionFactory());
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
  public StringRedisTemplate stringRedisTemplate() {
    StringRedisTemplate stringTemplate = new StringRedisTemplate();

    // lettuce
    stringTemplate.setConnectionFactory(lettuceConnectionFactory());
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

//  @Bean
//  public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
//
//    Map<String, RedisCacheConfiguration> mapRedisCacheConfiguration = new HashMap<>();
//
//    /** If we want to use JSON Serialized then use the below config snippet **/
//    //    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
//    //      .entryTtl(Duration.ofHours(1))
//    //      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
//
//    /** If we want to use JAVA Serialized then use the below config snippet **/
//    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//      .disableCachingNullValues()
//      .entryTtl(Duration.ofHours(1))
//      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()));
//
//    redisCacheConfiguration.usePrefix();
//
//    mapRedisCacheConfiguration.put("test-cache",redisCacheConfiguration);
//
//    return RedisCacheManager
//      .builder(lettuceConnectionFactory)
//      .withInitialCacheConfigurations(mapRedisCacheConfiguration)
//      .build();
//
//  }

}
