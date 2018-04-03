package com.logistimo.locations.validation;

import static com.logistimo.locations.constants.LocationConstants.*;
import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;
import com.logistimo.locations.model.LocationRequestModel;
import com.logistimo.locations.service.RepoApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by kumargaurav on 06/03/17.
 */
@Component
public class LocationValidator implements ConstraintValidator<ValidLocation,LocationRequestModel> {

  @Resource
  RepoApi repoApi;

  @Override
  public void initialize(ValidLocation validLocationHierarchy) {

  }

  @Override
  public boolean isValid(LocationRequestModel s, ConstraintValidatorContext constraintValidatorContext) {

    Country country = repoApi.getCountryByCode(s.getCountryCode());
    String message;
    if(null == country){
      message = INVALID_COUNTY_NAME;
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }
    State state = null;
    if (!StringUtils.isEmpty(s.getState())) {
      state = repoApi.getStateByName(country.getId(), s.getState());
      if (null == state) {
        message = INVALID_STATE_NAME;
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
        return false;
      } else if (!state.getCountry().equals(country)) {
        message = INVALID_STATE_COUNTRY;
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
        return false;
      }
    }

    District district = null;
    if (!StringUtils.isEmpty(s.getDistrict())) {
      district = repoApi.getDistrictByName(state.getId(), s.getDistrict());
      if(district == null || !district.getState().equals(state)) {
        message = INVALID_DISTRICT_STATE;
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
      }
    }
    if(!StringUtils.isEmpty(s.getTaluk())) {
      if(district == null) {
        message = INVALID_DISTRICT_EMPTY;
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
        return false;
      }
      SubDistrict subDistrict = repoApi.getSubDistrictByName(district.getId(), s.getTaluk());
      if (subDistrict == null || !subDistrict.getDistrict().equals(district)) {
        message = INVALID_DISTRICT_TALUK;
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
      }
    }
    return true;
  }
}
