
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.Athlete;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Athletedao {

    public boolean addAthlete(Athlete athlete) {
        String sql = "INSERT INTO Athlete (name, email, phone, sport_name) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, athlete.getName());
            ps.setString(2, athlete.getEmail());
            ps.setString(3, athlete.getPhone());
            ps.setString(4, athlete.getSportName());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding athlete: " + e.getMessage());
            return false;
        }
    }

    public List<Athlete> getAllAthletes() {
        List<Athlete> athletes = new ArrayList<>();

        String sql = "SELECT athlete_id, name, email, phone, sport_name "
                   + "FROM Athlete";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Athlete athlete = new Athlete();

                athlete.setAthleteId(rs.getInt("athlete_id"));
                athlete.setName(rs.getString("name"));
                athlete.setEmail(rs.getString("email"));
                athlete.setPhone(rs.getString("phone"));
                athlete.setSportName(rs.getString("sport_name"));

                athletes.add(athlete);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving athletes: " + e.getMessage());
        }

        return athletes;
    }

    public boolean deleteAthlete(int athleteId) {
        String sql = "DELETE FROM Athlete WHERE athlete_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, athleteId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting athlete: " + e.getMessage());
            return false;
        }
    }
    public Athlete getAthleteByUsername(String username) {
    String sql =
        "SELECT athlete_id, name, email, phone, sport_name " +
        "FROM Athlete WHERE LOWER(TRIM(name)) = LOWER(TRIM(?))";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Athlete athlete = new Athlete();
                athlete.setAthleteId(rs.getInt("athlete_id"));
                athlete.setName(rs.getString("name"));
                athlete.setEmail(rs.getString("email"));
                athlete.setPhone(rs.getString("phone"));
                athlete.setSportName(rs.getString("sport_name"));
                return athlete;
            }
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving athlete profile: " + e.getMessage());
    }

    return null;
}
}

