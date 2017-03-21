package com.logistimo.locations.controller;

import com.logistimo.locations.entity.location.City;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;
import com.logistimo.locations.service.LocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 24/02/17.
 */
@RestController
@RequestMapping(path = "/locations")
public class LocationController {

  private static final Logger log = LoggerFactory.getLogger(LocationController.class);

  @Resource
  LocationService locationService;

  @RequestMapping(path = "/city",method = RequestMethod.POST)
  public @ResponseBody LocationResponseModel getLocationDetails (@RequestBody final LocationRequestModel location) {
    return locationService.getPlaceDetail(location);
  }

  @RequestMapping(path = "/places",method = RequestMethod.GET)
  public @ResponseBody List<City> getPlaces(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "50") int limit) {
    return locationService.getPlaces(pageNo, limit);
  }

}
