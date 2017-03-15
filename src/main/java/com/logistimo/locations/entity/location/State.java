package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "STATE")
public class State extends AuditableEntity {

    @Column(name = "STATENAME")
    private String name;

    @Column(name = "STATECODE")
    private String code;

    @ManyToOne
    @JoinColumn(name="COUNTRYID",referencedColumnName = "ID")
    private Country country;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "STATEID")
    private Set<District> districts = new HashSet<District>();

    public State () {}

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

}
