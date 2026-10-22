package com.Inferno.sportsacademy.model;

public class Coach {

    private int coachId;
    private String name;
    private String email;
    private String phone;

    public Coach() {
    }

    public Coach(int coachId, String name, String email, String phone) {
        this.coachId = coachId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Coach(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
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

    @Override
    public String toString() {
        return "Coach{" +
                "coachId=" + coachId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
