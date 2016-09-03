/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logging related utilities
 */
public class LoggingUtils {
    public static void setupSimpleLogging(Level lvl, boolean showLevel, boolean showTimestamp) {
        Logger logger = Logger.getLogger("");
        for (final Handler handler : logger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                final SimpleLogFormatter logFormatter = new SimpleLogFormatter();
                logFormatter.setShowLevel(showLevel);
                logFormatter.setShowTimestamp(showTimestamp);
                logFormatter.setSeparator(": ");
                handler.setFormatter(logFormatter);
            }
        }
        setLoggingLevel(lvl);
    }

    public static void setLoggingLevel(Level lvl) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(lvl);
        for (final Handler handler : logger.getHandlers()) {
            handler.setLevel(lvl);
        }
    }

    public static void main(String[] args) {
        setLoggingLevel(Level.ALL);
        Logger logger = Logger.getLogger(LoggingUtils.class.getName());
        logger.fine("log fine");
        logger.info("log info");
    }
}
