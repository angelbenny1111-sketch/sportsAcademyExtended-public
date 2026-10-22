
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.Fee;
import com.Inferno.sportsacademy.service.Feeservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Feepanel extends JPanel {

    private JTextField athleteIdField;
    private JTextField amountField;
    private JTextField dateField;
    private JComboBox<String> statusBox;

    private JTable feeTable;
    private DefaultTableModel tableModel;
    private Feeservice feeService;

    public Feepanel() {
        
        feeService = new Feeservice();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Fee Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        formPanel.add(new JLabel("Athlete ID:"));
        athleteIdField = new JTextField();
        formPanel.add(athleteIdField);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Payment Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Status:"));
        statusBox = new JComboBox<>(new String[]{"pending", "paid"});
        formPanel.add(statusBox);

        JButton addButton = new JButton("Record Fee");
        JButton refreshButton = new JButton("Refresh");
        formPanel.add(addButton);
        formPanel.add(refreshButton);

        String[] columns = {
            "Fee ID", "Athlete ID", "Amount", "Payment Date", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        feeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(feeTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addFee());
        refreshButton.addActionListener(e -> loadFees());

        loadFees();
    }

    private void addFee() {
        try {
            int athleteId = Integer.parseInt(athleteIdField.getText().trim());
            BigDecimal amount = new BigDecimal(amountField.getText().trim());

            String dateText = dateField.getText().trim();
            LocalDate paymentDate = dateText.isEmpty()
                ? null : LocalDate.parse(dateText);

            String status = (String) statusBox.getSelectedItem();

            if (athleteId <= 0 || amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                    this, "Enter a valid athlete ID and positive amount."
                );
                return;
            }

            boolean success = feeService.addFee(
                athleteId,
                amount.doubleValue(),
                paymentDate,
                status
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Fee recorded successfully!");
                athleteIdField.setText("");
                amountField.setText("");
                dateField.setText("");
                loadFees();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Could not record fee. Check the athlete ID and database connection.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Enter a valid athlete ID and amount."
            );
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                this, "Enter the payment date as YYYY-MM-DD, or leave it blank."
            );
        }
    }

    private void loadFees() {
        tableModel.setRowCount(0);

        List<Fee> fees = feeService.getAllFees();

        for (Fee fee : fees) {
            tableModel.addRow(new Object[]{
                fee.getFeeId(),
                fee.getAthleteId(),
                fee.getAmount(),
                fee.getPaymentDate(),
                fee.getStatus()
            });
        }
    }
}

