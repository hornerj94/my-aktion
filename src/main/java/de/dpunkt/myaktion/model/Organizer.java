/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Julian
 */
@NamedQueries({ @NamedQuery(name = Organizer.findByEmail,
        query = "SELECT o FROM Organizer o WHERE o.email = :email") })
@Entity
public class Organizer extends DateEntity {
    //----------------------------------------------------------------------------------------------

    public static final String findByEmail = "Organizer.findByEmail";

    //==============================================================================================

    @GeneratedValue
    @Id
    private Long id;
    
    @NotNull
    @Size(min = 3, max = 20, message = "{organizer.firstName.size}")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30, message = "{organizer.lastName.size}")
    private String lastName;

    @Pattern(regexp = ".+@.+", message = "{organizer.email.pattern}")
    private String email;

    @NotNull
    private String password;

    //----------------------------------------------------------------------------------------------

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //----------------------------------------------------------------------------------------------
}