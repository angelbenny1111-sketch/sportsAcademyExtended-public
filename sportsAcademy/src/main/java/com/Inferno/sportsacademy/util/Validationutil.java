
package com.Inferno.sportsacademy.util;

public class Validationutil {

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@")
                && email.contains(".");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]{10}");
    }

    public static boolean isPositiveAmount(double amount) {
        return amount > 0;
    }

    public static boolean isValidRole(String role) {
        return "ADMIN".equals(role)
                || "COACH".equals(role)
                || "ATHLETE".equals(role);
    }
}
