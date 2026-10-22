
package com.Inferno.sportsacademy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sports_academy";

    private static final String USER = "root";

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            throw new SQLException("DB_PASSWORD is not set.");
        }

        return DriverManager.getConnection(URL, USER, password);
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
    if (con.isValid(2)) {
        System.out.println("database connected successfully!");
    }
        } catch (SQLException e) {
            System.out.println("database connection failed!");
            System.out.println(e.getMessage());
        }
    }
}
