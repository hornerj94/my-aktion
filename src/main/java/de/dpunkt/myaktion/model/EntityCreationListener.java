package de.dpunkt.myaktion.model;

import java.util.Date;

import javax.persistence.PrePersist;

public class EntityCreationListener {
    @PrePersist
    void onPreUpdate(Object entity) {
        DateEntity dateEntity = (DateEntity) entity;
        dateEntity.setCreatedAt(new Date());
    }
}
