
package com.Inferno.sportsacademy.service;

import com.Inferno.sportsacademy.dao.Athletedao;
import com.Inferno.sportsacademy.model.Athlete;
import com.Inferno.sportsacademy.util.Validationutil;

import java.util.List;

public class Athleteservice {

    private Athletedao athleteDAO = new Athletedao();

    public boolean addAthlete(String name, String email,
                              String phone, String sportName) {

        if (!Validationutil.isNotEmpty(name)) {
            System.out.println("Athlete name cannot be empty.");
            return false;
        }

        if (!Validationutil.isValidEmail(email)) {
            System.out.println("Invalid email address.");
            return false;
        }

        if (!Validationutil.isValidPhone(phone)) {
            System.out.println("Phone number must contain 10 digits.");
            return false;
        }

        if (!Validationutil.isNotEmpty(sportName)) {
            System.out.println("Sport name cannot be empty.");
            return false;
        }

        Athlete athlete = new Athlete();
        athlete.setName(name.trim());
        athlete.setEmail(email.trim());
        athlete.setPhone(phone.trim());
        athlete.setSportName(sportName.trim());

        return athleteDAO.addAthlete(athlete);
    }

    public List<Athlete> getAllAthletes() {
        return athleteDAO.getAllAthletes();
    }

    public boolean deleteAthlete(int athleteId) {
        if (athleteId <= 0) {
            System.out.println("Invalid athlete ID.");
            return false;
        }

        return athleteDAO.deleteAthlete(athleteId);
    }
    public Athlete getAthleteByUsername(String username) {
    if (username == null || username.trim().isEmpty()) {
        return null;
    }
    return athleteDAO.getAthleteByUsername(username.trim());
}
}

