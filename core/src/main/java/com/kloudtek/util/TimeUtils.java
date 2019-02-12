/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    /**
     * Generates a string that indicates the start and end date of a week in a human friendly format (without repeating year and/or month when applicable)
     *
     * @param date Date in the week (week is considered to start sunday and end saturday
     * @return Date range string
     */
    public static String getWeekDateRange(Date date) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i);
        int startDay = c.get(Calendar.DAY_OF_MONTH);
        int startMonth = c.get(Calendar.MONTH);
        int startYear = c.get(Calendar.YEAR);
        c.add(Calendar.DATE, 6);
        int endDay = c.get(Calendar.DAY_OF_MONTH);
        int endMonth = c.get(Calendar.MONTH);
        int endYear = c.get(Calendar.YEAR);
        String[] shortMonths = dateFormatSymbols.getShortMonths();
        String startMonthName = shortMonths[startMonth];
        if (startMonth == endMonth) {
            return startDay + " to " + endDay + " " + startMonthName + " " + startYear;
        } else if (startYear == endYear) {
            return startDay + " " + startMonthName + " to " + endDay + " " + shortMonths[endMonth] + " " + startYear;
        } else {
            return startDay + " " + startMonthName + " " + startYear + " to " + endDay + " " + shortMonths[endMonth] + " " + endYear;
        }
    }
}
