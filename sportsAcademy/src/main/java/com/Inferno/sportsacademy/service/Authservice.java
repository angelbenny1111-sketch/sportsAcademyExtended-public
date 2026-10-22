
package com.Inferno.sportsacademy.service;

import com.Inferno.sportsacademy.dao.Userdao;
import com.Inferno.sportsacademy.model.User;
import com.Inferno.sportsacademy.util.Passwordutil;
import com.Inferno.sportsacademy.util.Validationutil;

public class Authservice {

    private Userdao userDAO = new Userdao();

    public boolean register(String username, String password, String role) {

        if (!Validationutil.isNotEmpty(username)
                || !Validationutil.isNotEmpty(password)) {
            System.out.println("Username and password are required.");
            return false;
        }

        if (!Validationutil.isValidRole(role)) {
            System.out.println("Invalid role.");
            return false;
        }

        if (userDAO.getUserByUsername(username.trim()) != null) {
            System.out.println("Username already exists.");
            return false;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPasswordHash(Passwordutil.hashPassword(password));
        user.setRole(role);
        user.setActive(true);

        return userDAO.addUser(user);
    }

    public User login(String username, String password) {

        if (!Validationutil.isNotEmpty(username)
                || !Validationutil.isNotEmpty(password)) {
            System.out.println("Username and password are required.");
            return null;
        }

        User user = userDAO.getUserByUsername(username.trim());

        if (user == null || !user.isActive()) {
            System.out.println("Invalid username or inactive account.");
            return null;
        }

        if (!Passwordutil.checkPassword(password, user.getPasswordHash())) {
            System.out.println("Incorrect password.");
            return null;
        }

        return user;
    }


}

