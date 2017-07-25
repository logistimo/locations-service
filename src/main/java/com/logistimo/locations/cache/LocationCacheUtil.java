package com.logistimo.locations.cache;

import com.logistimo.locations.constants.LocationConstants;
import com.logistimo.locations.entity.location.*;
import com.logistimo.locations.repository.location.*;
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

  public void loadCache() {
    loadCountryCache();
    loadStateCache();
    loadDistrictCache();
    loadSubDistrictCache();
    loadPlaceCache();
  }

  @Transactional(value = "lcTransactionManager", readOnly = true)
  public void loadPlaceCache() {
    int limit  = 50;
    int total = (int)cityRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
      Cache cache = getCacheByName(LocationConstants.CITY);
    while(p < noOfPages) {
      Page<City> res = cityRepository.findAll(new PageRequest(p, limit));
      for(City city:res.getContent()) {
        if (city.getName() != null) {
          cache.putIfAbsent(getCityCacheKey(city), city);
        }
      }
      p++;
    }
  }

  private String getCityCacheKey(City city) {
      StringBuilder key = new StringBuilder();
      key.append(LocationConstants.CITYKEY).append(LocationConstants.HASH).append(city.getCountryId()).append(LocationConstants.HASH)
              .append(city.getStateId()).append(LocationConstants.HASH).append(city.getDistrictId() == null ? "" : city.getDistrictId()).
              append(LocationConstants.HASH).append(city.getSubdistrictId() == null ? "" : city.getSubdistrictId()).append(LocationConstants.HASH).append(city.getName());
    return key.toString();
  }

  @Transactional(value = "lcTransactionManager", readOnly = true)
  public void loadCountryCache() {
    int limit  = 50;
    int total = (int)countryRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
      Cache cache = getCacheByName(LocationConstants.COUNTRY);
    while(p < noOfPages) {
      Page<Country> res = countryRepository.findAll(new PageRequest(p, limit));
      for(Country pl:res.getContent()) {
        try {
          pl.populateStateUI(pl.getStates());
          if (pl.getName() != null) {
              cache.putIfAbsent(getCountryCacheKey(pl), pl);
          }
        } catch (Exception e) {
          log.warn("Issue with country cache reload", e);
        }
      }
      p++;
    }
  }

    private String getCountryCacheKey(Country country) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocationConstants.CNKEY).append(LocationConstants.HASH).append(country.getCode());
        return sb.toString();
    }

  @Transactional(value = "lcTransactionManager", readOnly = true)
  public void loadStateCache() {
    int limit  = 50;
    int total = (int)stateRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
      Cache cache = getCacheByName(LocationConstants.STATE);
    while(p < noOfPages) {
      Page<State> res = stateRepository.findAll(new PageRequest(p, limit));
      for(State pl:res.getContent()) {
        pl.populateDistUI(pl.getDistricts());
        if (pl.getName() != null) {
          cache.putIfAbsent(getStateCacheKey(pl), pl);
        }
      }
      p++;
    }
  }

  private String getStateCacheKey(State state) {
      StringBuilder key = new StringBuilder();
      key.append(LocationConstants.STKEY).append(LocationConstants.HASH).
              append(state.getCountry().getId()).append(LocationConstants.HASH).append(state.getName());
    return key.toString();
  }

  @Transactional(value = "lcTransactionManager", readOnly = true)
  public void loadDistrictCache() {
    int limit  = 50;
    int total = (int)districtRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
      Cache cache = getCacheByName(LocationConstants.DISTRICT);
    while(p < noOfPages) {
      Page<District> res = districtRepository.findAll(new PageRequest(p, limit));
      for(District pl:res.getContent()) {
        pl.populateSubDistUI(pl.getSubDistricts());
        if (pl.getName() != null) {
          cache.putIfAbsent(getDistCacheKey(pl), pl);
        }
      }
      p++;
    }
  }

  private String getDistCacheKey(District dist) {
      StringBuilder key = new StringBuilder();
      key.append(LocationConstants.DISTKEY).append(LocationConstants.HASH).
              append(dist.getState().getId()).append(LocationConstants.HASH).append(dist.getName());
    return key.toString();
  }

  @Transactional(value = "lcTransactionManager", readOnly = true)
  public void loadSubDistrictCache() {
    int limit  = 50;
    int total = (int)subDistrictRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0)
      noOfPages = noOfPages+1;
    int p= 0;
      Cache cache = getCacheByName(LocationConstants.SDISTRICT);
    while(p < noOfPages) {
      Page<SubDistrict> res = subDistrictRepository.findAll(new PageRequest(p, limit));
      for(SubDistrict pl:res.getContent()) {
        if (pl.getName() != null) {
          cache.putIfAbsent(getSubdistCacheKey(pl), pl);
        }
      }
      p++;
    }
  }

  private String getSubdistCacheKey(SubDistrict subdist) {
      StringBuilder key = new StringBuilder();
      key.append(LocationConstants.SDISTKEY).append(LocationConstants.HASH).
              append(subdist.getDistrict().getId()).append(LocationConstants.HASH).append(subdist.getName());
    return key.toString();
  }

  public void burstAllCache() {
      burstCacheByName(LocationConstants.COUNTRY);
      burstCacheByName(LocationConstants.STATE);
      burstCacheByName(LocationConstants.DISTRICT);
      burstCacheByName(LocationConstants.SDISTRICT);
      burstCacheByName(LocationConstants.CITY);
    log.info("cache cleared successfully");
  }

  public void burstCacheByName(String cacheName) {
    Cache cache = getCacheByName(cacheName);
    if (cache != null) {
      cache.clear();
    }
  }

  private Cache getCacheByName(String name) {
    return cacheManager.getCache(name);
  }

  public Object getCacheObject(String cache, String key) {
    Cache c = getCacheByName(cache);
    return c.get(key).get();
  }
}
