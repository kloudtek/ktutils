/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by yannick on 23/10/2014.
 */
public class TimeUtils {
    private static final TimeFormatter dateFormatter;

    static {
        // TODO improve performance by using a pool and/or threadlocal
        dateFormatter = new TimeFormatterJDKImpl();
    }

    /**
     * Parse a UTC date in ISO format YYYY-MM-DD ( ie: 2014-12-30 )
     *
     * @param date Date string
     * @return date
     * @throws java.text.ParseException if the string was invalid
     */
    public static Date parseISOUTCDate(String date) throws ParseException {
        return dateFormatter.parseISOUTCDate(date);
    }

    /**
     * Parse a UTC date in ISO format YYYY-MM-DDTHH:MM:SS ( ie: 2014-12-30T14:50:40 )
     *
     * @param dateTime Date string
     * @return date
     * @throws java.text.ParseException if the string was invalid
     */
    public static Date parseISOUTCDateTime(String dateTime) throws ParseException {
        return dateFormatter.parseISOUTCDateTime(dateTime);
    }

    /**
     * Format a date as it's UTC ISO representation YYYY-MM-DD ( ie: 2014-12-30 )
     *
     * @param date Date
     * @return formatted date
     */
    public static String formatISOUTCDate(Date date) {
        return dateFormatter.formatISOUTCDate(date);
    }

    /**
     * Format a date as it's UTC ISO representation YYYY-MM-DDTHH:MM:SS ( ie: 2014-12-30T14:50:40 )
     *
     * @param dateTime Date
     * @return formatted date
     */
    public static String formatISOUTCDateTime(Date dateTime) {
        return dateFormatter.formatISOUTCDateTime(dateTime);
    }
}
