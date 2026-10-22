
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.Athlete;
import com.Inferno.sportsacademy.service.Athleteservice;
import com.Inferno.sportsacademy.service.AthleteSportservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Athletepanel extends JPanel {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField sportField;

    private JTextField athleteIdField;
    private JTextField sportIdField;
    private JTextField coachIdField;

    private JTable athleteTable;
    private DefaultTableModel tableModel;
    private Athleteservice athleteService;
    private AthleteSportservice athleteSportService;

    public Athletepanel() {
        athleteService = new Athleteservice();
        athleteSportService = new AthleteSportservice();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Athlete Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // Existing athlete form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone (10 digits):"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Sport:"));
        sportField = new JTextField();
        formPanel.add(sportField);

        JButton addButton = new JButton("Add Athlete");
        JButton deleteButton = new JButton("Delete Selected");
        formPanel.add(addButton);
        formPanel.add(deleteButton);

        // Athlete table
        String[] columns = {"ID", "Name", "Email", "Phone", "Sport"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        athleteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(athleteTable);

        // New multi-sport enrolment form
        JPanel enrollmentPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        enrollmentPanel.setBorder(
            BorderFactory.createTitledBorder("Enroll Athlete in Sport")
        );

        enrollmentPanel.add(new JLabel("Athlete ID:"));
        athleteIdField = new JTextField();
        enrollmentPanel.add(athleteIdField);

        enrollmentPanel.add(new JLabel("Sport ID:"));
        sportIdField = new JTextField();
        enrollmentPanel.add(sportIdField);

        enrollmentPanel.add(new JLabel("Coach ID:"));
        coachIdField = new JTextField();
        enrollmentPanel.add(coachIdField);

        JButton enrollButton = new JButton("Enroll");
        JButton viewSportsButton = new JButton("View Athlete Sports");
        enrollmentPanel.add(enrollButton);
        enrollmentPanel.add(viewSportsButton);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(enrollmentPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addAthlete());
        deleteButton.addActionListener(e -> deleteAthlete());
        enrollButton.addActionListener(e -> enrollAthlete());
        viewSportsButton.addActionListener(e -> viewAthleteSports());

        loadAthletes();
    }

    private void addAthlete() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String sport = sportField.getText().trim();

        boolean success = athleteService.addAthlete(
            name, email, phone, sport
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Athlete added successfully!");
            clearFields();
            loadAthletes();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Could not add athlete. Check the details and database connection.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadAthletes() {
        tableModel.setRowCount(0);

        List<Athlete> athletes = athleteService.getAllAthletes();

        for (Athlete athlete : athletes) {
            tableModel.addRow(new Object[]{
                athlete.getAthleteId(),
                athlete.getName(),
                athlete.getEmail(),
                athlete.getPhone(),
                athlete.getSportName()
            });
        }
    }

    private void enrollAthlete() {
        try {
            int athleteId = Integer.parseInt(athleteIdField.getText().trim());
            int sportId = Integer.parseInt(sportIdField.getText().trim());

            String coachText = coachIdField.getText().trim();
            Integer coachId = coachText.isEmpty()
                ? null
                : Integer.parseInt(coachText);

            boolean success = athleteSportService.enrollAthlete(
                athleteId, sportId, coachId
            );

            if (success) {
                JOptionPane.showMessageDialog(
                    this, "Athlete enrolled successfully!"
                );
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Athlete ID and Sport ID must be numbers. Coach ID is optional.",
                "Invalid input",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not enrol athlete. Check the IDs and ensure the athlete-sport combination is not already enrolled.\n"
                    + ex.getMessage(),
                "Database error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, ex.getMessage(), "Invalid input",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewAthleteSports() {
        try {
            int athleteId = Integer.parseInt(athleteIdField.getText().trim());

            List<String> sports =
                athleteSportService.getSportsForAthlete(athleteId);

            String message = sports.isEmpty()
                ? "No active sports found for this athlete."
                : String.join("\n", sports);

            JOptionPane.showMessageDialog(
                this, message, "Athlete's Active Sports",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Enter a valid Athlete ID first.",
                "Invalid input", JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Could not retrieve sports:\n" + ex.getMessage(),
                "Database error", JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, ex.getMessage(), "Invalid input",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteAthlete() {
        int selectedRow = athleteTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an athlete first.");
            return;
        }

        int athleteId = (int) tableModel.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Delete the selected athlete?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (athleteService.deleteAthlete(athleteId)) {
                JOptionPane.showMessageDialog(this, "Athlete deleted.");
                loadAthletes();
            } else {
                JOptionPane.showMessageDialog(
                    this, "Could not delete athlete.",
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        sportField.setText("");
    }
}
