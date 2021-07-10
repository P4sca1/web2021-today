package de.thkoeln.web.today.models;

import java.time.LocalDate;

public class StatisticEntry {

    private LocalDate date;
    private int sunshineDuration;

    public StatisticEntry(LocalDate date, int sunshineDuration) {
        this.date = date;
        this.sunshineDuration = sunshineDuration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSunshineDuration() {
        return sunshineDuration;
    }

    public void setSunshineDuration(int sunshineDuration) {
        this.sunshineDuration = sunshineDuration;
    }

}
