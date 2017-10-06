package com.logistimo.locations.service;

import com.logistimo.locations.entity.location.City;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;

import java.util.List;

/**
 * Created by kumargaurav on 14/02/17.
 */
public interface LocationService {

  LocationResponseModel getPlaceDetail(LocationRequestModel model);

  List<City> getPlaces(int page, int limit);
}
