package com.logistimo.locations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * Created by kumargaurav on 08/02/17.
 */
@SpringBootApplication(scanBasePackages = "com.logistimo.locations,com.logistimo.locations.*")
@EnableHystrix
public class Application {


  public static void main (String[] args) {
    SpringApplication.run(Application.class,args);
  }

}
