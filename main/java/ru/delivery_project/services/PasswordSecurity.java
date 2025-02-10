package ru.delivery_project.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordSecurity {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
