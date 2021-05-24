package security;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public static String hashPassword(String passwordPlainText) throws NoSuchAlgorithmException {
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        byte[] digest = MD5.digest(passwordPlainText.getBytes(StandardCharsets.UTF_8));
        return String.format("%032x%n", new BigInteger(1, digest));
    }
}
