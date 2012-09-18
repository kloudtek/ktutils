/*
 * Copyright (c) Kloudtek Ltd 2012.
 */

package com.kloudtek.util;

import java.util.logging.LogManager;

/**
 * This log manager extends the normal log manager so that reset() is disabled, in order for the LogManager
 * to not automatically reset itself during JVM shutdown. This will allow other threads to use logging while performing shutdown operations, at which time
 * the {@link #clean()} method should be called.
 * In order to active this log manager, call <code>System.setProperty("java.util.logging.manager","com.kloudtek.util.NoCleanLogManager")</code>
 * before instantiating any logging classes.
 */
public class NoCleanLogManager extends LogManager {
    public NoCleanLogManager() {
    }

    @Override
    public void reset() throws SecurityException {
        // Do nothing
    }

    public void clean() {
        super.reset();
    }
}
