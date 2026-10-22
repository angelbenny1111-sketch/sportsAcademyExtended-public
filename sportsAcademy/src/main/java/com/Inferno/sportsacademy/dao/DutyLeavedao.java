package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.DutyLeave;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DutyLeavedao {

    public boolean addDutyLeave(DutyLeave leave)
            throws SQLException {

        String sql = "INSERT INTO DutyLeave " +
                "(athlete_id, leave_date, reason, status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leave.getAthleteId());
            ps.setDate(2, leave.getLeaveDate());
            ps.setString(3, leave.getReason());
            ps.setString(4, leave.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public int countApprovedDutyLeaves(int athleteId)
            throws SQLException {

        LocalDate today = LocalDate.now();
        LocalDate start;
        LocalDate end;

        // Academic year: June to May
        if (today.getMonthValue() >= 6) {
            start = LocalDate.of(today.getYear(), 6, 1);
            end = LocalDate.of(today.getYear() + 1, 5, 31);
        } else {
            start = LocalDate.of(today.getYear() - 1, 6, 1);
            end = LocalDate.of(today.getYear(), 5, 31);
        }

        String sql = "SELECT COUNT(*) FROM DutyLeave " +
                "WHERE athlete_id = ? " +
                "AND status = 'APPROVED' " +
                "AND leave_date BETWEEN ? AND ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, athleteId);
            ps.setDate(2, java.sql.Date.valueOf(start));
            ps.setDate(3, java.sql.Date.valueOf(end));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
    public int getAthleteIdByUsername(String username)
        throws SQLException {

    String sql = "SELECT athlete_id FROM Athlete WHERE name = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("athlete_id");
            }
        }
    }

    return -1;
}
public List<Object[]> getPendingDutyLeaves() throws SQLException {
    List<Object[]> requests = new ArrayList<>();

    String sql = "SELECT d.duty_leave_id, a.name, d.leave_date, d.reason " +
                 "FROM DutyLeave d " +
                 "JOIN Athlete a ON d.athlete_id = a.athlete_id " +
                 "WHERE d.status = 'PENDING' " +
                 "ORDER BY d.leave_date";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            requests.add(new Object[]{
                rs.getInt("duty_leave_id"),
                rs.getString("name"),
                rs.getDate("leave_date"),
                rs.getString("reason")
            });
        }
    }

    return requests;
}

public boolean updateDutyLeaveStatus(int dutyLeaveId, String status)
        throws SQLException {

    if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
        return false;
    }

    String sql = "UPDATE DutyLeave SET status = ? " +
                 "WHERE duty_leave_id = ? AND status = 'PENDING'";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, status);
        ps.setInt(2, dutyLeaveId);

        return ps.executeUpdate() > 0;
    }
}
}
