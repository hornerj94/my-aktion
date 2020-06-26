/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import de.dpunkt.myaktion.model.Campaign;

/**
 * @author Julian
 */
@RequestScoped
public class CampaignServiceBean implements CampaignService {
    @Override
    public List<Campaign> getAllCampaigns() {
        return new LinkedList<Campaign>();
    }
}