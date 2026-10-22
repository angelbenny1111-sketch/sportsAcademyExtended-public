
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.User;
import com.Inferno.sportsacademy.service.Athleteservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Myattendancepanel extends JPanel {

    private final User loggedInUser;

    public Myattendancepanel(User user) {
        this.loggedInUser = user;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Attendance");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {
            "Sport", "Attendance Date", "Status",
            "Training Date", "Training Time", "Venue"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadAttendance(model);
    }

    private void loadAttendance(DefaultTableModel model) {
        try {
            Athleteservice athleteService = new Athleteservice();
            var athlete = athleteService.getAthleteByUsername(
                loggedInUser.getUsername()
            );

            if (athlete == null) {
                JOptionPane.showMessageDialog(
                    this, "Athlete profile not found."
                );
                return;
            }

            String sql =
                "SELECT s.sport_name, a.attendance_date, a.status, " +
                "t.training_date, t.training_time, t.venue " +
                "FROM Attendance a " +
                "JOIN Training t ON a.training_id = t.training_id " +
                "JOIN Sport s ON t.sport_id = s.sport_id " +
                "WHERE a.athlete_id = ? " +
                "ORDER BY a.attendance_date DESC";

            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sports_academy",
                    "root",
                    System.getenv("DB_PASSWORD"));
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, athlete.getAthleteId());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("sport_name"),
                            rs.getDate("attendance_date"),
                            rs.getString("status"),
                            rs.getDate("training_date"),
                            rs.getTime("training_time"),
                            rs.getString("venue")
                        });
                    }
                }
            }

            if (model.getRowCount() == 0) {
                JLabel emptyMessage = new JLabel(
                    "no attendance records yet.",
                    SwingConstants.CENTER
                );
                emptyMessage.setFont(new Font("Arial", Font.PLAIN, 15));
                add(emptyMessage, BorderLayout.SOUTH);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load attendance: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

