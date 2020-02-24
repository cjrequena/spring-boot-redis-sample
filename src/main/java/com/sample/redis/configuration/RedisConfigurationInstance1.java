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
@EnableRedisRepositories(basePackages = {"com.sample.redis.repository.instance1"})
public class RedisConfigurationInstance1 {

  @Autowired
  private RedisConfigurationPropertiesInstance1 redisConfigurationPropertiesInstance1;

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = {"spring.redis.instance1.jedis.pool.max-active", "spring.redis.instance1.jedis.pool.max-idle", "spring.redis.instance1.jedis.pool.max-wait", "spring.redis.instance1.jedis.pool.min-idle"})
  @Bean(name = "jedisConnectionFactoryInstance1")
  public RedisConnectionFactory jedisConnectionFactoryInstance1() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxIdle(redisConfigurationPropertiesInstance1.getJedis().getPool().getMaxIdle());
    jedisPoolConfig.setMinIdle(redisConfigurationPropertiesInstance1.getJedis().getPool().getMinIdle());
    jedisPoolConfig.setMaxWaitMillis(redisConfigurationPropertiesInstance1.getJedis().getPool().getMaxWait());
    jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
    return jedisConnectionFactory;
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance1.cluster.nodes")
  @Bean(name = "jedisConnectionFactoryClusterInstance1")
  public RedisConnectionFactory jedisConnectionFactoryClusterInstance1() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigurationPropertiesInstance1.getCluster().getNodes());
    redisClusterConfiguration.setMaxRedirects(redisConfigurationPropertiesInstance1.getCluster().getMaxRedirects().intValue());
    return new JedisConnectionFactory(redisClusterConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance1.sentinel.nodes")
  @Bean(name = "jedisConnectionFactorySentinelInstance1")
  public RedisConnectionFactory jedisConnectionFactorySentinelInstance1() {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    redisSentinelConfiguration.setMaster(redisConfigurationPropertiesInstance1.getSentinel().getMaster());
    for (String node : redisConfigurationPropertiesInstance1.getSentinel().getNodes()) {
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
  @ConditionalOnProperty({"spring.redis.instance1.lettuce.pool.max-active", "spring.redis.instance1.lettuce.pool.max-idle", "spring.redis.instance1.lettuce.pool.max-wait", "spring.redis.instance1.lettuce.pool.min-idle"})
  @Bean(name = "lettuceConnectionFactoryInstance1")
  @Primary
  public RedisConnectionFactory lettuceConnectionFactoryInstance1() {
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
    lettuceConnectionFactory.setDatabase(redisConfigurationPropertiesInstance1.getDatabase());
    lettuceConnectionFactory.setHostName(redisConfigurationPropertiesInstance1.getHost());
    lettuceConnectionFactory.setPort(redisConfigurationPropertiesInstance1.getPort());
    lettuceConnectionFactory.setPassword(redisConfigurationPropertiesInstance1.getPassword());
    lettuceConnectionFactory.setDatabase(redisConfigurationPropertiesInstance1.getDatabase());
    lettuceConnectionFactory.setUseSsl(redisConfigurationPropertiesInstance1.isSsl());
    lettuceConnectionFactory.setTimeout(redisConfigurationPropertiesInstance1.getTimeout());
    lettuceConnectionFactory.setShutdownTimeout(redisConfigurationPropertiesInstance1.getLettuce().getShutdownTimeout());
    return lettuceConnectionFactory;
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance1.cluster.nodes")
  @Bean(name = "lettuceConnectionFactoryClusterInstance1")
  public RedisConnectionFactory lettuceConnectionFactoryClusterInstance1() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigurationPropertiesInstance1.getCluster().getNodes());
    redisClusterConfiguration.setMaxRedirects(redisConfigurationPropertiesInstance1.getCluster().getMaxRedirects().intValue());
    return new LettuceConnectionFactory(redisClusterConfiguration);
  }

  /**
   *
   * @return
   */
  @ConditionalOnProperty(value = "spring.redis.instance1.sentinel.nodes")
  @Bean(name = "lettuceConnectionFactorySentinelInstance1")
  public RedisConnectionFactory lettuceConnectionFactorySentinelInstance1() {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    redisSentinelConfiguration.setMaster(redisConfigurationPropertiesInstance1.getSentinel().getMaster());
    for (String node : redisConfigurationPropertiesInstance1.getSentinel().getNodes()) {
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
  @Bean(name="redisTemplateInstance1")
  public RedisTemplate redisTemplateInstance1() {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(lettuceConnectionFactoryInstance1());

    // lettuce
    redisTemplate.setConnectionFactory(lettuceConnectionFactoryInstance1());
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
  @Bean(name = "stringRedisTemplateInstance1")
  public StringRedisTemplate stringRedisTemplateInstance1() {
    StringRedisTemplate stringTemplate = new StringRedisTemplate();

    // lettuce
    stringTemplate.setConnectionFactory(lettuceConnectionFactoryInstance1());
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
