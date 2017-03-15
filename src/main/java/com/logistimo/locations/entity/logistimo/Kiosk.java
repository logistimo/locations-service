package com.logistimo.locations.entity.logistimo;

import com.logistimo.locations.entity.AuditableEntity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 14/02/17.
 */
@Entity
@Table(name = "KIOSK")
@AttributeOverrides({
    @AttributeOverride(name = "id",column = @Column(name = "KIOSKID")),
    @AttributeOverride(name = "createdBy",column = @Column(name = "RGDBY")),
    @AttributeOverride(name = "createdOn",column = @Column(name = "LASTUPDATED"))
})
public class Kiosk extends AuditableEntity {


  @Column(name = "KIOSKID",insertable=false, updatable=false)
  private Long kioskId;

  @Column(name = "SDID")
  private Long domainId;

  @Column(name = "RGDBY", insertable=false, updatable=false)
  private String rgdBy;

  @Column(name = "LASTUPDATED", insertable=false, updatable=false)
  private Date lastUpdated;

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
  private Long countryId;

  @Column(name = "STATEID")
  private Long stateId;

  @Column(name = "DISTID")
  private Long districtId;

  @Column(name = "SUBDISTID")
  private Long subdistrictId;

  @Column(name = "CITYID")
  private Long placeId;


  public Kiosk () {}

  public Long getKioskId() {
    return kioskId;
  }

  public void setKioskId(Long kioskId) {
    this.kioskId = kioskId;
  }

  public String getRgdBy() {
    return rgdBy;
  }

  public void setRgdBy(String rgdBy) {
    this.rgdBy = rgdBy;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
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

  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }

  public Long getStateId() {
    return stateId;
  }

  public void setStateId(Long stateId) {
    this.stateId = stateId;
  }

  public Long getDistrictId() {
    return districtId;
  }

  public void setDistrictId(Long districtId) {
    this.districtId = districtId;
  }

  public Long getSubdistrictId() {
    return subdistrictId;
  }

  public void setSubdistrictId(Long subdistrictId) {
    this.subdistrictId = subdistrictId;
  }

  public Long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(Long placeId) {
    this.placeId = placeId;
  }
}
