package com.logistimo.locations.service.impl;

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
import com.logistimo.locations.service.RepoApi;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 01/03/17.
 */
@Service
public class RepoApiImpl implements RepoApi {

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


  @Override
  @Cacheable(value ="country")
  public Country getCountryByCode(String code) {
    return countryRepository.findByCode(code);
  }

  @Override
  public Country getCountryByName(String name) {
    return countryRepository.findByName(name);
  }

  @Override
  @Cacheable(value ="state")
  public State getStateByName(String name) {
    return stateRepository.findByName(name);
  }

  @Override
  @Cacheable(value ="district")
  public District getDistrictByName(String name) {
    return districtRepository.findByName(name);
  }

  @Override
  @Cacheable(value ="subdistrict")
  public SubDistrict getSubDistrictByName(String name) {
    return subDistrictRepository.findByName(name);
  }

  @Override
  @Cacheable(value ="city")
  public City getPlaceByName(String name) {
    return cityRepository.findByPlaceName(name);
  }

  @Override
  @CachePut(value = "city",key = "#city.name")
  public City savePlace(City city) {
    return  cityRepository.save(city);
  }

  @Override
  public Page<City> getPlaces(Pageable pageable) {
    return cityRepository.findAll(pageable);
  }
}

