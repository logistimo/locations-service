package com.logistimo.locations;

import com.logistimo.locations.exception.BadRequestException;
import com.logistimo.locations.loader.LocationLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by kumargaurav on 13/09/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Config.class)
//@TestPropertySource("classpath:application-test.properties")
public class LoaderTest {

  @Test
  public void testLoadwithoutJson() throws IOException {

  }

  @Test
  public void testLoadwithJson() {

  }

}
