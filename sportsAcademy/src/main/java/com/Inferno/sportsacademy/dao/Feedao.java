
package com.Inferno.sportsacademy.dao;

import com.Inferno.sportsacademy.model.Fee;
import com.Inferno.sportsacademy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feedao {

    public boolean addFee(Fee fee) {
        String sql = "INSERT INTO Fee "
                   + "(athlete_id, amount, payment_date, status) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, fee.getAthleteId());
            ps.setBigDecimal(2, fee.getAmount());

            if (fee.getPaymentDate() == null) {
                ps.setNull(3, java.sql.Types.DATE);
            } else {
                ps.setDate(3, java.sql.Date.valueOf(fee.getPaymentDate()));
            }

            ps.setString(4, fee.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding fee: " + e.getMessage());
            return false;
        }
    }

    public List<Fee> getAllFees() {
        List<Fee> fees = new ArrayList<>();

        String sql = "SELECT fee_id, athlete_id, amount, payment_date, status "
                   + "FROM Fee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Fee fee = new Fee();

                fee.setFeeId(rs.getInt("fee_id"));
                fee.setAthleteId(rs.getInt("athlete_id"));
                fee.setAmount(rs.getBigDecimal("amount"));

                java.sql.Date paymentDate = rs.getDate("payment_date");
                if (paymentDate != null) {
                    fee.setPaymentDate(paymentDate.toLocalDate());
                }

                fee.setStatus(rs.getString("status"));
                fees.add(fee);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving fees: " + e.getMessage());
        }

        return fees;
    }
}

