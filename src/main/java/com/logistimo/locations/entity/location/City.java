package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "CITY")
public class City extends AuditableEntity {

  private static final long serialVersionUID = 3487495895819399L;

    @Column(name = "PLACENAME")
    private String name;

    @Column(name = "PLACECODE")
    private String code;

    @Column(name = "POSTALCODE")
    private String postalCode;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @Column(name = "COUNTRYID")
    private String countryId;

    @Column(name = "STATEID")
    private String stateId;

    @Column(name = "BLOCKID")
    private String blockId;

    @Column(name = "SUBDISTID")
    private String subdistrictId;

    @Column(name = "DISTID")
    private String districtId;

    public City () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
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

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getSubdistrictId() {
        return subdistrictId;
    }

    public void setSubdistrictId(String subdistrictId) {
        this.subdistrictId = subdistrictId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
}
