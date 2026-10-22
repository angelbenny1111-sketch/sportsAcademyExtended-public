package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.dao.DutyLeavedao;
import com.Inferno.sportsacademy.model.DutyLeave;
import com.Inferno.sportsacademy.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class RequestDutyLeavePanel extends JPanel {

    private final User user;
    private final JTextField dateField = new JTextField(15);
    private final JTextField reasonField = new JTextField(20);

    public RequestDutyLeavePanel(User user) {
        this.user = user;
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Leave date (yyyy-mm-dd):"));
        add(dateField);

        add(new JLabel("Reason:"));
        add(reasonField);

        JButton submitButton = new JButton("Submit Request");
        add(submitButton);
        add(new JLabel(""));

        submitButton.addActionListener(e -> submitRequest());
    }

    private void submitRequest() {
        String dateText = dateField.getText().trim();
        String reason = reasonField.getText().trim();

        if (dateText.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please enter the date and reason."
            );
            return;
        }

        try {
            Date leaveDate = Date.valueOf(
                LocalDate.parse(dateText)
            );

            if (leaveDate.toLocalDate().isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                    this, "Please select today or a future date."
                );
                return;
            }

            DutyLeavedao dao = new DutyLeavedao();
            int athleteId =
                dao.getAthleteIdByUsername(user.getUsername());

            if (athleteId == -1) {
                JOptionPane.showMessageDialog(
                    this, "Your athlete account could not be found."
                );
                return;
            }

            DutyLeave leave = new DutyLeave(
                athleteId, leaveDate, reason, "PENDING"
            );

            if (dao.addDutyLeave(leave)) {
                JOptionPane.showMessageDialog(
                    this, "Duty leave request submitted!"
                );
                dateField.setText("");
                reasonField.setText("");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, "Enter a valid date, e.g. 2026-10-25."
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Could not submit request: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}