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
  @Cacheable(value = "country", key = "'CN'.concat('#').concat(#code)", unless = "#key != null")
  public Country getCountryByCode(String code) {
    return countryRepository.findByCode(code);
  }

  @Override
  public Country getCountryByName(String name) {
    return countryRepository.findByName(name);
  }

  @Override
  @Cacheable(value = "state", key = "'ST'.concat('#').concat(#countryId).concat('#').concat(#name)", unless = "#key != null")
  public State getStateByName(String countryId, String name) {
    return stateRepository.findByName(countryId, name);
  }


  @Override
  @Cacheable(value = "district", key = "'DST'.concat('#').concat(#stateId).concat('#').concat(#name)", unless = "#key != null")
  public District getDistrictByName(String stateId, String name) {
    return districtRepository.findByName(stateId, name);
  }

  @Override
  //@Cacheable(value = "subdistrict", key = "'SDST'.concat('#').concat(#distId).concat('#').concat(#name)", unless = "#key != null")
  public SubDistrict getSubDistrictByName(String distId, String name) {
    return subDistrictRepository.findByName(distId, name);
  }


  @Override
  @Cacheable(value = "city", unless = "#key != null")
  public City getPlaceByName(String name) {
    return cityRepository.findByPlaceName(name);
  }


  @Override
  @Cacheable(value = "city",
      key = "'CT'.concat('#').concat(#countryId).concat('#').concat(#stateId).concat('#').concat(#distId?:'').concat('#').concat(#name)"
      , unless = "#key != null")
  public City getPlaceByName(String countryId, String stateId, String distId, String subdistId,
                             String name) {
    if (distId != null) {
      return cityRepository.findByCountryStateDistPlaceName(countryId, stateId, distId, name);

    } else {
      return cityRepository.findByCountryStatePlaceName(countryId, stateId, name);
    }
  }


  @Override
  @CachePut(value = "city",
      key = "'CT'.concat('#').concat(#city.countryId).concat('#').concat(#city.stateId).concat('#').concat(#city.districtId?:'').concat('#').concat(#city.name)",
      unless = "#key != null")
  public City savePlace(City city) {
    return cityRepository.save(city);
  }

  @Override
  public Page<City> getPlaces(Pageable pageable) {
    return cityRepository.findAll(pageable);
  }
}

