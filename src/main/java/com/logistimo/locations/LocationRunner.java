package com.logistimo.locations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 08/02/17.
 */
@Component
public class LocationRunner implements CommandLineRunner {

  @Resource
  LocationLoader loader;

  @Value("${city.start}")
  Boolean run;

  @Override
  public void run(String... strings) throws Exception {

    if(!run) {
      loader.load();
    }
  }
}
