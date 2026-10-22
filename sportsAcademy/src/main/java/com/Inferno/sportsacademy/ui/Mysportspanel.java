
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.User;
import com.Inferno.sportsacademy.model.Athlete;
import com.Inferno.sportsacademy.service.Athleteservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Mysportspanel extends JPanel {

    private final User loggedInUser;
    private final JTable sportsTable;
    private final DefaultTableModel tableModel;

    public Mysportspanel(User user) {
        this.loggedInUser = user;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("My Sports");
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"Sport", "Coach", "Enrollment Date", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sportsTable = new JTable(tableModel);
        add(new JScrollPane(sportsTable), BorderLayout.CENTER);

        loadSports();
    }

    private void loadSports() {
        tableModel.setRowCount(0);

        Athlete athlete = new Athleteservice()
                .getAthleteByUsername(loggedInUser.getUsername());

        if (athlete == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No athlete profile found for username: "
                            + loggedInUser.getUsername(),
                    "Profile Not Found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql =
                "SELECT s.sport_name, c.name AS coach_name, " +
                "       aspt.enrollment_date, aspt.status " +
                "FROM Athlete_Sport aspt " +
                "JOIN Sport s ON aspt.sport_id = s.sport_id " +
                "LEFT JOIN Coach c ON aspt.coach_id = c.coach_id " +
                "WHERE aspt.athlete_id = ? " +
                "ORDER BY s.sport_name";

        try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sports_academy",
                    "root",
                    System.getenv("DB_PASSWORD"));
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, athlete.getAthleteId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getString("sport_name"),
                            rs.getString("coach_name") == null
                                    ? "Not assigned"
                                    : rs.getString("coach_name"),
                            rs.getDate("enrollment_date"),
                            rs.getString("status")
                    });
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load your sports: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
