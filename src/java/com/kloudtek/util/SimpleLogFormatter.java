/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SimpleLogFormatter extends Formatter {
    private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    private final Date date = new Date();
    private boolean showLevel;
    private boolean showTimestamp;
    private String separator = " ";

    public boolean isShowLevel() {
        return showLevel;
    }

    public void setShowLevel(final boolean showLevel) {
        this.showLevel = showLevel;
    }

    public boolean isShowTimestamp() {
        return showTimestamp;
    }

    public void setShowTimestamp(final boolean showTimestamp) {
        this.showTimestamp = showTimestamp;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(final String separator) {
        this.separator = separator;
    }

    @Override
    public String format(final LogRecord record) {
        final StringBuilder txt = new StringBuilder();
        if (showLevel) {
            txt.append(record.getLevel().getLocalizedName()).append(separator);
        }
        if (showTimestamp) {
            synchronized (dateFormat) {
                date.setTime(record.getMillis());
                txt.append(dateFormat.format(date)).append(separator);
            }
        }
        txt.append(formatMessage(record)).append('\n');
        return txt.toString();
    }
}
