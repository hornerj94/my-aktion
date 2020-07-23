/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * @author Julian
 */
@ViewScoped
@Named
public class ListDonationsController implements Serializable {
    //----------------------------------------------------------------------------------------------

    private static final long serialVersionUID = 437878972432L;

    //==============================================================================================

    public String doOk() {
        return Pages.LIST_CAMPAIGNS;
    }

    //----------------------------------------------------------------------------------------------
}