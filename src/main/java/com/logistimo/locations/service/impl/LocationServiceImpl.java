package com.logistimo.locations.service.impl;

import com.logistimo.locations.entity.location.City;
import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;
import com.logistimo.locations.exception.ExceptionUtils;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;
import com.logistimo.locations.service.LocationService;
import com.logistimo.locations.service.RepoApi;
import com.logistimo.locations.validation.LCValidationException;
import static com.logistimo.locations.constants.LocationConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
  public LocationResponseModel getPlaceDetail(LocationRequestModel model) {

    validate(model);
    //country detail
    Country country = repoApi.getCountryByCode(model.getCountryCode());
    //state detail
    State state = null;
    if (!StringUtils.isEmpty(model.getState())) {
      state = repoApi.getStateByName(country.getId(), model.getState());
    }
    //district detail
    District district = null;
    if (!StringUtils.isEmpty(model.getDistrict())) {
      district = repoApi.getDistrictByName(state.getId(), model.getDistrict());
    }
    //subdistrict detail
    SubDistrict taluk = null;
    if (!StringUtils.isEmpty(model.getTaluk())) {
      taluk = repoApi.getSubDistrictByName(district.getId(), model.getTaluk());
    }
    //Place detail
    City city = getAndCreate(model, country, state, district, taluk);
    //returning response
    return getResponse(city, country, state, district, taluk);
  }

  private void validate(LocationRequestModel model) {
    //validating the request data
    Set<ConstraintViolation<LocationRequestModel>> violations = validator.validate(model);
    if (!violations.isEmpty() && violations.size() > 0) {
      StringBuilder errBuilder = new StringBuilder();
      violations.forEach(violation -> errBuilder.append(violation.getMessage()));
      log.error("Invalid request with data {} and error {}", model, errBuilder.toString());
      Optional<ConstraintViolation<LocationRequestModel>> optional = violations.stream().findFirst();
      String code;
      if(optional.isPresent()) {
        code = optional.get().getMessage();
      } else {
        code = DEFAULT_ERROR_CODE;
      }
      String msg = ExceptionUtils.constructMessage(code);
      throw new LCValidationException(code, msg);
    }
  }

  @Override
  public List<City> getPlaces(int page, int limit) {
    Pageable pageable = new PageRequest(page,limit);
    return repoApi.getPlaces(pageable).getContent();
  }

  private City getAndCreate(LocationRequestModel model,Country country,State state,District district,SubDistrict subDistrict){

    String placeName = model.getPlace();
    if (StringUtils.isEmpty(placeName)) {
      return null;
    }
    City city = null;
    String countryId = country.getId();
    String stateId = null;
    String distId = null;
    String subdistId = null;
    if (null != state) {
      stateId = state.getId();
    }
    if (null != district) {
      distId = district.getId();
    }
    if (null != subDistrict) {
      subdistId = subDistrict.getId();
    }
    //prevent null key lookup in cache
    city = repoApi.getPlaceByName(countryId, stateId, distId, subdistId, placeName);

    //if no existing place found create one
    if (city == null) {
      city = new City();
      city.setName(placeName);
      city.setCreatedBy(model.getUserName());
      city.setCreatedOn(new Date());
      city.setDistrictId(distId);
      city.setSubdistrictId(subdistId);
      city.setCountryId(countryId);
      city.setStateId(stateId);
      city.setLatitude(model.getLatitude());
      city.setLongitude(model.getLongitude());
      city.setPostalCode(model.getPincode());
      city = repoApi.savePlace(city);
    } else {
      boolean changed = false;
      if (model.getPincode() != null && !model.getPincode().equals(city.getPostalCode())) {
        city.setPostalCode(model.getPincode());
        changed = true;
      }
      if(subdistId != null && city.getSubdistrictId() == null){
        city.setSubdistrictId(subdistId);
        changed = true;
      }
      if(changed) {
        city = repoApi.savePlace(city);
      }
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
