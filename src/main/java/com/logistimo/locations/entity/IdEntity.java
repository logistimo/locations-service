package com.logistimo.locations.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by kumargaurav on 02/01/17.
 */
@MappedSuperclass
public class IdEntity implements Serializable {

  private static final long serialVersionUID = 3487495895819393L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
