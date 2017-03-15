package com.logistimo.locations.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by kumargaurav on 02/01/17.
 */
@MappedSuperclass
public class IdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
