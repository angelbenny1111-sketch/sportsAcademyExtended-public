
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.User;
import com.Inferno.sportsacademy.service.Athleteservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Mytrainingpanel extends JPanel {

    public Mytrainingpanel(User user) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Training Schedule");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {
            "Sport", "Training Date", "Time", "Venue", "Coach"
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

        loadTraining(user, model);
    }

    private void loadTraining(User user, DefaultTableModel model) {
        try {
            var athlete = new Athleteservice()
                .getAthleteByUsername(user.getUsername());

            if (athlete == null) {
                JOptionPane.showMessageDialog(this, "Athlete not found.");
                return;
            }

            String sql =
                "SELECT DISTINCT s.sport_name, t.training_date, " +
                "t.training_time, t.venue, c.name AS coach_name " +
                "FROM Athlete_Sport ats " +
                "JOIN Training t ON ats.sport_id = t.sport_id " +
                "JOIN Sport s ON t.sport_id = s.sport_id " +
                "JOIN Coach c ON t.coach_id = c.coach_id " +
                "WHERE ats.athlete_id = ? " +
                "AND ats.status = 'ACTIVE' " +
                "ORDER BY t.training_date, t.training_time";

            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sports_academy",
                    "root", System.getenv("DB_PASSWORD"));
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, athlete.getAthleteId());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("sport_name"),
                            rs.getDate("training_date"),
                            rs.getTime("training_time"),
                            rs.getString("venue"),
                            rs.getString("coach_name")
                        });
                    }
                }
            }

            if (model.getRowCount() == 0) {
                add(new JLabel("no training sessions scheduled yet."),
                    BorderLayout.SOUTH);
            }

        } catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(
        this,
        e.toString(),
        "Training Panel Error",
        JOptionPane.ERROR_MESSAGE
    );
}
    }
}
