/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.util.UUID;

/**
 * UUID factory class that uses {@link UUID#randomUUID()}
 */
public class UUIDFactoryRandomImpl extends UUIDFactory {
    @Override
    public UUID create() {
        return UUID.randomUUID();
    }
}
