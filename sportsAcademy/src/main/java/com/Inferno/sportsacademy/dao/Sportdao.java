
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.Sport;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sportdao {

    public boolean addSport(Sport sport) {
        String sql = "INSERT INTO Sport (sport_name, description) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sport.getSportName());
            ps.setString(2, sport.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding sport: " + e.getMessage());
            return false;
        }
    }

    public List<Sport> getAllSports() {
        List<Sport> sports = new ArrayList<>();

        String sql = "SELECT sport_id, sport_name, description FROM Sport";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sport sport = new Sport();

                sport.setSportId(rs.getInt("sport_id"));
                sport.setSportName(rs.getString("sport_name"));
                sport.setDescription(rs.getString("description"));

                sports.add(sport);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving sports: " + e.getMessage());
        }

        return sports;
    }

    public boolean deleteSport(int sportId) {
        String sql = "DELETE FROM Sport WHERE sport_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sportId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting sport: " + e.getMessage());
            return false;
        }
    }
}
