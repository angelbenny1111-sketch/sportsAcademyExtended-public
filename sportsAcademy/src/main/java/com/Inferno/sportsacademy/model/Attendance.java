
package com.Inferno.sportsacademy.model;

import java.time.LocalDate;

public class Attendance {

    private int athleteId;
    private int trainingId;
    private LocalDate attendanceDate;
    private String status;

    public Attendance() {
    }

    public Attendance(int athleteId, int trainingId,
                      LocalDate attendanceDate, String status) {
        this.athleteId = athleteId;
        this.trainingId = trainingId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
