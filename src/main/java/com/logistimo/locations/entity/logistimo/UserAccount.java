package com.logistimo.locations.entity.logistimo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 14/02/17.
 */

@Entity
@Table(name = "USERACCOUNT")
/*@AttributeOverrides({
    @AttributeOverride(name = "id",column = @Column(name = "USERID")),
    @AttributeOverride(name = "createdBy",column = @Column(name = "REGISTEREDBY")),
    @AttributeOverride(name = "createdOn",column = @Column(name = "UO"))
})*/
public class UserAccount {

  @Id
  private String userId;

  @Column(name = "DID")
  private Long domainId;

  @Column(name = "REGISTEREDBY")
  private String rgdBy;

  @Column(name = "UO")
  private Date updatedOn;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "STATE")
  private String state;

  @Column(name = "DISTRICT")
  private String district;

  @Column(name = "TALUK")
  private String subDistrict;

  @Column(name = "CITY")
  private String city;

  @Column(name = "PINCODE")
  private String postalCode;

  @Column(name = "LONGITUDE")
  private Double longitude;

  @Column(name = "LATITUDE")
  private Double latitude;

  @Column(name = "COUNTRYID")
  private String countryId;

  @Column(name = "STATEID")
  private String stateId;

  @Column(name = "DISTID")
  private String districtId;

  @Column(name = "SUBDISTID")
  private String subdistrictId;

  @Column(name = "PLACEID")
  private String placeId;

  public UserAccount () {}

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRgdBy() {
    return rgdBy;
  }

  public void setRgdBy(String rgdBy) {
    this.rgdBy = rgdBy;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getSubDistrict() {
    return subDistrict;
  }

  public void setSubDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getStateId() {
    return stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public String getDistrictId() {
    return districtId;
  }

  public void setDistrictId(String districtId) {
    this.districtId = districtId;
  }

  public String getSubdistrictId() {
    return subdistrictId;
  }

  public void setSubdistrictId(String subdistrictId) {
    this.subdistrictId = subdistrictId;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }
}

