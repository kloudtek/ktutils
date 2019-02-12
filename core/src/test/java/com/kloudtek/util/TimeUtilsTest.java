package com.kloudtek.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.JANUARY;
import static org.junit.Assert.assertEquals;

public class TimeUtilsTest {
    @Test
    public void getWeekDateRangeSameMonth() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(2019, JANUARY, 9);
        assertEquals("6 to 12 Jan 2019", TimeUtils.getWeekDateRange(gregorianCalendar.getTime()));
    }

    @Test
    public void getWeekDateRangeSameYear() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(2019, JANUARY, 30);
        assertEquals("27 Jan to 2 Feb 2019", TimeUtils.getWeekDateRange(gregorianCalendar.getTime()));
    }

    @Test
    public void getWeekDateRangeDifferentYear() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(2019, Calendar.DECEMBER, 30);
        assertEquals("29 Dec 2019 to 4 Jan 2020", TimeUtils.getWeekDateRange(gregorianCalendar.getTime()));
    }
}