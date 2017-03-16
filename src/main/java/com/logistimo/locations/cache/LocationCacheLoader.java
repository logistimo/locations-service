package com.logistimo.locations.cache;

import com.logistimo.locations.entity.location.City;
import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;
import com.logistimo.locations.repository.location.CityRepository;
import com.logistimo.locations.repository.location.CountryRepository;
import com.logistimo.locations.repository.location.DistrictRepository;
import com.logistimo.locations.repository.location.StateRepository;
import com.logistimo.locations.repository.location.SubDistrictRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 02/03/17.
 */
@Component
public class LocationCacheLoader {

  private static final Logger log = LoggerFactory.getLogger(LocationCacheLoader.class);

  @Resource
  CacheManager cacheManager;

  @Resource
  CountryRepository countryRepository;

  @Resource
  StateRepository stateRepository;

  @Resource
  CityRepository cityRepository;

  @Resource
  DistrictRepository districtRepository;

  @Resource
  SubDistrictRepository subDistrictRepository;

  public void reloadCache() {
    burstAllCache();
    loadCache();
    log.info("cache reloaded successfully");
  }

  private void loadCache() {
    loadCountryCache();
    loadStateCache();
    loadDistrictCache();
    loadSubDistrictCache();
    loadPlaceCache();
  }

  private void loadPlaceCache(){
    int limit  = 50;
    int total = (int)cityRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName("city");
    while(p < noOfPages) {
      Page<City> res = cityRepository.findAll(new PageRequest(p, limit));
      for(City city:res.getContent()) {
        cache.putIfAbsent(city.getName(),city);
      }
      p++;
    }
  }

  private void loadCountryCache () {
    int limit  = 50;
    int total = (int)countryRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName("country");
    while(p < noOfPages) {
      Page<Country> res = countryRepository.findAll(new PageRequest(p, limit));
      for(Country pl:res.getContent()) {
        cache.putIfAbsent(pl.getCode(),pl);
      }
      p++;
    }
  }

  private void loadStateCache () {
    int limit  = 50;
    int total = (int)stateRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName("state");
    while(p < noOfPages) {
      Page<State> res = stateRepository.findAll(new PageRequest(p, limit));
      for(State pl:res.getContent()) {
        cache.putIfAbsent(pl.getName(),pl);
      }
      p++;
    }
  }

  private void loadDistrictCache () {
    int limit  = 50;
    int total = (int)districtRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName("district");
    while(p < noOfPages) {
      Page<District> res = districtRepository.findAll(new PageRequest(p, limit));
      for(District pl:res.getContent()) {
        cache.putIfAbsent(pl.getName(),pl);
      }
      p++;
    }
  }

  private void loadSubDistrictCache () {
    int limit  = 50;
    int total = (int)subDistrictRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName("subdistrict");
    while(p < noOfPages) {
      Page<SubDistrict> res = subDistrictRepository.findAll(new PageRequest(p, limit));
      for(SubDistrict pl:res.getContent()) {
        cache.putIfAbsent(pl.getName(),pl);
      }
      p++;
    }
  }

  public void burstAllCache() {
    burstCacheByName("country");
    burstCacheByName("state");
    burstCacheByName("district");
    burstCacheByName("subdistrict");
    burstCacheByName("city");
    log.info("cache cleared successfully");
  }

  public void burstCacheByName(String cacheName) {
    Cache cache = getCacheByName(cacheName);
    cache.clear();
  }

  private Cache getCacheByName(String name) {
    return cacheManager.getCache(name);
  }
}
