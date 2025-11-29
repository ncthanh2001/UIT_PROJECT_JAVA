package org.smart_job.util;

import java.util.regex.Pattern;

public class Extensions {
    public static boolean  isValidEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }

}
