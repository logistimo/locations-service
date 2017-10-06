package com.logistimo.locations.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by kumargaurav on 02/01/17.
 */
@MappedSuperclass
public class AuditableEntity {

  private static final long serialVersionUID = 3487495895819394L;

    @Column(name = "CREATEDBY")
    protected String createdBy;

    @Column(name = "CREATEDON")
    protected Date createdOn;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
