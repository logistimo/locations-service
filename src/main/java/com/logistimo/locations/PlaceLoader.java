package com.logistimo.locations;

import com.logistimo.locations.entity.logistimo.Kiosk;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;
import com.logistimo.locations.repository.logistimo.KioskRepository;
import com.logistimo.locations.service.LocationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 14/02/17.
 */
@Component
public class PlaceLoader {

  @Resource
  KioskRepository kioskRepository;

  @Resource
  LocationService locationService;



  public void load ()  {

    int limit  = 50;
    int total = (int)kioskRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0) {
      noOfPages = noOfPages + 1;
    }
    int p= 0;
    while(p < noOfPages) {
      Page<Kiosk> res = kioskRepository.findAllKiosk(new PageRequest(p, limit));
      process(res.getContent());
      p++;
    }
  }

  private void process(List<Kiosk> list) {
    LocationRequestModel m = null;
    LocationResponseModel rm = null;
    for (Kiosk k :list) {
      m = new LocationRequestModel();
      m.setCountryCode(k.getCountry());
      m.setState(k.getState());
      m.setDistrict(k.getDistrict());
      m.setTaluk(k.getSubDistrict());
      m.setPlace(k.getCity());
      try {
        rm = locationService.getPlaceDetail(m);

      } catch (Exception e) {
        continue;
        //Ignore
      }
      if(rm == null)
        continue;
      if (null != rm.getCountryId())
      k.setCountryId(new Long(rm.getCountryId()));
      if (null != rm.getStateId())
      k.setStateId(new Long(rm.getStateId()));
      if (null != rm.getDistrictId())
      k.setDistrictId(new Long(rm.getDistrictId()));
      if (null != rm.getTalukId())
      k.setSubdistrictId(new Long(rm.getTalukId()));

      if(rm.getPlaceId() != null)
      k.setPlaceId(new Long(rm.getPlaceId()));
    }
    kioskRepository.save(list);
  }

}
