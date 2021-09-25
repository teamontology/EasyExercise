package com.example.myapplication.beans;

import java.sql.Time;

public class Workout {
    private Sport sport;
    private Facility facility;
    private float duration;
    private Time startTime;

    public Workout(Sport sport, Facility facility, float duration, Time startTime) {
        this.sport = sport;
        this.facility = facility;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
}