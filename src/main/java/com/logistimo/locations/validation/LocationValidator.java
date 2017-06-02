package com.logistimo.locations.validation;

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

  private String message;


  @Override
  public void initialize(ValidLocation validLocationHierarchy) {

  }

  @Override
  public boolean isValid(LocationRequestModel s, ConstraintValidatorContext constraintValidatorContext) {

    Country country = repoApi.getCountryByCode(s.getCountryCode());
    if(null == country){
      message = " Invalid Country name \n";
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }
    State state = null;
    if (!StringUtils.isEmpty(s.getState())) {
      state = repoApi.getStateByName(s.getState());
      if (null == state) {
        message = " Invalid state name \n";
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
        return false;
      } else if (!state.getCountry().equals(country)) {
        message = " State country combination not valid \n";
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
        return false;
      }
    }

    District district = null;
    if (!StringUtils.isEmpty(s.getDistrict())) {
      district = repoApi.getDistrictByName(s.getDistrict());
      if(district == null || !district.getState().equals(state)) {
        message = " district state combination not valid \n";
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
      }
    }
    if(!StringUtils.isEmpty(s.getTaluk())) {
      SubDistrict subDistrict = repoApi.getSubDistrictByName(s.getTaluk());
      if (subDistrict == null || !subDistrict.getDistrict().equals(district)) {
        message = " district taluk combination not valid \n";
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
      }
    }
    return true;
  }
}
