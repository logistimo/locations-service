package com.logistimo.locations.service.impl;

import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.Place;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;
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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    SubDistrict subDistrict = repoApi.getSubDistrictByName(model.getTaluk());
    //Place detail
    Place place = getAndCreate(model,country,state,district,subDistrict);

    //returning response
    return getResponse(place,country.getName(),state.getName(),district != null?district.getName():null,subDistrict !=null?subDistrict.getName():null);
  }

  @Override
  public List<Place> getPlaces(int page, int limit) {
    Pageable pageable = new PageRequest(page,limit);
    return repoApi.getPlaces(pageable).getContent();
  }

  private Place getAndCreate(LocationRequestModel model,Country country,State state,District district,SubDistrict subDistrict){

    Place place = repoApi.getPlaceByName(model.getPlace());
    //if no existing place found create one
    if (place == null) {
      place = new Place();
      place.setName(model.getPlace());
      place.setCreatedBy(model.getUserName());
      place.setCreatedOn(new Date());
      if (null != district) {
        place.setDistrictId(district.getId());
      }
      if (null != subDistrict) {
        place.setSubdistrictId(subDistrict.getId());
      }
      place.setCountryId(country.getId());
      place.setStateId(state.getId());
      place.setLatitude(model.getLatitude());
      place.setLongitude(model.getLongitude());
      if (null != model.getPincode()) {
        place.setPostalCode(Integer.valueOf(model.getPincode()));
      }
      place = repoApi.savePlace(place);
    }
    return  place;
  }

  private LocationResponseModel getResponse(Place place,String country,String state,String dist, String taluk) {
    LocationResponseModel m = new LocationResponseModel();
    m.setCountry(country);
    m.setCountryId(place.getCountryId());
    m.setState(state);
    m.setStateId(place.getStateId());
    m.setDistrict(dist);
    m.setDistrictId(place.getDistrictId());
    m.setTaluk(taluk);
    m.setTalukId(place.getSubdistrictId());
    m.setPlace(place.getName());
    m.setPlaceId(place.getId());
    return m;
  }

}
