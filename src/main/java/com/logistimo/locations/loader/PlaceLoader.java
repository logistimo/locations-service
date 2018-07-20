package com.logistimo.locations.loader;

import com.logistimo.locations.entity.logistimo.Kiosk;
import com.logistimo.locations.entity.logistimo.UserAccount;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.model.LocationResponseModel;
import com.logistimo.locations.repository.logistimo.KioskRepository;
import com.logistimo.locations.repository.logistimo.UserAccountRepositpry;
import com.logistimo.locations.service.LocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumargaurav on 14/02/17.
 */
@Component
public class PlaceLoader {

  private static final String UNAME = "location-user";

  private static final String ANAME = "location-migration";

  private static final Logger log = LoggerFactory.getLogger(PlaceLoader.class);

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
    List<Kiosk> mlist = new ArrayList<>();
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
      log.info("processing kiosk with data {}",m);
      try {
        rm = locationService.getPlaceDetail(m);

      } catch (Exception e) {
        continue;
        //Ignore
      }
      if(rm == null)
        continue;
      mlist.add(k);
      log.info("kiosk location response with data {}",rm);
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

      if (rm.getCityId() != null) {
        k.setPlaceId(rm.getCityId());
      }
    }
    if (!mlist.isEmpty()) {
      kioskRepository.save(mlist);
      mlist.stream().forEach(k -> log.info("kiosk with data {} updated", k));
    }
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
    List<UserAccount> mlist = new ArrayList<>();
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
      log.info("processing user with data {}",m);
      try {
        rm = locationService.getPlaceDetail(m);

      } catch (Exception e) {
        continue;
        //Ignore
      }
      if (rm == null) {
        continue;
      }
      mlist.add(k);
      log.info("user location response with data {}",rm);
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

      if (rm.getCityId() != null) {
        k.setPlaceId(rm.getCityId());
      }
    }
    if (!mlist.isEmpty()) {
      userAccountRepositpry.save(mlist);
      mlist.stream().forEach(k -> log.info("user with data {} updated", k));
    }
  }

  public void updateIds () {
    updateKiosk();
    updateUser();
  }

  private void updateKiosk () {

    int limit  = 50;
    int total = kioskRepository.countKioskWithLOcIdsNull();
    int noOfPages = total/limit;
    if(total%limit != 0) {
      noOfPages = noOfPages + 1;
    }
    int p= 0;
    while(p < noOfPages) {
      Page<Kiosk> res = kioskRepository.findKioskWithLocIdsNull(new PageRequest(p, limit));
      processKiosk(res.getContent());
      p++;
    }
  }

  private void updateUser () {
    int limit  = 50;
    int total = userAccountRepositpry.countUserWithLOcIdsNull();
    int noOfPages = total/limit;
    if(total%limit != 0) {
      noOfPages = noOfPages + 1;
    }
    int p= 0;
    while(p < noOfPages) {
      Page<UserAccount> res = userAccountRepositpry.findUserWithLocIdsNull(new PageRequest(p, limit));
      processUser(res.getContent());
      p++;
    }
  }

}
