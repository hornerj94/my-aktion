/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.util.Log.TecLog;

/**
 * @author Julian
 */
@Stateless
public class CampaignServiceBean implements CampaignService {
    //----------------------------------------------------------------------------------------------

    @Inject
    @TecLog
    private Logger logger;

    @Inject
    EntityManager entityManager;

    //----------------------------------------------------------------------------------------------

    @Override
    public List<Campaign> getAllCampaigns() {
        TypedQuery<Campaign> query =
                entityManager.createNamedQuery(Campaign.findAll, Campaign.class);
        List<Campaign> campaigns = query.getResultList();

        campaigns.forEach(
                campaign -> campaign.setAmountDonatedSoFar(getAmountDonatedSoFar(campaign)));

        logger.log(Level.INFO, "Loaded all campaign entities with query " + Campaign.findAll);

        return campaigns;
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void addCampaign(Campaign campaign) {
        entityManager.persist(campaign);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void updateCampaign(Campaign campaign) {
        entityManager.merge(campaign);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void deleteCampaign(Campaign campaign) {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaign.getId());
        entityManager.remove(managedCampaign);
    }

    //----------------------------------------------------------------------------------------------

    private Double getAmountDonatedSoFar(Campaign campaign) {
        TypedQuery<Double> query =
                entityManager.createNamedQuery(Campaign.getAmountDonatedSoFar, Double.class);
        query.setParameter("campaign", campaign);

        Double result = query.getSingleResult();
        if (result == null)
            result = 0d;

        return result;
    }

    //----------------------------------------------------------------------------------------------
}