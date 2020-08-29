/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import java.text.ParseException;
import java.util.Date;

/**
 * Interface to time formatter implementations
 */
public interface TimeFormatter {
    /**
     * Parse a UTC date in ISO format YYYY-MM-DD ( ie: 2014-12-30 )
     *
     * @param date Date string
     * @return date
     */
    Date parseISOUTCDate(String date) throws ParseException;

    /**
     * Parse a UTC date in ISO format YYYY-MM-DDTHH:MM:SS ( ie: 2014-12-30T14:50:40 )
     *
     * @param dateTime Date string
     * @return date
     */
    Date parseISOUTCDateTime(String dateTime) throws ParseException;

    /**
     * Format a date as it's UTC ISO representation YYYY-MM-DD ( ie: 2014-12-30 )
     *
     * @param date Date
     * @return formatted date
     */
    String formatISOUTCDate(Date date);

    /**
     * Format a date as it's UTC ISO representation YYYY-MM-DDTHH:MM:SS ( ie: 2014-12-30T14:50:40 )
     *
     * @param dateTime Date
     * @return formatted date
     */
    String formatISOUTCDateTime(Date dateTime);
}
