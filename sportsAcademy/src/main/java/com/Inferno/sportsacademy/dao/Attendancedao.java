
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.Attendance;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Attendancedao {

    public boolean addAttendance(Attendance attendance) {
        String sql = "INSERT INTO Attendance "
                   + "(athlete_id, training_id, attendance_date, status) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, attendance.getAthleteId());
            ps.setInt(2, attendance.getTrainingId());
            ps.setDate(3, java.sql.Date.valueOf(
                    attendance.getAttendanceDate()));
            ps.setString(4, attendance.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding attendance: " + e.getMessage());
            return false;
        }
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> records = new ArrayList<>();

        String sql = "SELECT athlete_id, training_id, attendance_date, status "
                   + "FROM Attendance";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Attendance attendance = new Attendance();

                attendance.setAthleteId(rs.getInt("athlete_id"));
                attendance.setTrainingId(rs.getInt("training_id"));
                attendance.setAttendanceDate(
                    rs.getDate("attendance_date").toLocalDate()
                );
                attendance.setStatus(rs.getString("status"));

                records.add(attendance);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving attendance: "
                    + e.getMessage());
        }

        return records;
    }
}

