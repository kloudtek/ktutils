/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

/**
 * Thread-related helper functions
 */
public class ThreadUtils {
    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new UnexpectedException(e);
        }
    }
}
