/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Julian
 */
@NamedQueries({
        @NamedQuery(name = Campaign.findByOrganizer,
                query = "SELECT c FROM Campaign c WHERE c.organizer = :organizer ORDER BY c.name"),
        @NamedQuery(name = Campaign.findAll, 
                query = "SELECT a FROM Campaign a ORDER BY a.name"),
        @NamedQuery(name = Campaign.getAmountDonatedSoFar,
                query = "SELECT SUM(d.amount) FROM Donation d WHERE d.campaign = :campaign") })
@XmlRootElement
@Entity
@EntityListeners(EntityCreationListener.class)
public class Campaign extends DateEntity {
    public static final String findByOrganizer = "Campaign.findByOrganizer";
    
    public static final String findAll = "Campaign.findAll";
    
    public static final String getAmountDonatedSoFar = "Campaign.getAmountDonatedSoFar";

    //==============================================================================================

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    @Size(min = 4, max = 30, message = "{campaign.name.size}")
    private String name;

    @NotNull(message = "{campaign.targetAmount.notNull}")
    @DecimalMin(value = "10.00", message = "{campaign.targetAmount.decimalMin}")
    private BigDecimal targetAmount;

    @NotNull(message = "{campaign.donationMinimum.notNull}")
    @DecimalMin(value = "1.00", message = "{campaign.donationMinimum.decimalMin}")
    private BigDecimal donationMinimum;

    @Transient
    private BigDecimal amountDonatedSoFar;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "accountName")) })
    private Account account;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.REMOVE)
    private List<Donation> donations;

    @ManyToOne
    private Organizer organizer;

    //----------------------------------------------------------------------------------------------

    public Campaign() {
        account = new Account();
    }

    //----------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getDonationMinimum() {
        return donationMinimum;
    }

    public void setDonationMinimum(BigDecimal donationMinimum) {
        this.donationMinimum = donationMinimum;
    }

    public BigDecimal getAmountDonatedSoFar() {
        return amountDonatedSoFar;
    }

    public void setAmountDonatedSoFar(BigDecimal amountDonatedSoFar) {
        this.amountDonatedSoFar = amountDonatedSoFar;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    //----------------------------------------------------------------------------------------------
}
