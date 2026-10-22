
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.Training;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Trainingdao {

    public boolean addTraining(Training training) {
        String sql = "INSERT INTO Training "
                   + "(sport_id, coach_id, training_date, training_time, venue) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, training.getSportId());
            ps.setInt(2, training.getCoachId());
            ps.setDate(3, java.sql.Date.valueOf(training.getTrainingDate()));
            ps.setTime(4, java.sql.Time.valueOf(training.getTrainingTime()));
            ps.setString(5, training.getVenue());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding training: " + e.getMessage());
            return false;
        }
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = new ArrayList<>();

        String sql = "SELECT * FROM Training";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Training training = new Training();

                training.setTrainingId(rs.getInt("training_id"));
                training.setSportId(rs.getInt("sport_id"));
                training.setCoachId(rs.getInt("coach_id"));
                training.setTrainingDate(
                    rs.getDate("training_date").toLocalDate()
                );
                training.setTrainingTime(
                    rs.getTime("training_time").toLocalTime()
                );
                training.setVenue(rs.getString("venue"));

                trainings.add(training);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving trainings: " + e.getMessage());
        }

        return trainings;
    }

    public boolean deleteTraining(int trainingId) {
        String sql = "DELETE FROM Training WHERE training_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainingId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting training: " + e.getMessage());
            return false;
        }
    }
}
