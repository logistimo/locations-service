package com.logistimo.locations.job;

import com.logistimo.locations.cache.LocationCacheUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 11/08/17.
 */
@Component
public class LocationJob {

  private static final Logger log = LoggerFactory.getLogger(LocationJob.class);

  @Resource
  LocationCacheUtil cacheUtil;

  private AtomicInteger counter = new AtomicInteger(0);

  @Scheduled(cron = "${cron.expression}")
  public void run() {
    cacheUtil.reloadCache();
    log.info("location job with id {} run at {}", counter.getAndIncrement(), new Date());
  }
}
