
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.Coach;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Coachdao {

    public boolean addCoach(Coach coach) {
        String sql = "INSERT INTO Coach (name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, coach.getName());
            ps.setString(2, coach.getEmail());
            ps.setString(3, coach.getPhone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding coach: " + e.getMessage());
            return false;
        }
    }

    public List<Coach> getAllCoaches() {
        List<Coach> coaches = new ArrayList<>();

        String sql = "SELECT coach_id, name, email, phone FROM Coach";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Coach coach = new Coach();

                coach.setCoachId(rs.getInt("coach_id"));
                coach.setName(rs.getString("name"));
                coach.setEmail(rs.getString("email"));
                coach.setPhone(rs.getString("phone"));

                coaches.add(coach);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving coaches: " + e.getMessage());
        }

        return coaches;
    }

    public boolean deleteCoach(int coachId) {
        String sql = "DELETE FROM Coach WHERE coach_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, coachId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting coach: " + e.getMessage());
            return false;
        }
    }
}

