package com.Inferno.sportsacademy.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Inferno.sportsacademy.util.DBConnection;

public class DutyLeave {

    private int dutyLeaveId;
    private int athleteId;
    private Date leaveDate;
    private String reason;
    private String status;

    public DutyLeave(int athleteId, Date leaveDate,
                     String reason, String status) {
        this.athleteId = athleteId;
        this.leaveDate = leaveDate;
        this.reason = reason;
        this.status = status;
    }

    public int getDutyLeaveId() {
        return dutyLeaveId;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
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
}
