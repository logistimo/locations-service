package com.logistimo.locations.entity.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistimo.locations.entity.AuditableEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "STATE")
public class State extends AuditableEntity {

    private static final long serialVersionUID = 1L;

  @Column(name = "STATENAME")
  private String name;

  @Column(name = "STATECODE")
  private String code;

  @ManyToOne
  @JoinColumn(name = "COUNTRYID", referencedColumnName = "ID")
  private Country country;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "STATEID")
  private Set<District> districts = new HashSet<District>();

  @Transient
  private List<District> districtUI = new ArrayList<>();

  public State() {
  }

  public void populateDistUI(Set<District> distList) {
    districtUI.clear();
    District dist = null;
    if (distList.size() > 0) {
      for (District s : distList) {
        dist = new District();
        dist.setId(s.getId());
        dist.setName(s.getName());
        districtUI.add(dist);
      }
    }
  }

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

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Set<District> getDistricts() {
    return districts;
  }

  public void setDistricts(Set<District> districts) {
    this.districts = districts;
  }

  public List<District> getDistrictUI() {
    return districtUI;
  }

  public void setDistrictUI(List<District> districtUI) {
    this.districtUI = districtUI;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    State state = (State) o;
    if (getId() != null ? !getId().equals(state.getId()) : state.getId() != null) {
      return false;
    }
    return getName() != null ? getName().equals(state.getName()) : state.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
    return result;
  }
}
