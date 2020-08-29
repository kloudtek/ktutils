package com.aeontronix.common;

import org.junit.Test;

import java.time.LocalDate;

import static com.aeontronix.common.TimeUtils.getWeekAndYear;
import static org.junit.Assert.assertEquals;

public class TimeUtilsTest {
    @Test
    public void getWeekDateRangeSameMonth() {
        assertEquals("6 to 12 Jan 2019", TimeUtils.getWeekDateRange(LocalDate.of(2019,1,9)));
    }

    @Test
    public void getWeekDateRangeSameYear() {
        assertEquals("27 Jan to 2 Feb 2019", TimeUtils.getWeekDateRange(LocalDate.of(2019,1,30)));
    }

    @Test
    public void getWeekDateRangeDifferentYear() {
        assertEquals("29 Dec 2019 to 4 Jan 2020", TimeUtils.getWeekDateRange(LocalDate.of(2019,12,30)));
    }

    @Test
    public void getWeekAndYears() {
        testWeekYear(new WeekAndYear(52, 2018), LocalDate.of(2018, 12, 25),LocalDate.of(2018, 12, 29));
        testWeekYear(new WeekAndYear(1,2019), LocalDate.of(2018,12,30), LocalDate.of(2019, 1, 5));
        testWeekYear(new WeekAndYear(1,2019), LocalDate.of(2019,1,1), LocalDate.of(2019, 1, 5));
        testWeekYear(new WeekAndYear(2,2019), LocalDate.of(2019,1,6), LocalDate.of(2019, 1, 12));
    }

    private void testWeekYear(WeekAndYear expected, LocalDate date, LocalDate expectedLastDay) {
        WeekAndYear weekAndYear = getWeekAndYear(date);
        assertEquals(expected, weekAndYear);
        assertEquals(expectedLastDay, TimeUtils.getLastDayOfWeek(weekAndYear));
    }
}
