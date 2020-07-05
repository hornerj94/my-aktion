package de.dpunkt.myaktion.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.services.CampaignService;

@Path("/organizer/campaign")
public class CampaignResource {
    @Inject
    private CampaignService campaignService;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Campaign> getAllCampaigns() {
        List<Campaign> allCampaigns = campaignService.getAllCampaigns();

        allCampaigns.forEach(campaign -> {
            campaign.setDonations(null);
            campaign.setOrganizer(null);
        });

        return allCampaigns;
    }

    @DELETE
    @Path("/{campaignId}")
    public void deleteAktion(@PathParam(value = "campaignId") Long campaignId) {
        campaignService.deleteCampaign(campaignId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Campaign addCampaign(Campaign campaign) {
        return campaignService.addCampaign(campaign);
    }

    @PUT
    @Path("/{campaignId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Campaign updateCampaign(@PathParam(value = "campaignId") Long campaignId,
            Campaign newCampaign) {
        Campaign campaign = campaignService.getCampaign(campaignId);
        campaign.setName(newCampaign.getName());
        campaign.setDonationMinimum(newCampaign.getDonationMinimum());
        campaign.setTargetAmount(newCampaign.getTargetAmount());
        
        newCampaign = campaignService.updateCampaign(campaign);
        newCampaign.setDonations(null);
        newCampaign.setOrganizer(null);
        
        return newCampaign;
    }
}