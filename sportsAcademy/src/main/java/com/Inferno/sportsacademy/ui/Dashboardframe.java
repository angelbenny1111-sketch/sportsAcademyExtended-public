package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.User;

import javax.swing.*;
import java.awt.*;

public class Dashboardframe extends JFrame {
    private final User loggedInUser;

    public Dashboardframe(User user) {
        this.loggedInUser = user;

        setTitle("Sports Academy Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(180, 30, 45));
        topPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel titleLabel = new JLabel("Sports Academy Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel welcomeLabel = new JLabel(
            "Welcome, " + user.getUsername()
            + "  |  Role: " + user.getRole()
        );
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(welcomeLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // dashboard content
        JPanel contentPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        contentPanel.setBackground(new Color(37, 42, 53));
        contentPanel.setBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        String role = user.getRole();

        // duty leave warning for athletes
        if ("ATHLETE".equalsIgnoreCase(role)) {
            try {
                com.Inferno.sportsacademy.dao.DutyLeavedao dao =
                    new com.Inferno.sportsacademy.dao.DutyLeavedao();

                int athleteId =
                    dao.getAthleteIdByUsername(user.getUsername());

                if (athleteId != -1) {
                    int count = dao.countApprovedDutyLeaves(athleteId);

                    if (count > 4) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Warning: You have " + count
                            + " approved duty leaves this academic year.",
                            "Duty Leave Warning",
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            } catch (java.sql.SQLException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Could not check duty leaves: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }

        // role-based dashboard buttons
        if ("ADMIN".equalsIgnoreCase(role)) {
            addDashboardButton(contentPanel, "Manage Athletes");
            addDashboardButton(contentPanel, "Manage Coaches");
            addDashboardButton(contentPanel, "Manage Sports");
            addDashboardButton(contentPanel, "Manage Training Sessions");
            addDashboardButton(contentPanel, "Manage Attendance");
            addDashboardButton(contentPanel, "Manage Fees");
            addDashboardButton(contentPanel, "User Management");
            addDashboardButton(contentPanel, "Manage Duty Leaves");

        } else if ("COACH".equalsIgnoreCase(role)) {
            addDashboardButton(contentPanel, "Manage Athletes");
            addDashboardButton(contentPanel, "Manage Training Sessions");
            addDashboardButton(contentPanel, "Manage Attendance");

        } else if ("ATHLETE".equalsIgnoreCase(role)) {
            addDashboardButton(contentPanel, "My Profile");
            addDashboardButton(contentPanel, "My Sports");
            addDashboardButton(contentPanel, "My Attendance");
            addDashboardButton(contentPanel, "My Training Schedule");
            addDashboardButton(contentPanel, "My Fees");
            addDashboardButton(contentPanel, "Request Duty Leave");

        } else {
            JOptionPane.showMessageDialog(
                this,
                "Your account has an invalid role. Please contact the administrator.",
                "Access Denied",
                JOptionPane.ERROR_MESSAGE
            );
            dispose();
            new Loginframe().setVisible(true);
            return;
        }

        add(contentPanel, BorderLayout.CENTER);

        // bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                new Loginframe().setVisible(true);
                dispose();
            }
        });

        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addDashboardButton(JPanel panel, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Cambria", Font.BOLD, 15));
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            JPanel selectedPanel = null;

            switch (buttonText) {
                case "Manage Athletes":
                    selectedPanel = new Athletepanel();
                    break;

                case "My Profile":
                    selectedPanel = new Athleteprofilepanel(loggedInUser);
                    break;

                case "My Sports":
                    selectedPanel = new Mysportspanel(loggedInUser);
                    break;

                case "My Attendance":
                    selectedPanel = new Myattendancepanel(loggedInUser);
                    break;

                case "My Training Schedule":
                    selectedPanel = new Mytrainingpanel(loggedInUser);
                    break;

                case "My Fees":
                    selectedPanel = new Myfeespanel(loggedInUser);
                    break;

                case "Manage Coaches":
                    selectedPanel = new Coachpanel();
                    break;

                case "Manage Sports":
                    selectedPanel = new Sportpanel();
                    break;

                case "Manage Training Sessions":
                    selectedPanel = new Sessionpanel();
                    break;

                case "Manage Attendance":
                    selectedPanel = new Attendancepanel();
                    break;

                case "Manage Fees":
                    selectedPanel = new Feepanel();
                    break;

                case "User Management":
                    selectedPanel = new Usermanagementpanel();
                    break;
                    
                case "Request Duty Leave":
                    selectedPanel = new RequestDutyLeavePanel(loggedInUser);
                     break;
                case "Manage Duty Leaves":
                    selectedPanel = new ReviewDutyLeavePanel();
                    break;
            }

            if (selectedPanel != null) {
                JFrame frame = new JFrame(buttonText);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel container = new JPanel(new BorderLayout());
                JButton backButton = new JButton("← Back");

                backButton.addActionListener(event -> frame.dispose());

                container.add(backButton, BorderLayout.NORTH);
                container.add(selectedPanel, BorderLayout.CENTER);

                frame.setContentPane(container);
                frame.setSize(850, 550);
                frame.setLocationRelativeTo(this);
                frame.setVisible(true);
            }
        });

        panel.add(button);
    }
}