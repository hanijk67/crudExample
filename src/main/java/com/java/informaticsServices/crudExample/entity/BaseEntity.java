package com.java.informaticsServices.crudExample.entity;

import jakarta.persistence.*;

import java.sql.Date;

@MappedSuperclass
public abstract class BaseEntity<L> implements Entity<L> {

    @Column(name = "CREATIONDT")
    Date creationDate;

    @Column(name = "LASTMDFDT")
    protected Date lastModifyDate;

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

}
