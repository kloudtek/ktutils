/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.UUID;

/**
 * UUID factory class.
 */
public abstract class UUIDFactory {
    private static UUIDFactory global;

    public abstract UUID create();

    /**
     * Return an UUID using a global UUIDFactory loaded through {@link ServiceLoader}
     * @return UUID
     */
    public static UUID generate() {
        if( global == null ) {
            Iterator<UUIDFactory> serviceLoader = ServiceLoader.load(UUIDFactory.class).iterator();
            if( serviceLoader.hasNext() ) {
                global = serviceLoader.next();
            } else {
                global = new UUIDFactoryRandomImpl();
            }
        }
        return UUID.randomUUID();
    }
}
