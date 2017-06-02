package com.logistimo.locations.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
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

  @Bean
  public JedisConnectionFactory jedisConnectionFactory () {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
    jedisConnectionFactory.setPort(Integer.valueOf(env.getProperty("spring.redis.port")));
    return jedisConnectionFactory;
  }

  @Bean
  public RedisTemplate<Object,Object> redisTemplate  () {
    RedisTemplate redisTemplate = new StringRedisTemplate();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
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
    return redisCacheManager;
  }
}
