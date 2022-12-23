package it.aendrix.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SimplyDate {

    private int day, month, year;

    public SimplyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public SimplyDate(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        calendar.setTime(date);

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
    }

    public SimplyDate(long millis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        calendar.setTimeInMillis(millis);

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
    }

    public SimplyDate(String date) {
        String[] str = date.split("/");

        this.day = Integer.parseInt(str[0]);
        this.month = Integer.parseInt(str[1]);
        this.year = Integer.parseInt(str[2]);
    }

    public SimplyDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean equals(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        calendar.setTime(date);

        return this.day == calendar.get(Calendar.DAY_OF_MONTH) &&
                this.month == calendar.get(Calendar.MONTH) &&
                this.year == calendar.get(Calendar.YEAR);
    }

    public boolean equals(long millis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        calendar.setTimeInMillis(millis);

        return this.day == calendar.get(Calendar.DAY_OF_MONTH) &&
                this.month == calendar.get(Calendar.MONTH) &&
                this.year == calendar.get(Calendar.YEAR);
    }

    public boolean equals(SimplyDate date) {
        return this.day == date.getDay() && this.month == date.getMonth() && this.year == date.getYear();
    }

    @Override
    public String toString() {
        return this.day+"/"+this.month+"/"+this.year;
    }

    public static SimplyDate getInstance() {
        return new SimplyDate();
    }

    public static SimplyDate today() {
        return new SimplyDate();
    }
}
