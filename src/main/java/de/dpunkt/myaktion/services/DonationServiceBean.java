/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.model.Donation;

/**
 * @author Julian
 */
@Stateless
public class DonationServiceBean implements DonationService {
    //----------------------------------------------------------------------------------------------

    @Inject
    EntityManager entityManager;

    //----------------------------------------------------------------------------------------------

    @RolesAllowed("Organizer")
    @Override
    public List<Donation> getDonationList(Long campaignId) {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
        List<Donation> donations = managedCampaign.getDonations();
        
        donations.size(); // To avoid Lazy loading exception

        return donations;
    }

    @PermitAll
    @Override
    public void addDonation(Long campaignId, Donation donation) {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
        donation.setCampaign(managedCampaign);
        
        entityManager.persist(donation);
    }
    
    //----------------------------------------------------------------------------------------------
}
