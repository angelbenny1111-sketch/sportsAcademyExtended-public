
package com.Inferno.sportsacademy.model;

public class Sport {

    private int sportId;
    private String sportName;
    private String description;

    public Sport() {
    }

    public Sport(int sportId, String sportName, String description) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.description = description;
    }

    public Sport(String sportName, String description) {
        this.sportName = sportName;
        this.description = description;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return sportName;
    }
}

