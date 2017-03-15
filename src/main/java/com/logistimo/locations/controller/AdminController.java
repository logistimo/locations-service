package com.logistimo.locations.controller;

import com.logistimo.locations.cache.LocationCacheLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 03/03/17.
 */
@RestController
@RequestMapping(path = "/admins")
public class AdminController {

  private static final Logger log = LoggerFactory.getLogger(AdminController.class);

  @Resource
  LocationCacheLoader lcCacheManager;

  @RequestMapping(path = "/reloadcache",method = RequestMethod.GET)
  public @ResponseBody
  String reloadCache() {
    lcCacheManager.reloadCache();
    log.info("locations caches reloaded");
    return "Cache reloaded!!";
  }

  @RequestMapping(path = "/clearcache",method = RequestMethod.GET)//check camelcase is standard or not
  public @ResponseBody String clearCache() {
    lcCacheManager.burstAllCache();
    log.info("locations caches cleared");
    return "Cache cleared!!";
  }
}
