package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.dao.DutyLeavedao;
import com.Inferno.sportsacademy.model.DutyLeave;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class DutyLeavePanel extends JPanel {

    private final JTextField usernameField = new JTextField(20);
    private final JTextField dateField = new JTextField(20);
    private final JTextField reasonField = new JTextField(20);

    private final JComboBox<String> statusBox =
            new JComboBox<>(new String[]{
                "PENDING", "APPROVED", "REJECTED"
            });

    public DutyLeavePanel() {
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Athlete username:"));
        add(usernameField);

        add(new JLabel("Leave date (yyyy-mm-dd):"));
        add(dateField);

        add(new JLabel("Reason:"));
        add(reasonField);

        add(new JLabel("Status:"));
        add(statusBox);

        JButton addButton = new JButton("Add Duty Leave");
        add(addButton);
        add(new JLabel(""));

        addButton.addActionListener(e -> addDutyLeave());
    }

    private void addDutyLeave() {
        String username = usernameField.getText().trim();
        String dateText = dateField.getText().trim();
        String reason = reasonField.getText().trim();
        String status = (String) statusBox.getSelectedItem();

        if (username.isEmpty() || dateText.isEmpty()
                || reason.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill in all fields."
            );
            return;
        }

        try {
            Date leaveDate = Date.valueOf(
                LocalDate.parse(dateText)
            );

            DutyLeavedao dao = new DutyLeavedao();
            int athleteId = dao.getAthleteIdByUsername(username);

            if (athleteId == -1) {
                JOptionPane.showMessageDialog(
                    this, "Athlete not found. Check the username."
                );
                return;
            }

            DutyLeave leave = new DutyLeave(
                athleteId, leaveDate, reason, status
            );

            if (dao.addDutyLeave(leave)) {
                JOptionPane.showMessageDialog(
                    this, "Duty leave added successfully!"
                );

                usernameField.setText("");
                dateField.setText("");
                reasonField.setText("");
                statusBox.setSelectedIndex(0);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, "Enter a valid date, e.g. 2026-10-22."
            );
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not add duty leave: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}