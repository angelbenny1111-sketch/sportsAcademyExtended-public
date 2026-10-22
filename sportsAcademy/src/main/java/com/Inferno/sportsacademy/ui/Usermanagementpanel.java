
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.service.Authservice;

import javax.swing.*;
import java.awt.*;

public class Usermanagementpanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    private Authservice authService;

    public Usermanagementpanel() {
       
        authService = new Authservice();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[]{"ADMIN", "COACH", "ATHLETE"});
        formPanel.add(roleBox);

        JButton registerButton = new JButton("Register User");
        JButton clearButton = new JButton("Clear Fields");

        formPanel.add(registerButton);
        formPanel.add(clearButton);

        add(formPanel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> registerUser());
        clearButton.addActionListener(e -> clearFields());
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Username and password are required."
            );
            return;
        }

        boolean success = authService.register(username, password, role);

        if (success) {
            JOptionPane.showMessageDialog(
                this,
                "User registered successfully!"
            );
            clearFields();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Registration failed. The username may already exist, or the database may be unavailable.",
                "Registration Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        roleBox.setSelectedIndex(0);
    }
}

