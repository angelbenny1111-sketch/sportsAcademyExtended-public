
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.dao.Sportdao;
import com.Inferno.sportsacademy.model.Sport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Sportpanel extends JPanel {

    private JTextField sportNameField;
    private JTextField descriptionField;

    private JTable sportTable;
    private DefaultTableModel tableModel;
    private Sportdao sportDAO;

    public Sportpanel() {
        
        sportDAO = new Sportdao();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Sport Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));

        formPanel.add(new JLabel("Sport Name:"));
        sportNameField = new JTextField();
        formPanel.add(sportNameField);

        formPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        JButton addButton = new JButton("Add Sport");
        JButton deleteButton = new JButton("Delete Selected");
        formPanel.add(addButton);
        formPanel.add(deleteButton);

        String[] columns = {"ID", "Sport Name", "Description"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sportTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(sportTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addSport());
        deleteButton.addActionListener(e -> deleteSport());

        loadSports();
    }

    private void addSport() {
        String name = sportNameField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a sport name.");
            return;
        }

        Sport sport = new Sport();
        sport.setSportName(name);
        sport.setDescription(description);

        if (sportDAO.addSport(sport)) {
            JOptionPane.showMessageDialog(this, "Sport added successfully!");
            sportNameField.setText("");
            descriptionField.setText("");
            loadSports();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Could not add sport. Check the database connection or duplicate name.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadSports() {
        tableModel.setRowCount(0);

        List<Sport> sports = sportDAO.getAllSports();

        for (Sport sport : sports) {
            tableModel.addRow(new Object[]{
                sport.getSportId(),
                sport.getSportName(),
                sport.getDescription()
            });
        }
    }

    private void deleteSport() {
        int selectedRow = sportTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a sport first.");
            return;
        }

        int sportId = (int) tableModel.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Delete the selected sport?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (sportDAO.deleteSport(sportId)) {
                JOptionPane.showMessageDialog(this, "Sport deleted.");
                loadSports();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Could not delete sport. It may be used by a training session.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
