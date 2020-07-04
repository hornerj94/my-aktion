/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.scheduler;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import de.dpunkt.myaktion.services.DonationService;

/**
 * @author Julian
 */
@Singleton
public class SchedulerBean {
    @Inject
    private DonationService donationService;

    @Schedule(hour = "*", minute = "*/5", persistent = false)
    public void doTransferDonations() {
        donationService.transferDonations();
    }
}