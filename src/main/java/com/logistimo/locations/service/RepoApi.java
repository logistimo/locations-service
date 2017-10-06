package com.logistimo.locations.service;

import com.logistimo.locations.entity.location.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by kumargaurav on 01/03/17.
 */
public interface RepoApi {

  Country getCountryByCode(String code);

  Country getCountryByName(String name);

  State getStateByName(String countryId, String name);

  District getDistrictByName(String stateId, String name);

  SubDistrict getSubDistrictByName(String distId, String name);

  City getPlaceByName(String name);

  City getPlaceByName(String countryId, String stateId, String distId, String subdistId,
                      String name);

  City savePlace(City city);

  Page<City> getPlaces(Pageable pageable);

  City renamePlace(String cityId, String name);
}
