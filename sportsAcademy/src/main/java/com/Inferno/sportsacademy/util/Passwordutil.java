
package com.Inferno.sportsacademy.util;

import java.security.MessageDigest;

public class Passwordutil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());

            String result = "";

            for (byte b : bytes) {
                result += String.format("%02x", b);
            }

            return result;

        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
}
