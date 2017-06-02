package com.logistimo.locations.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 14/02/17.
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableJpaRepositories(
    entityManagerFactoryRef = "lcEntityManagerFactory",
    transactionManagerRef = "lcTransactionManager",
    basePackages = {"com.logistimo.locations.repository.location"})
public class LocationDSConfig {

  @Resource
  Environment env;

  @Bean(name = "lcEntityManagerFactory")
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory()
      throws PropertyVetoException {
    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(locationDataSource());
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

    // Hibernate properties
    Properties additionalProperties = new Properties();
    additionalProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
    additionalProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    additionalProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    //caching
    additionalProperties.put("hibernate.cache.use_second_level_cache", "true");
    additionalProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
    additionalProperties.put("hibernate.cache.use_query_cache", "true");
    additionalProperties.put("hibernate.generate_statistics", "true");
    additionalProperties.put("hibernate.enable_lazy_load_no_trans", "true");

    entityManagerFactory.setJpaProperties(additionalProperties);
    entityManagerFactory.setPackagesToScan("com.logistimo.locations.entity.location");
    return  entityManagerFactory;
  }

  @Bean(name = "lcDataSource")
  @Primary
  public ComboPooledDataSource locationDataSource () throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.min_size")));
    dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.max_size")));
//  dataSource.setAcquireIncrement(acquireIncrement);
//  dataSource.setIdleConnectionTestPeriod(idleTestPeriod);
//  dataSource.setMaxStatements(maxStatements);
    dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("hibernate.c3p0.idle_test_period")));
    dataSource.setJdbcUrl(env.getProperty("spring.location.db.url"));
    dataSource.setPassword(env.getProperty("spring.location.db.password"));
    dataSource.setUser(env.getProperty("spring.location.db.username"));
    dataSource.setDriverClass(env.getProperty("spring.location.db.driver"));
    return dataSource;
  }

  @Bean(name = "lcTransactionManager")
  @Primary
  public PlatformTransactionManager transactionManager() throws PropertyVetoException {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

}

