/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Interface to time formatter implementations
 */
public class TimeFormatterJDKImpl implements TimeFormatter {
    private static final SimpleDateFormat isoDateFormat;
    private static final SimpleDateFormat isoDateTimeFormat;

    static {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoDateFormat.setTimeZone(tz);
        isoDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoDateTimeFormat.setTimeZone(tz);
    }

    @Override
    public Date parseISOUTCDate(String date) throws ParseException {
        synchronized (isoDateFormat) {
            return isoDateFormat.parse(date);
        }
    }

    @Override
    public Date parseISOUTCDateTime(String dateTime) throws ParseException {
        synchronized (isoDateTimeFormat) {
            return isoDateTimeFormat.parse(dateTime);
        }
    }

    @Override
    public String formatISOUTCDate(Date date) {
        synchronized (isoDateFormat) {
            return isoDateFormat.format(date);
        }
    }

    @Override
    public String formatISOUTCDateTime(Date dateTime) {
        synchronized (isoDateTimeFormat) {
            return isoDateTimeFormat.format(dateTime);
        }
    }
}
