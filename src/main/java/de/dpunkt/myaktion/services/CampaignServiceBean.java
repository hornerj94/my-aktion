/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.util.Log.TecLog;

/**
 * @author Julian
 */
@RequestScoped
public class CampaignServiceBean implements CampaignService {
    @Inject @TecLog
    private Logger logger;

    @Override
    public List<Campaign> getAllCampaigns() {
        logger.log(Level.INFO, "getAllCampaigns");
        
        return new LinkedList<Campaign>();
    }
}