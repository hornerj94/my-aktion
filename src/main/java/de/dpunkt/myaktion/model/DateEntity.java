/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.model;

import java.util.Date;

import javax.persistence.EntityListeners;

/**
 * @author Julian
 *
 */
public class DateEntity {

    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
}
