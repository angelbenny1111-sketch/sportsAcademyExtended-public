
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.dao.Trainingdao;
import com.Inferno.sportsacademy.model.Training;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Sessionpanel extends JPanel {

    private JTextField sportIdField;
    private JTextField coachIdField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField venueField;

    private JTable sessionTable;
    private DefaultTableModel tableModel;
    private Trainingdao trainingDAO;

    public Sessionpanel() {
        
        trainingDAO = new Trainingdao();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Training Session Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 8));

        formPanel.add(new JLabel("Sport ID:"));
        sportIdField = new JTextField();
        formPanel.add(sportIdField);

        formPanel.add(new JLabel("Coach ID:"));
        coachIdField = new JTextField();
        formPanel.add(coachIdField);

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Time (HH:MM, 24-hour):"));
        timeField = new JTextField();
        formPanel.add(timeField);

        formPanel.add(new JLabel("Venue:"));
        venueField = new JTextField();
        formPanel.add(venueField);

        JButton addButton = new JButton("Add Session");
        JButton deleteButton = new JButton("Delete Selected");
        formPanel.add(addButton);
        formPanel.add(deleteButton);

        String[] columns = {
            "Training ID", "Sport ID", "Coach ID", "Date", "Time", "Venue"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sessionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(sessionTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addSession());
        deleteButton.addActionListener(e -> deleteSession());

        loadSessions();
    }

    private void addSession() {
        try {
            int sportId = Integer.parseInt(sportIdField.getText().trim());
            int coachId = Integer.parseInt(coachIdField.getText().trim());

            LocalDate date = LocalDate.parse(dateField.getText().trim());
            LocalTime time = LocalTime.parse(timeField.getText().trim());

            String venue = venueField.getText().trim();

            if (sportId <= 0 || coachId <= 0 || venue.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "Enter valid sport ID, coach ID, and venue."
                );
                return;
            }

            Training training = new Training();
            training.setSportId(sportId);
            training.setCoachId(coachId);
            training.setTrainingDate(date);
            training.setTrainingTime(time);
            training.setVenue(venue);

            if (trainingDAO.addTraining(training)) {
                JOptionPane.showMessageDialog(
                    this, "Training session added successfully!"
                );
                clearFields();
                loadSessions();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Could not add session. Check that the sport and coach IDs exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Sport ID and Coach ID must be numbers."
            );
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Enter date as YYYY-MM-DD and time as HH:MM."
            );
        }
    }

    private void loadSessions() {
        tableModel.setRowCount(0);

        List<Training> sessions = trainingDAO.getAllTrainings();

        for (Training training : sessions) {
            tableModel.addRow(new Object[]{
                training.getTrainingId(),
                training.getSportId(),
                training.getCoachId(),
                training.getTrainingDate(),
                training.getTrainingTime(),
                training.getVenue()
            });
        }
    }

    private void deleteSession() {
        int selectedRow = sessionTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a session first.");
            return;
        }

        int trainingId = (int) tableModel.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Delete the selected training session?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (trainingDAO.deleteTraining(trainingId)) {
                JOptionPane.showMessageDialog(this, "Session deleted.");
                loadSessions();
            } else {
                JOptionPane.showMessageDialog(
                    this, "Could not delete session.",
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void clearFields() {
        sportIdField.setText("");
        coachIdField.setText("");
        dateField.setText("");
        timeField.setText("");
        venueField.setText("");
    }
}
