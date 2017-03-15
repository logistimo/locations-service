package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "COUNTRY")
public class Country extends AuditableEntity {

    @Column(name = "COUNTRYNAME")
    private String name;

    @Column(name = "COUNTRYCODE")
    private String code;

    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name = "COUNTRYID")
    private Set<State> states = new HashSet<State>();

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
