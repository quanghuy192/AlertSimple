package com.example.huydq17.alertsimple;

/**
 * Created by HuyDQ17 on 10/2/2015.
 */
public class Utilities {

    private long hour;
    private long minutes;
    private long seconds;
    private long millionSeconds;

    public long getMilionSeconds() {
        return millionSeconds;
    }

    // Receive millionSeconds
    // Return hours, minutes, second
    public void setMillionSeconds(long millionSeconds) {
        this.millionSeconds = millionSeconds;
        hour = (int) millionSeconds / (60 * 60 * 1000);
        minutes = millionSeconds - hour * 60 * 60 * 1000;
        minutes = (int) minutes / (60 * 1000);
        seconds = millionSeconds - hour * 60 * 60 * 1000 - minutes * 60 * 1000;
        seconds = (int) seconds / (1000);
    }

    public String getHour() {
        return String.valueOf(hour);
    }

    public String getMinutes() {
        return String.valueOf(minutes);
    }

    public String getSeconds() {
        return String.valueOf(seconds);
    }
}
