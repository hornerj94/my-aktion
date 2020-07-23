/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.model.Donation;
import de.dpunkt.myaktion.model.Donation.Status;
import de.dpunkt.myaktion.services.exceptions.ObjectNotFoundException;
import de.dpunkt.myaktion.util.Log.TecLog;

/**
 * @author Julian
 */
@Stateless
public class DonationServiceBean implements DonationService {
    //----------------------------------------------------------------------------------------------

    @Inject
    EntityManager entityManager;

    @Inject
    @TecLog
    private Logger logger;

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

    @PermitAll
    @Override
    public void transferDonations() {
        logger.log(Level.INFO, "log.transferDonation.start");

        TypedQuery<Donation> query =
                entityManager.createNamedQuery(Donation.findByStatus, Donation.class);
        query.setParameter("status", Status.IN_PROCESS);

        List<Donation> donations = query.getResultList();
        donations.forEach(donation -> donation.setStatus(Status.TRANSFERRED));

        logger.log(Level.INFO, "log.transferDonation.done", new Object[] { donations.size() });
    }

    @PermitAll
    @Override
    public List<Donation> getDonationListPublic(Long campaignId) throws ObjectNotFoundException {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
        if (managedCampaign == null) {
            throw new ObjectNotFoundException();
        }

        List<Donation> donations = managedCampaign.getDonations();
        final Function<Donation, Donation> donationFilter = donation -> {
            Donation filtered = new Donation();
            filtered.setAmount(donation.getAmount());
            filtered.setDonorName(donation.getDonorName());

            return filtered;
        };

        return donations.stream().map(donationFilter).collect(Collectors.toList());
    }

    //----------------------------------------------------------------------------------------------
}
