package com.logistimo.locations.service;

import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.Place;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by kumargaurav on 01/03/17.
 */
public interface RepoApi {

  Country getCountryByCode(String code);

  Country getCountryByName(String name);

  State getStateByName(String name);

  District getDistrictByName (String name);

  SubDistrict getSubDistrictByName(String name);

  Place getPlaceByName(String name);

  Place savePlace(Place place);

  Page<Place> getPlaces(Pageable pageable);
}
