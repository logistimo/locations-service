package com.logistimo.locations.model;

import java.io.Serializable;

/**
 * Created by kumargaurav on 24/02/17.
 */
public class LocationResponseModel implements Serializable{

  private String country;

  private Long countryId;

  private String state;

  private Long stateId;

  private String district;

  private Long districtId;

  private String taluk;

  private Long talukId;

  private String block;

  private Long blockId;

  private String place;

  private Long placeId;

  public LocationResponseModel() {

  }

  public LocationResponseModel(String country, Long countryId, String state,
                               Long stateId, String district, Long districtId,
                               String taluk, Long talukId, String block, Long blockId,
                               String place, Long placeId) {
    this.country = country;
    this.countryId = countryId;
    this.state = state;
    this.stateId = stateId;
    this.district = district;
    this.districtId = districtId;
    this.taluk = taluk;
    this.talukId = talukId;
    this.block = block;
    this.blockId = blockId;
    this.place = place;
    this.placeId = placeId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Long getStateId() {
    return stateId;
  }

  public void setStateId(Long stateId) {
    this.stateId = stateId;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public Long getDistrictId() {
    return districtId;
  }

  public void setDistrictId(Long districtId) {
    this.districtId = districtId;
  }

  public String getTaluk() {
    return taluk;
  }

  public void setTaluk(String taluk) {
    this.taluk = taluk;
  }

  public Long getTalukId() {
    return talukId;
  }

  public void setTalukId(Long talukId) {
    this.talukId = talukId;
  }

  public String getBlock() {
    return block;
  }

  public void setBlock(String block) {
    this.block = block;
  }

  public Long getBlockId() {
    return blockId;
  }

  public void setBlockId(Long blockId) {
    this.blockId = blockId;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public Long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(Long placeId) {
    this.placeId = placeId;
  }

  @Override
  public String toString() {
    return "LocationResponseModel{" +
        "country='" + country + '\'' +
        ", countryId=" + countryId +
        ", state='" + state + '\'' +
        ", stateId=" + stateId +
        ", district='" + district + '\'' +
        ", districtId=" + districtId +
        ", taluk='" + taluk + '\'' +
        ", talukId=" + talukId +
        ", block='" + block + '\'' +
        ", blockId=" + blockId +
        ", place='" + place + '\'' +
        ", placeId=" + placeId +
        '}';
  }
}
