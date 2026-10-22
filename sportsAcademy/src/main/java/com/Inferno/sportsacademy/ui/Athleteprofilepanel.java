
package com.Inferno.sportsacademy.ui;

import com.Inferno.sportsacademy.model.Athlete;
import com.Inferno.sportsacademy.model.User;
import com.Inferno.sportsacademy.service.Athleteservice;

import javax.swing.*;
import java.awt.*;

public class Athleteprofilepanel extends JPanel {

    public Athleteprofilepanel(User user) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel heading = new JLabel("My Profile");
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        Athlete athlete =
        new Athleteservice().getAthleteByUsername(user.getUsername());

        if (athlete == null) {
            add(new JLabel("No athlete profile is linked to your account."),
                    BorderLayout.CENTER);
            return;
        }

        JPanel details = new JPanel(new GridLayout(0, 1, 8, 8));

        details.add(new JLabel("Athlete ID: " + athlete.getAthleteId()));
        details.add(new JLabel("Name: " + athlete.getName()));
        details.add(new JLabel("Email: " + athlete.getEmail()));
        details.add(new JLabel("Phone: " + athlete.getPhone()));
        details.add(new JLabel("Legacy Sport: " + athlete.getSportName()));

        add(details, BorderLayout.CENTER);
    }
}