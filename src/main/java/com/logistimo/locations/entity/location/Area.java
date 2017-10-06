package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;
import com.logistimo.locations.entity.Identifiable;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 04/10/17.
 */
@Entity
@Table(name = "AREA")
public class Area extends AuditableEntity implements Identifiable<String> {

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "com.logistimo.locations.entity.identifier.LocationStringSeqIdentifier",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_prefix", value = "AR#"),
      }
  )
  @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
  protected String id;

  @Column(name = "AREANAME")
  private String name;

  @Column(name = "POSTALCODE")
  private String postalCode;

  @Column(name = "LONGITUDE")
  private Double longitude;

  @Column(name = "LATITUDE")
  private Double latitude;

  @Column(name = "ISALIAS")
  private Boolean isAlias;

  @Column(name = "CITYID")
  private String cityId;

  @Column(name = "AREAKEYID")
  private String areaKeyId;

  public Area() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Boolean getAlias() {
    return isAlias;
  }

  public void setAlias(Boolean alias) {
    isAlias = alias;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getAreaKeyId() {
    return areaKeyId;
  }

  public void setAreaKeyId(String areaKeyId) {
    this.areaKeyId = areaKeyId;
  }

  @Override
  public String getEntityName() {
    return null;
  }
}
