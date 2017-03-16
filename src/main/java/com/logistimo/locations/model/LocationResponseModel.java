package com.logistimo.locations.model;

import java.io.Serializable;

/**
 * Created by kumargaurav on 24/02/17.
 */
public class LocationResponseModel implements Serializable{

  private String city;
  private String country;

  private String countryId;

  private String state;

  private String stateId;

  private String district;

  private String districtId;

  private String taluk;

  private String talukId;

  private String block;

  private String blockId;

  private String place;

  private String placeId;

  public LocationResponseModel() {

  }

  public LocationResponseModel(String country, String countryId, String state,
                               String stateId, String district, String districtId,
                               String taluk, String talukId, String block, String blockId,
                               String city, String placeId) {
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
    this.city = city;
    this.placeId = placeId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getStateId() {
    return stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getDistrictId() {
    return districtId;
  }

  public void setDistrictId(String districtId) {
    this.districtId = districtId;
  }

  public String getTaluk() {
    return taluk;
  }

  public void setTaluk(String taluk) {
    this.taluk = taluk;
  }

  public String getTalukId() {
    return talukId;
  }

  public void setTalukId(String talukId) {
    this.talukId = talukId;
  }

  public String getBlock() {
    return block;
  }

  public void setBlock(String block) {
    this.block = block;
  }

  public String getBlockId() {
    return blockId;
  }

  public void setBlockId(String blockId) {
    this.blockId = blockId;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
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
        ", city='" + place + '\'' +
        ", placeId=" + placeId +
        '}';
  }
}
