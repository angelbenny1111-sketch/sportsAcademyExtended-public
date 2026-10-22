
package com.Inferno.sportsacademy.service;

import com.Inferno.sportsacademy.dao.AthleteSportdao;
import java.sql.SQLException;
import java.util.List;

public class AthleteSportservice {

    private final AthleteSportdao dao = new AthleteSportdao();

    public boolean enrollAthlete(int athleteId, int sportId, Integer coachId)
            throws SQLException {

        if (athleteId <= 0 || sportId <= 0) {
            throw new IllegalArgumentException(
                "Athlete ID and Sport ID must be valid."
            );
        }

        if (coachId != null && coachId <= 0) {
            throw new IllegalArgumentException("Invalid Coach ID.");
        }

        return dao.enrollAthlete(athleteId, sportId, coachId);
    }

    public List<String> getSportsForAthlete(int athleteId)
            throws SQLException {

        if (athleteId <= 0) {
            throw new IllegalArgumentException("Invalid Athlete ID.");
        }

        return dao.getSportsForAthlete(athleteId);
    }
}

