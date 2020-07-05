/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.services;

import java.util.List;

import de.dpunkt.myaktion.model.Donation;
import de.dpunkt.myaktion.services.exceptions.ObjectNotFoundException;

/**
 * @author Julian
 */
public interface DonationService {
    //----------------------------------------------------------------------------------------------

    List<Donation> getDonationList(Long campaignId);

    void addDonation(Long campaignId, Donation donation);

    void transferDonations();

    List<Donation> getDonationListPublic(Long campaignId) throws ObjectNotFoundException;

    //----------------------------------------------------------------------------------------------
}