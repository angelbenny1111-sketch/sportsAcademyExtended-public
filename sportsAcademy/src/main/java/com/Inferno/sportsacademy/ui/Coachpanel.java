
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.Coach;
import com.Inferno.sportsacademy.service.Coachservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Coachpanel extends JPanel {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JTable coachTable;
    private DefaultTableModel tableModel;
    private Coachservice coachService;

    public Coachpanel() {
        
        coachService = new Coachservice();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Coach Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // coach input form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone (10 digits):"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        JButton addButton = new JButton("Add Coach");
        JButton deleteButton = new JButton("Delete Selected");
        formPanel.add(addButton);
        formPanel.add(deleteButton);

        // coach table
        String[] columns = {"ID", "Name", "Email", "Phone"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coachTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(coachTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addCoach());
        deleteButton.addActionListener(e -> deleteCoach());

        loadCoaches();
    }

    private void addCoach() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        boolean success = coachService.addCoach(name, email, phone);

        if (success) {
            JOptionPane.showMessageDialog(this, "Coach added successfully!");
            clearFields();
            loadCoaches();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Could not add coach. Check the details and database connection.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadCoaches() {
        tableModel.setRowCount(0);

        List<Coach> coaches = coachService.getAllCoaches();

        for (Coach coach : coaches) {
            tableModel.addRow(new Object[]{
                coach.getCoachId(),
                coach.getName(),
                coach.getEmail(),
                coach.getPhone()
            });
        }
    }

    private void deleteCoach() {
        int selectedRow = coachTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a coach first.");
            return;
        }

        int coachId = (int) tableModel.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Delete the selected coach?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (coachService.deleteCoach(coachId)) {
                JOptionPane.showMessageDialog(this, "Coach deleted.");
                loadCoaches();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Could not delete coach.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}
