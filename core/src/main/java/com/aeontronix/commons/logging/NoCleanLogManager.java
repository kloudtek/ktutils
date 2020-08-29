/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons.logging;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 * This log manager extends the normal log manager so that reset() is disabled, in order for the LogManager
 * to not automatically reset itself during JVM shutdown. This will allow other threads to use logging while performing shutdown operations, at which time
 * the {@link #close()} method should be called.
 * In order to active this log manager, call <code>System.setProperty("java.util.logging.manager","NoCleanLogManager")</code>
 * before instantiating any logging classes.
 */
public class NoCleanLogManager extends LogManager implements Closeable {
    public NoCleanLogManager() {
    }

    @Override
    public void reset() throws SecurityException {
        // Do nothing
    }

    @Override
    public void close() throws IOException {
        super.reset();
    }
}
