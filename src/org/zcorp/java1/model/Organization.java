package org.zcorp.java1.model;

import java.time.LocalDate;
import java.util.*;

public class Organization {
    private final Link homePage;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name, String url) {
        this.homePage = new Link(name, url);
    }

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this(name, url);
        periods.add(new Period(startDate, endDate, title, description));
    }

    public Organization(String name, String url, Period... periods) {
        this(name, url);
        this.periods.addAll(Arrays.asList(periods));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public Period getPeriod(int index) {
        return periods.get(index);
    }

    public boolean removePeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        return periods.remove(new Period(startDate, endDate, title, description));
    }

    public boolean removePeriod(Period period) {
        return periods.remove(period);
    }

    public Period removePeriod(int index) {
        return periods.remove(index);
    }

    public Period setPeriod(int index, LocalDate startDate, LocalDate endDate, String title, String description) {
        return periods.set(index, new Period(startDate, endDate, title, description));
    }

    public Period setPeriod(int index, Period period) {
        return periods.set(index, period);
    }

    public boolean addPeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        return periods.add(new Period(startDate, endDate, title, description));
    }

    public boolean addPeriod(Period period) {
        return periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", periods=" + periods +
                '}';
    }
}