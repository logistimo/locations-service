package com.logistimo.locations.config;

import com.logistimo.locations.config.condition.SentinelCondition;
import com.logistimo.locations.config.condition.StandaloneCondition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 06/03/17.
 */
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

  @Resource
  Environment env;

  @Value("${app.issentinel}")
  private Boolean isSentinel;


  @Bean
  @Conditional(StandaloneCondition.class)
  public JedisConnectionFactory jedisConnectionFactory () {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(env.getProperty("app.redis.host"));
    jedisConnectionFactory.setPort(Integer.valueOf(env.getProperty("app.redis.port")));
    return jedisConnectionFactory;
  }

  @Bean
  @Conditional(SentinelCondition.class)
  public JedisConnectionFactory sentinelJedisConnectionFactory() {

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig());
    jedisConnectionFactory.setUsePool(true);
    return jedisConnectionFactory;
  }

  @Bean
  @Conditional(SentinelCondition.class)
  public RedisSentinelConfiguration sentinelConfig() {
    RedisSentinelConfiguration
        SENTINEL_CONFIG =
        new RedisSentinelConfiguration().master(env.getProperty("app.redis.sentinel.master")) //
            .sentinel(env.getProperty("app.redis.sentinel.node1.host"), 26379)//
            .sentinel(env.getProperty("app.redis.sentinel.node2.host"), 26378);
    return SENTINEL_CONFIG;
  }

  @Bean
  public RedisTemplate<Object,Object> redisTemplate  () {
    RedisTemplate redisTemplate = new StringRedisTemplate();
    if (isSentinel) {
      redisTemplate.setConnectionFactory(sentinelJedisConnectionFactory());
    } else {
      redisTemplate.setConnectionFactory(jedisConnectionFactory());
    }
    redisTemplate.setExposeConnection(true);
    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
    redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    return redisTemplate;
  }

  @Bean
  public RedisCacheManager redisCacheManager () {
    RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
    redisCacheManager.setUsePrefix(true);
    redisCacheManager.setDefaultExpiration(86400l);
    redisCacheManager.getCache("country");
    redisCacheManager.getCache("country").put("IN", "IN");
    return redisCacheManager;
  }

}
