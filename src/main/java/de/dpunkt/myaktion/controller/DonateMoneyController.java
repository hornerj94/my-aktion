package de.dpunkt.myaktion.controller;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.dpunkt.myaktion.model.Donation;
import de.dpunkt.myaktion.model.Donation.Status;
import de.dpunkt.myaktion.services.DonationService;
import de.dpunkt.myaktion.util.Log.FachLog;

/**
 * @author Julian
 */
@ViewScoped
@Named
public class DonateMoneyController implements Serializable {
    //----------------------------------------------------------------------------------------------

    private static final long serialVersionUID = 5493038842003809106L;

    //==============================================================================================

    private String textColor = "000000";
    
    private String bgColor = "ffffff";
    
    private Long campaignId;
    
    private Donation donation;

    //----------------------------------------------------------------------------------------------

    @Inject
    private FacesContext facesContext;
    
    @Inject @FachLog
    private Logger logger;
    
    @Inject
    private DonationService donationService;
    
    //----------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {
        this.donation = new Donation();
    }
    
    //----------------------------------------------------------------------------------------------

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    //----------------------------------------------------------------------------------------------

    public String doDonation() {       
        getDonation().setStatus(Status.IN_PROCESS);
        donationService.addDonation(getCampaignId(), getDonation());
        
        final ResourceBundle resourceBundle =
                facesContext.getApplication().getResourceBundle(facesContext, "msg");
        
        final String msg = resourceBundle.getString("donateMoney.thank_you");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
        
        init();
        
        logger.log(Level.INFO, "log.donateMoney.thank_you", new 
                Object[]{getDonation().getDonorName(), getDonation().getAmount()});
        
        return Pages.DONATE_MONEY;
    }

    //----------------------------------------------------------------------------------------------
}