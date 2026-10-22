
package com.Inferno.sportsacademy.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Training {

    private int trainingId;
    private int sportId;
    private int coachId;
    private LocalDate trainingDate;
    private LocalTime trainingTime;
    private String venue;

    public Training() {
    }

    public Training(int trainingId, int sportId, int coachId,
                           LocalDate trainingDate, LocalTime trainingTime,
                           String venue) {
        this.trainingId = trainingId;
        this.sportId = sportId;
        this.coachId = coachId;
        this.trainingDate = trainingDate;
        this.trainingTime = trainingTime;
        this.venue = venue;
    }

    public Training(int sportId, int coachId,
                           LocalDate trainingDate, LocalTime trainingTime,
                           String venue) {
        this.sportId = sportId;
        this.coachId = coachId;
        this.trainingDate = trainingDate;
        this.trainingTime = trainingTime;
        this.venue = venue;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public LocalTime getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(LocalTime trainingTime) {
        this.trainingTime = trainingTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
