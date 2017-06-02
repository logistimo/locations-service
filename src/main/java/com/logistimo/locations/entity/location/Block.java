package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 27/02/17.
 */
@Entity
@Table(name = "BLOCK")
public class Block extends AuditableEntity {

  private static final long serialVersionUID = 3487495895819340L;

  @Column(name = "BLOCKNAME")
  private String name;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name="SUBDISTID",referencedColumnName = "ID")
  private SubDistrict subdistrictId;

  @Column(name = "DISTID")
  private Long districtId;

  public Block () {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SubDistrict getSubdistrictId() {
    return subdistrictId;
  }

  public void setSubdistrictId(SubDistrict subdistrictId) {
    this.subdistrictId = subdistrictId;
  }

  public Long getDistrictId() {
    return districtId;
  }

  public void setDistrictId(Long districtId) {
    this.districtId = districtId;
  }
}
