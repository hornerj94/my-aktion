/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.util;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;

import de.dpunkt.myaktion.util.Log.FachLog;
import de.dpunkt.myaktion.util.Log.TecLog;

/**
 * @author Julian
 */
@Dependent
public class Resources {
    @Produces @FachLog
    public Logger produceFachLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(
                "FachLog: " + injectionPoint.getMember().getDeclaringClass().getName(), "messages");
    }

    @Produces @TecLog
    public Logger produceTecLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(
                "TecLog: " + injectionPoint.getMember().getDeclaringClass().getName(), "messages");
    }

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}