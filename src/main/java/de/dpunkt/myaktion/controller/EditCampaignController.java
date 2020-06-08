/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.dpunkt.myaktion.data.CampaignListProducer;
import de.dpunkt.myaktion.data.CampaignProducer;

/**
 * @author Julian
 */
@SessionScoped
@Named
public class EditCampaignController implements Serializable {
    //----------------------------------------------------------------------------------------------

    private static final long serialVersionUID = 2815796004558360299L;
    
    //==============================================================================================

    @Inject
    private CampaignListProducer campaignListProducer;
    
    @Inject
    private CampaignProducer campaignProducer;

    //----------------------------------------------------------------------------------------------

    public String doSave() {
        if (campaignProducer.isAddMode()) {
            campaignListProducer.getCampaigns().add(campaignProducer.getSelectedCampaign());
        }
        
        return Pages.LIST_CAMPAIGNS;
    }

    public String doCancel() {
        return Pages.LIST_CAMPAIGNS;
    }

    //----------------------------------------------------------------------------------------------
}