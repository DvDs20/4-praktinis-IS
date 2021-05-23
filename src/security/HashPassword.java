package security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashPassword {

    //workload  10 - 31 is valid value;
    private static int workload = 10;

    public static String BCryptPassword(String passwordPlainText) {
        String salt = BCrypt.gensalt(workload);

        return BCrypt.hashpw(passwordPlainText, salt);
    }
}
