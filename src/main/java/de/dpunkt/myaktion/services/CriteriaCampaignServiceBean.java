/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.model.Donation;
import de.dpunkt.myaktion.util.Log.TecLog;

/**
 * @author Julian
 */
@Stateless
@Alternative
public class CriteriaCampaignServiceBean implements CampaignService {
    //----------------------------------------------------------------------------------------------

    @Inject
    @TecLog
    private Logger logger;

    @Inject
    EntityManager entityManager;

    //----------------------------------------------------------------------------------------------

    @Override
    public List<Campaign> getAllCampaigns() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Campaign> criteriaQuery = cb.createQuery(Campaign.class);
        
        Root<Campaign> campaignRoot = criteriaQuery.from(Campaign.class);
        criteriaQuery.select(campaignRoot).orderBy(cb.asc(campaignRoot.get("name")));
        
        TypedQuery<Campaign> query = entityManager.createQuery(criteriaQuery);
        List<Campaign> campaigns = query.getResultList();

//        campaigns.forEach(campaign -> 
//                campaign.setAmountDonatedSoFar(new BigDecimal(getAmountDonatedSoFar(campaign))));
        
        for (Campaign campaign : campaigns) {
            campaign.setAmountDonatedSoFar(new BigDecimal(getAmountDonatedSoFar(campaign)));
        }
        
        logger.log(Level.INFO, "Loaded all campaign entities with criteria query");

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
        Root<Donation> donation = criteriaQuery.from(Donation.class);
        
        ParameterExpression<Campaign> campaignParameter = criteriaBuilder.parameter(Campaign.class);
        
        criteriaQuery.select(criteriaBuilder.sumAsDouble(donation.get("amount")))
                .where(criteriaBuilder.equal(donation.get("campaign"), campaignParameter));        
        
        TypedQuery<Double> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(campaignParameter, campaign);
        
        Double result = query.getSingleResult();        
        if (result == null) 
            result = 0d;

        return result;
    }

    //----------------------------------------------------------------------------------------------
}