package com.logistimo.locations.entity.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistimo.locations.entity.AuditableEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "COUNTRY")
public class Country extends AuditableEntity {

  private static final long serialVersionUID = 3487495895819395L;

    @Column(name = "COUNTRYNAME")
    private String name;

    @Column(name = "COUNTRYCODE")
    private String code;

  @JsonIgnore
    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name = "COUNTRYID")
    private Set<State> states = new HashSet<State>();

  @Transient
  private List<State> stateUI = new ArrayList<>();

    public Country () {}


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

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

  public List<State> getStateUI() {
    return stateUI;
  }

  public void setStateUI(List<State> stateUI) {
    this.stateUI = stateUI;
  }

  public void populateStateUI(Set<State> stateList) {
    stateUI.clear();
    State state = null;
    if (stateList.size() > 0) {
      for (State s : stateList) {
        state = new State();
        state.setId(s.getId());
        state.setName(s.getName());
        stateUI.add(state);
      }
    }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Country country = (Country) o;

        if (getId() != null ? !getId().equals(country.getId()) : country.getId() != null) {
            return false;
        }

        if (getName() != null ? !getName().equals(country.getName()) : country.getName() != null) {
            return false;
        }
        return getCode() != null ? getCode().equals(country.getCode()) : country.getCode() == null;
    }

}
