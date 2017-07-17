package com.logistimo.locations.service.impl;

import com.logistimo.locations.entity.location.*;
import com.logistimo.locations.exception.LSServiceException;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;
import com.logistimo.locations.service.LocationService;
import com.logistimo.locations.service.RepoApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by kumargaurav on 14/02/17.
 */
@Service
public class LocationServiceImpl implements LocationService {

  private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

  @Resource
  Validator validator;

  @Resource
  RepoApi repoApi;

  @Override
  public LocationResponseModel getPlaceDetail(LocationRequestModel model)
      throws LSServiceException {
    //validating the request data
    Set<ConstraintViolation<LocationRequestModel>> violations = validator.validate(model);
    if (!violations.isEmpty() && violations.size() > 0) {
      StringBuilder errBuilder = new StringBuilder();
      log.error("Invalid request with bad data {} ", model);
      Iterator<ConstraintViolation<LocationRequestModel>> itr = violations.iterator();
      while(itr.hasNext()){
        errBuilder.append(itr.next().getMessage());
      }
      throw new LSServiceException(errBuilder.toString());
    }
    //country detail
    Country country = repoApi.getCountryByCode(model.getCountryCode());
    //state detail
    State state = repoApi.getStateByName(model.getState());
    //district detail
    District district = repoApi.getDistrictByName(model.getDistrict());
    //subdistrict detail
    SubDistrict taluk = repoApi.getSubDistrictByName(model.getTaluk());
    //Place detail
    City city = getAndCreate(model,country,state,district,taluk);
    //returning response
    return getResponse(city != null ? city : null, country, state != null ? state : null,
        district != null ? district : null, taluk != null ? taluk : null);
  }

  @Override
  public List<City> getPlaces(int page, int limit) {
    Pageable pageable = new PageRequest(page,limit);
    return repoApi.getPlaces(pageable).getContent();
  }

  private City getAndCreate(LocationRequestModel model,Country country,State state,District district,SubDistrict subDistrict){

    City city = null;
    String p = null;
    //prevent null key lookup in cache
    if (model.getPlace() != null && model.getPlace().trim().length() > 0) {
      p = model.getPlace();
      city = repoApi.getPlaceByName(p);
    }

    //if no existing place found create one
    if (city == null && p != null) {
      city = new City();
      city.setName(p);
      city.setCreatedBy(model.getUserName());
      city.setCreatedOn(new Date());
      if (null != district) {
        city.setDistrictId(district.getId());
      }
      if (null != subDistrict) {
        city.setSubdistrictId(subDistrict.getId());
      }
      city.setCountryId(country.getId());
      city.setStateId(state.getId());
      city.setLatitude(model.getLatitude());
      city.setLongitude(model.getLongitude());
      if (model.getPincode() != null) {
        city.setPostalCode(model.getPincode());
      }
      city = repoApi.savePlace(city);
    } else if (city != null) {
      if (model.getPincode() != null && !model.getPincode().equals(city.getPostalCode())) {
        city.setPostalCode(model.getPincode());
      }
      city = repoApi.savePlace(city);
    }
    return  city;
  }

  private LocationResponseModel getResponse(City city,Country country,State state,District dist, SubDistrict taluk) {
    LocationResponseModel m = new LocationResponseModel();
    m.setCountry(country.getName());
    m.setCountryId(country.getId());
    if (state != null) {
      m.setState(state.getName());
      m.setStateId(state.getId());
    }
    if (dist != null) {
      m.setDistrict(dist.getName());
      m.setDistrictId(dist.getId());
    }
    if(taluk != null) {
      m.setTaluk(taluk.getName());
      m.setTalukId(taluk.getId());
    }
    if (city != null) {
      m.setCity(city.getName());
      m.setCityId(city.getId());
      m.setPincode(city.getPostalCode());
    }
    return m;
  }

}
