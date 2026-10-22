
package com.Inferno.sportsacademy.model;

public class Athlete {

    private int athleteId;
    private String name;
    private String email;
    private String phone;
    private String sportName;

    public Athlete() {
    }

    public Athlete(int athleteId, String name, String email,
                   String phone, String sportName) {
        this.athleteId = athleteId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sportName = sportName;
    }

    public Athlete(String name, String email, String phone,
                   String sportName) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sportName = sportName;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    @Override
    public String toString() {
        return name + " - " + sportName;
    }
}
