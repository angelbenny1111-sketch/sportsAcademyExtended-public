
package com.Inferno.sportsacademy.service;

import com.Inferno.sportsacademy.dao.Coachdao;
import com.Inferno.sportsacademy.model.Coach;
import com.Inferno.sportsacademy.util.Validationutil;

import java.util.List;

public class Coachservice {

    private Coachdao coachDAO = new Coachdao();

    public boolean addCoach(String name, String email, String phone) {

        if (!Validationutil.isNotEmpty(name)) {
            System.out.println("Coach name cannot be empty.");
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

        Coach coach = new Coach();
        coach.setName(name.trim());
        coach.setEmail(email.trim());
        coach.setPhone(phone.trim());

        return coachDAO.addCoach(coach);
    }

    public List<Coach> getAllCoaches() {
        return coachDAO.getAllCoaches();
    }

    public boolean deleteCoach(int coachId) {

        if (coachId <= 0) {
            System.out.println("Invalid coach ID.");
            return false;
        }

        return coachDAO.deleteCoach(coachId);
    }
}
