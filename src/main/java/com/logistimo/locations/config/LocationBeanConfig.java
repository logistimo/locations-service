package com.logistimo.locations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * Created by kumargaurav on 03/03/17.
 */
@Configuration
public class LocationBeanConfig {

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }
}
