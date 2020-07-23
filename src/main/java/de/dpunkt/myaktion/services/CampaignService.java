package de.dpunkt.myaktion.services;

import java.util.List;

import de.dpunkt.myaktion.model.Campaign;

/**
 * @author Julian
 */
public interface CampaignService {
    //----------------------------------------------------------------------------------------------

    List<Campaign> getAllCampaigns();

    Campaign addCampaign(Campaign campaign);
    
    void deleteCampaign(Campaign campaign);

    void deleteCampaign(Long campaignId);
    
    Campaign updateCampaign(Campaign campaign);
    
    Campaign getCampaign(Long campaignId);
    
    //----------------------------------------------------------------------------------------------
}