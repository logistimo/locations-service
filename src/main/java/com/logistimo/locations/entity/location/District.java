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
@Table(name = "DISTRICT")
public class District extends AuditableEntity {

    @Column(name = "DISTNAME")
    private String name;

    @Column(name = "DISTCODE")
    private String code;

    @ManyToOne
    @JoinColumn(name="STATEID",referencedColumnName = "ID")
    private State state;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTID")
    private Set<SubDistrict> subDistricts = new HashSet<SubDistrict>();

    public District () {}

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<SubDistrict> getSubDistricts() {
        return subDistricts;
    }

    public void setSubDistricts(Set<SubDistrict> subDistricts) {
        this.subDistricts = subDistricts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        District district = (District) o;
        if (getId() != null ? !getId().equals(district.getId()) : district.getId() != null) {
            return false;
        }
        return getName() != null ? getName().equals(district.getName())
            : district.getName() == null;
    }

}
