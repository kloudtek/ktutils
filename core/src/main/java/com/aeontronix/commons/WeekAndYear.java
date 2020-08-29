package com.aeontronix.commons;

import java.io.Serializable;
import java.util.Objects;

public class WeekAndYear implements Serializable {
    private int week;
    private int year;

    public WeekAndYear() {
    }

    public WeekAndYear(int week, int year) {
        this.week = week;
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekAndYear)) return false;
        WeekAndYear that = (WeekAndYear) o;
        return week == that.week &&
                year == that.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(week, year);
    }

    @Override
    public String toString() {
        return year + "W"+week;
    }
}
