package com.logistimo.locations;

import com.logistimo.locations.entity.logistimo.Kiosk;
import com.logistimo.locations.entity.logistimo.UserAccount;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;
import com.logistimo.locations.repository.logistimo.KioskRepository;
import com.logistimo.locations.repository.logistimo.UserAccountRepositpry;
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

  private static final String UNAME = "location-user";

  private static final String ANAME = "location-migration";

  @Resource
  KioskRepository kioskRepository;

  @Resource
  UserAccountRepositpry userAccountRepositpry;

  @Resource
  LocationService locationService;


  public void load() {
    loadKiosk();
    loadUser();
  }

  private void loadKiosk() {

    int limit  = 50;
    int total = (int)kioskRepository.count();
    int noOfPages = total/limit;
    if(total%limit != 0) {
      noOfPages = noOfPages + 1;
    }
    int p= 0;
    while(p < noOfPages) {
      Page<Kiosk> res = kioskRepository.findAllKiosk(new PageRequest(p, limit));
      processKiosk(res.getContent());
      p++;
    }
  }

  private void processKiosk(List<Kiosk> list) {
    LocationRequestModel m = null;
    LocationResponseModel rm = null;
    for (Kiosk k :list) {
      m = new LocationRequestModel();
      m.setCountryCode(k.getCountry());
      m.setState(k.getState());
      m.setDistrict(k.getDistrict());
      m.setTaluk(k.getSubDistrict());
      m.setPlace(k.getCity());
      //test set up
      m.setUserName(UNAME);
      m.setAppName(ANAME);
      try {
        rm = locationService.getPlaceDetail(m);

      } catch (Exception e) {
        continue;
        //Ignore
      }
      if(rm == null)
        continue;
      if (null != rm.getCountryId()) {
        k.setCountryId(rm.getCountryId());
      }
      if (null != rm.getStateId()) {
        k.setStateId(rm.getStateId());
      }
      if (null != rm.getDistrictId()) {
        k.setDistrictId(rm.getDistrictId());
      }
      if (null != rm.getTalukId()) {
        k.setSubdistrictId(rm.getTalukId());
      }

      if (rm.getPlaceId() != null) {
        k.setPlaceId(rm.getPlaceId());
      }
    }
    kioskRepository.save(list);
  }

  private void loadUser() {

    int limit = 50;
    int total = (int) userAccountRepositpry.count();
    int noOfPages = total / limit;
    if (total % limit != 0) {
      noOfPages = noOfPages + 1;
    }
    int p = 0;
    while (p < noOfPages) {
      Page<UserAccount> res = userAccountRepositpry.findAllUser(new PageRequest(p, limit));
      processUser(res.getContent());
      p++;
    }
  }

  private void processUser(List<UserAccount> list) {
    LocationRequestModel m = null;
    LocationResponseModel rm = null;
    for (UserAccount k : list) {
      m = new LocationRequestModel();
      m.setCountryCode(k.getCountry());
      m.setState(k.getState());
      m.setDistrict(k.getDistrict());
      m.setTaluk(k.getSubDistrict());
      m.setPlace(k.getCity());
      //test set up
      m.setUserName(UNAME);
      m.setAppName(ANAME);
      try {
        rm = locationService.getPlaceDetail(m);

      } catch (Exception e) {
        continue;
        //Ignore
      }
      if (rm == null) {
        continue;
      }
      if (null != rm.getCountryId()) {
        k.setCountryId(rm.getCountryId());
      }
      if (null != rm.getStateId()) {
        k.setStateId(rm.getStateId());
      }
      if (null != rm.getDistrictId()) {
        k.setDistrictId(rm.getDistrictId());
      }
      if (null != rm.getTalukId()) {
        k.setSubdistrictId(rm.getTalukId());
      }

      if (rm.getPlaceId() != null) {
        k.setPlaceId(rm.getPlaceId());
      }
    }
    userAccountRepositpry.save(list);
  }

}
