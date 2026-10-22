package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.dao.Attendancedao;
import com.Inferno.sportsacademy.model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Attendancepanel extends JPanel {

    private JTextField athleteIdField;
    private JTextField trainingIdField;
    private JTextField dateField;
    private JComboBox<String> statusBox;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private Attendancedao attendanceDAO;

    public Attendancepanel() {
        
        attendanceDAO = new Attendancedao();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Attendance Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        formPanel.add(new JLabel("Athlete ID:"));
        athleteIdField = new JTextField();
        formPanel.add(athleteIdField);

        formPanel.add(new JLabel("Training ID:"));
        trainingIdField = new JTextField();
        formPanel.add(trainingIdField);

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Status:"));
        statusBox = new JComboBox<>(new String[]{"Present", "Absent"});
        formPanel.add(statusBox);

        JButton addButton = new JButton("Record Attendance");
        JButton refreshButton = new JButton("Refresh");
        formPanel.add(addButton);
        formPanel.add(refreshButton);

        String[] columns = {
            "Athlete ID", "Training ID", "Date", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addAttendance());
        refreshButton.addActionListener(e -> loadAttendance());

        loadAttendance();
    }

    private void addAttendance() {
        try {
            int athleteId = Integer.parseInt(athleteIdField.getText().trim());
            int trainingId = Integer.parseInt(trainingIdField.getText().trim());
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            String status = (String) statusBox.getSelectedItem();

            if (athleteId <= 0 || trainingId <= 0) {
                JOptionPane.showMessageDialog(
                    this, "Athlete ID and Training ID must be positive numbers."
                );
                return;
            }

            Attendance attendance = new Attendance();
            attendance.setAthleteId(athleteId);
            attendance.setTrainingId(trainingId);
            attendance.setAttendanceDate(date);
            attendance.setStatus(status);

            if (attendanceDAO.addAttendance(attendance)) {
                JOptionPane.showMessageDialog(
                    this, "Attendance recorded successfully!"
                );
                athleteIdField.setText("");
                trainingIdField.setText("");
                dateField.setText("");
                loadAttendance();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Could not record attendance. Check the IDs or whether attendance already exists for this session.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Athlete ID and Training ID must be numbers."
            );
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                this, "Enter the date in YYYY-MM-DD format."
            );
        }
    }

    private void loadAttendance() {
        tableModel.setRowCount(0);

        List<Attendance> records = attendanceDAO.getAllAttendance();

        for (Attendance record : records) {
            tableModel.addRow(new Object[]{
                record.getAthleteId(),
                record.getTrainingId(),
                record.getAttendanceDate(),
                record.getStatus()
            });
        }
    }
}