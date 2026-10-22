
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AthleteSportdao {

    public boolean enrollAthlete(int athleteId, int sportId, Integer coachId)
            throws SQLException {

        String sql = """
            INSERT INTO Athlete_Sport
                (athlete_id, sport_id, coach_id, enrollment_date, status)
            VALUES (?, ?, ?, CURDATE(), 'ACTIVE')
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, athleteId);
            ps.setInt(2, sportId);

            if (coachId == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, coachId);
            }

            return ps.executeUpdate() == 1;
        }
    }

    public List<String> getSportsForAthlete(int athleteId)
            throws SQLException {

        List<String> sports = new ArrayList<>();

        String sql = """
            SELECT s.sport_name
            FROM Athlete_Sport a
            JOIN Sport s ON a.sport_id = s.sport_id
            WHERE a.athlete_id = ? AND a.status = 'ACTIVE'
            ORDER BY s.sport_name
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, athleteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sports.add(rs.getString("sport_name"));
                }
            }
        }

        return sports;
    }
}

