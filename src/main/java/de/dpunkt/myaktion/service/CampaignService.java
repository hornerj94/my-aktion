/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.service;

import java.util.List;

import de.dpunkt.myaktion.model.Campaign;

/**
 * @author Julian
 */
public interface CampaignService {
    List<Campaign> getAllCampaigns();
}