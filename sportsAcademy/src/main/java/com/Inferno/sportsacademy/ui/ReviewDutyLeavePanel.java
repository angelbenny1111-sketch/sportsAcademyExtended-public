package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.dao.DutyLeavedao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ReviewDutyLeavePanel extends JPanel {

    private final DutyLeavedao dao = new DutyLeavedao();

    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID", "Athlete", "Leave Date", "Reason"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(model);

    public ReviewDutyLeavePanel() {
        setLayout(new BorderLayout(10, 10));

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        JButton refreshButton = new JButton("Refresh");

        buttons.add(approveButton);
        buttons.add(rejectButton);
        buttons.add(refreshButton);

        add(buttons, BorderLayout.SOUTH);

        approveButton.addActionListener(e -> updateStatus("APPROVED"));
        rejectButton.addActionListener(e -> updateStatus("REJECTED"));
        refreshButton.addActionListener(e -> loadRequests());

        loadRequests();
    }

    private void loadRequests() {
        model.setRowCount(0);

        try {
            List<Object[]> requests = dao.getPendingDutyLeaves();

            for (Object[] request : requests) {
                model.addRow(request);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load requests: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateStatus(String status) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select a request first."
            );
            return;
        }

        int dutyLeaveId = (int) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to " + status.toLowerCase()
                + " this request?",
            "Confirm Decision",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if (dao.updateDutyLeaveStatus(dutyLeaveId, status)) {
                JOptionPane.showMessageDialog(
                    this, "Request " + status.toLowerCase() + " successfully."
                );
                loadRequests();
            } else {
                JOptionPane.showMessageDialog(
                    this, "This request has already been processed. Refresh the list."
                );
                loadRequests();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not update request: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}