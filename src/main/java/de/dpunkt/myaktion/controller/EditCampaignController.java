package de.dpunkt.myaktion.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.dpunkt.myaktion.data.CampaignProducer;
import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.util.Events.Added;
import de.dpunkt.myaktion.util.Events.Updated;

/**
 * @author Julian
 */
@ViewScoped
@Named
public class EditCampaignController implements Serializable {
    //----------------------------------------------------------------------------------------------

    private static final long serialVersionUID = 2815796004558360299L;

    //==============================================================================================

    @Inject
    private CampaignProducer campaignProducer;

    @Inject @Added
    private Event<Campaign> campaignAddEvent;

    @Inject @Updated
    private Event<Campaign> campaignUpdatedEvent;

    //----------------------------------------------------------------------------------------------

    public String doSave() {
        if (campaignProducer.isAddMode()) {
            campaignAddEvent.fire(campaignProducer.getSelectedCampaign());
        } else {
            campaignUpdatedEvent.fire(campaignProducer.getSelectedCampaign());
        }

        return Pages.LIST_CAMPAIGNS;
    }

    public String doCancel() {
        return Pages.LIST_CAMPAIGNS;
    }

    //----------------------------------------------------------------------------------------------
}