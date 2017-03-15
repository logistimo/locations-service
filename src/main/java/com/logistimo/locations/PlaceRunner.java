package com.logistimo.locations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 28/02/17.
 */
@Component
public class PlaceRunner implements CommandLineRunner {


  @Resource
  PlaceLoader loader;

  @Value("${place.start}")
  Boolean run;

  @Override
  public void run(String... strings) throws Exception {

    if(!run) {
      loader.load();
    }
  }
}
