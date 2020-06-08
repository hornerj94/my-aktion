/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.dpunkt.myaktion.model.Campaign;

/**
 * @author Julian
 */
@SessionScoped
@Named
public class CampaignProducer implements Serializable {
    //----------------------------------------------------------------------------------------------

    private static final long serialVersionUID = -1828660647917534556L;

    //==============================================================================================

    private enum Mode {
        EDIT, ADD
    }

    //----------------------------------------------------------------------------------------------

    private Campaign campaign;
    
    private Mode mode;

    //----------------------------------------------------------------------------------------------

    public Campaign getSelectedCampaign() {
        return campaign;
    }

    public void setSelectedCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    //----------------------------------------------------------------------------------------------
    public boolean isAddMode() {
        return mode == Mode.ADD;
    }
    public void prepareAddCampaign() {
        this.campaign = new Campaign();
        this.mode = Mode.ADD;
    }

    public void prepareEditCampaign(Campaign campaign) {
        this.campaign = campaign;
        this.mode = Mode.EDIT;
    }

    //----------------------------------------------------------------------------------------------
}