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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 02/03/17.
 */
@Component
public class LocationCacheUtil {

  private static final Logger log = LoggerFactory.getLogger(LocationCacheUtil.class);

  private static final String COUNTRY = "country";
  private static final String STATE = "state";
  private static final String DISTRICT = "district";
  private static final String SDISTRICT = "subdistrict";
  private static final String CITY = "city";

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

  @Transactional
  private void loadPlaceCache(){
    int limit  = 50;
    int total = (int)cityRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName(CITY);
    while(p < noOfPages) {
      Page<City> res = cityRepository.findAll(new PageRequest(p, limit));
      for(City city:res.getContent()) {
        if (city.getName() != null) {
          cache.putIfAbsent(city.getName(), city);
        }
      }
      p++;
    }
  }

  @Transactional(value = "lcTransactionManager", readOnly = true)
  public void loadCountryCache() {
    int limit  = 50;
    int total = (int)countryRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName(COUNTRY);
    while(p < noOfPages) {
      Page<Country> res = countryRepository.findAll(new PageRequest(p, limit));
      for(Country pl:res.getContent()) {
        try {
          pl.populateStateUI(pl.getStates());
          if (pl.getName() != null) {
            cache.putIfAbsent(pl.getName(), pl);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      p++;
    }
  }

  @Transactional
  private void loadStateCache () {
    int limit  = 50;
    int total = (int)stateRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName(STATE);
    while(p < noOfPages) {
      Page<State> res = stateRepository.findAll(new PageRequest(p, limit));
      for(State pl:res.getContent()) {
        pl.populateDistUI(pl.getDistricts());
        if (pl.getName() != null) {
          cache.putIfAbsent(pl.getName(), pl);
        }
      }
      p++;
    }
  }

  @Transactional
  private void loadDistrictCache () {
    int limit  = 50;
    int total = (int)districtRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName(DISTRICT);
    while(p < noOfPages) {
      Page<District> res = districtRepository.findAll(new PageRequest(p, limit));
      for(District pl:res.getContent()) {
        pl.populateSubDistUI(pl.getSubDistricts());
        if (pl.getName() != null) {
          cache.putIfAbsent(pl.getName(), pl);
        }
      }
      p++;
    }
  }

  @Transactional
  private void loadSubDistrictCache () {
    int limit  = 50;
    int total = (int)subDistrictRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
    Cache cache = getCacheByName(SDISTRICT);
    while(p < noOfPages) {
      Page<SubDistrict> res = subDistrictRepository.findAll(new PageRequest(p, limit));
      for(SubDistrict pl:res.getContent()) {
        if (pl.getName() != null) {
          cache.putIfAbsent(pl.getName(), pl);
        }
      }
      p++;
    }
  }

  public void burstAllCache() {
    burstCacheByName(COUNTRY);
    burstCacheByName(STATE);
    burstCacheByName(DISTRICT);
    burstCacheByName(SDISTRICT);
    burstCacheByName(CITY);
    log.info("cache cleared successfully");
  }

  public void burstCacheByName(String cacheName) {
    Cache cache = getCacheByName(cacheName);
    cache.clear();
  }

  private Cache getCacheByName(String name) {
    return cacheManager.getCache(name);
  }

  public Object getCacheObject(String cache, String key) {
    Cache c = getCacheByName(cache);
    return c.get(key).get();
  }
}
