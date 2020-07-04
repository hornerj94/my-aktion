/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.model.Organizer;
import de.dpunkt.myaktion.util.Log.TecLog;

/**
 * @author Julian
 */
@RolesAllowed("Organizer")
@Stateless
public class CampaignServiceBean implements CampaignService {
    // ----------------------------------------------------------------------------------------------

    @Inject
    @TecLog
    private Logger logger;

    @Inject
    EntityManager entityManager;

    @Resource
    private SessionContext sessionContext;

    // ----------------------------------------------------------------------------------------------

    @Override
    public List<Campaign> getAllCampaigns() {
        TypedQuery<Campaign> query =
                entityManager.createNamedQuery(Campaign.findByOrganizer, Campaign.class);
        query.setParameter("organizer", getLoggedinOrganizer());
        List<Campaign> campaigns = query.getResultList();

        campaigns.forEach(
                campaign -> campaign.setAmountDonatedSoFar(getAmountDonatedSoFar(campaign)));

        logger.log(Level.INFO, "Loaded all campaign entities with query " + Campaign.findAll);

        return campaigns;
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public Campaign addCampaign(Campaign campaign) {
        Organizer organizer = getLoggedinOrganizer();
        campaign.setOrganizer(organizer);
        entityManager.persist(campaign);
        
        return campaign;
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public void updateCampaign(Campaign campaign) {
        entityManager.merge(campaign);
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public void deleteCampaign(Campaign campaign) {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaign.getId());
        entityManager.remove(managedCampaign);
    }

    public void deleteCampaign(Long campaignId) {
        Campaign managedCampaign = getCampaign(campaignId);
        entityManager.remove(managedCampaign);
    }

    public Campaign getCampaign(Long campaignId) {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
  
        return managedCampaign;
    }

    // ----------------------------------------------------------------------------------------------

    private BigDecimal getAmountDonatedSoFar(Campaign campaign) {
        TypedQuery<BigDecimal> query =
                entityManager.createNamedQuery(Campaign.getAmountDonatedSoFar, BigDecimal.class);
        query.setParameter("campaign", campaign);

        BigDecimal result = query.getSingleResult();
        if (result == null)
            result = BigDecimal.ZERO;

        return result;
    }

    // ----------------------------------------------------------------------------------------------

    private Organizer getLoggedinOrganizer() {
        String organizerEmail = sessionContext.getCallerPrincipal().getName();
        Organizer organizer = entityManager.createNamedQuery(Organizer.findByEmail, Organizer.class)
                .setParameter("email", organizerEmail).getSingleResult();
        return organizer;
    }

    // ----------------------------------------------------------------------------------------------
}