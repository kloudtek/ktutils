/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.util.UUID;

/**
 * UUID factory class.
 */
public class UUIDFactory {
    /**
     * Return an UUID (currently this just uses {@link java.util.UUID#randomUUID()} but will be replaced in time with
     * something better :)
     *
     * @return UUID
     */
    public static UUID generate() {
        return UUID.randomUUID();
    }
}
