
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.User;
import com.Inferno.sportsacademy.service.Athleteservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Myfeespanel extends JPanel {

    public Myfeespanel(User user) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Fees");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {
            "Fee ID", "Amount", "Due Date", "Status"
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

        loadFees(user, model);
    }

    private void loadFees(User user, DefaultTableModel model) {
        try {
            var athlete = new Athleteservice()
                .getAthleteByUsername(user.getUsername());

            if (athlete == null) {
                JOptionPane.showMessageDialog(this, "Athlete not found.");
                return;
            }

            String sql =
        "SELECT fee_id, amount, payment_date, status " +
        "FROM Fee " +
        "WHERE athlete_id = ? " +
        "ORDER BY payment_date DESC";

            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sports_academy",
                    "root", System.getenv("DB_PASSWORD"));
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, athlete.getAthleteId());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("fee_id"),
                            rs.getBigDecimal("amount"),
                            rs.getDate("payment_date"),
                            rs.getString("status")
                        });
                    }
                }
            }

            if (model.getRowCount() == 0) {
                add(new JLabel("no fee records yet."),
                    BorderLayout.SOUTH);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "could not load fees: " + e.getMessage(),
                "database error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

