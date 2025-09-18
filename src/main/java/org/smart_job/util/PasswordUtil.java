package org.smart_job.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Number of rounds (higher is safer but slower)
    private static final int LOG_ROUNDS = 12;

    /**
     * Use BCrypt to hash password
     */
    public static String encode(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Check the password the user entered matches the hash in the DB
     */
    public static boolean matches(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
