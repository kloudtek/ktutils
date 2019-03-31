/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

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
     * Get the week and year, based on the last day of the week
     *
     * @param date Date
     * @return Week and Year
     */
    public static WeekAndYear getWeekAndYear(LocalDate date) {
        LocalDate lastDayOfWeek = getLastDayOfWeek(date);
        return new WeekAndYear(lastDayOfWeek.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), lastDayOfWeek.get(IsoFields.WEEK_BASED_YEAR));
    }

    public static DayOfWeek getFirstDayOfWeek() {
        return WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    }

    public static DayOfWeek getLastDayOfWeek() {
        return DayOfWeek.of(((getFirstDayOfWeek().getValue() + 5) % DayOfWeek.values().length) + 1);
    }

    public static LocalDate getLastDayOfWeek(WeekAndYear weekAndYear) {
        return getLastDayOfWeek(weekAndYear.getWeek(), weekAndYear.getYear());
    }

    public static LocalDate getLastDayOfWeek(int week, int year) {
        return LocalDate.of(year, 2, 1).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week).with(ChronoField.DAY_OF_WEEK, getLastDayOfWeek().getValue());
    }

    public static LocalDate getFirstDayOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(getFirstDayOfWeek()));
    }

    public static LocalDate getLastDayOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(getLastDayOfWeek()));
    }

    public static String toString(Month month) {
        return month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }

    /**
     * Generates a string that indicates the start and end date of a week in a human friendly format (without repeating year and/or month when applicable)
     *
     * @param date Date in the week (week is considered to start sunday and end saturday
     * @return Date range string
     */
    public static String getWeekDateRange(LocalDate date) {
        LocalDate firstDayOfWeek = getFirstDayOfWeek(date);
        LocalDate lastDayOfWeek = getLastDayOfWeek(date);
        if (firstDayOfWeek.getMonth() == lastDayOfWeek.getMonth()) {
            return firstDayOfWeek.getDayOfMonth() + " to " + lastDayOfWeek.getDayOfMonth() + " " +
                    toString(firstDayOfWeek.getMonth()) + " " + firstDayOfWeek.getYear();
        } else if (firstDayOfWeek.getYear() == lastDayOfWeek.getYear()) {
            return firstDayOfWeek.getDayOfMonth() + " " + toString(firstDayOfWeek.getMonth()) + " to " +
                    lastDayOfWeek.getDayOfMonth() + " " + toString(lastDayOfWeek.getMonth()) + " " + lastDayOfWeek.getYear();
        } else {
            return firstDayOfWeek.getDayOfMonth() + " " + toString(firstDayOfWeek.getMonth()) + " " +
                    firstDayOfWeek.getYear() + " to " + lastDayOfWeek.getDayOfMonth() + " " +
                    toString(lastDayOfWeek.getMonth()) + " " + lastDayOfWeek.getYear();
        }
    }
}
