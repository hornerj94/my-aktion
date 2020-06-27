/*
 * Copyright 2019 (C) by Julian Horner.
 * All Rights Reserved.
 */

package de.dpunkt.myaktion.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * @author Julian
 */
public class Log {
    @Qualifier
    @Target({ FIELD, PARAMETER, METHOD })
    @Retention(RUNTIME)
    public @interface FachLog { }

    @Qualifier
    @Target({ FIELD, PARAMETER, METHOD })
    @Retention(RUNTIME)
    public @interface TecLog { }
}