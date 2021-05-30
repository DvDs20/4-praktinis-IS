package security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptDecryptPassword {
    private static final String ALGORITHM = "AES";

    private static final String KEY = "VerySecretKey";
    private static final String IV = "AAAASSSSDDDDFFFF";

    public static String encryptPassword(String password) throws Exception {
        return encrypt(KEY, IV, password);
    }

    public static String decryptPassword(String encryptedPassword) throws Exception {
        return decrypt(KEY, IV, encryptedPassword);
    }

    public static String encrypt(String key, String iv, String password) throws Exception {
        byte[] bytesOfKey = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] keyBytes = messageDigest.digest(bytesOfKey);

        final byte[] ivBytes = iv.getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        final byte[] resultBytes = cipher.doFinal(password.getBytes());
        return Base64.getMimeEncoder().encodeToString(resultBytes);
    }

    public static String decrypt(String key, String iv, String encryptedPassword) throws Exception{
        byte[] bytesOfKey = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] keyBytes = messageDigest.digest(bytesOfKey);

        final byte[] ivBytes = iv.getBytes();

        final byte[] encryptedBytes = Base64.getMimeDecoder().decode(encryptedPassword);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        final byte[] resultBytes = cipher.doFinal(encryptedBytes);
        return new String(resultBytes);
    }
}
