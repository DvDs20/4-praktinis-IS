package security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptDecryptFile {

    private static final String ALGORITHM = "AES";

    private static final String KEY = "VerySecretKey";
    private static final String IV = "AAAASSSSDDDDFFFF";


    public static void encryptFile(String filePath) throws Exception {
        String fileText = readFile(filePath);
        String encryptedFileText = encrypt(KEY, IV, fileText);
        PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8);
        writer.print(encryptedFileText);
        writer.close();
    }

    public static void decryptFile(String filePath) throws Exception {
        String fileText = readFile(filePath);
        String decryptedFileText = decrypt(KEY, IV, fileText);
        PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8);
        writer.print(decryptedFileText);
        writer.close();
    }

    public static String encrypt(String key, String iv, String fileText) throws Exception {
        byte[] bytesOfKey = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] keyBytes = messageDigest.digest(bytesOfKey);

        final byte[] ivBytes = iv.getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        final byte[] resultBytes = cipher.doFinal(fileText.getBytes());
        return Base64.getMimeEncoder().encodeToString(resultBytes);
    }

    public static String decrypt(String key, String iv, String encryptedFileText) throws Exception{
        byte[] bytesOfKey = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] keyBytes = messageDigest.digest(bytesOfKey);

        final byte[] ivBytes = iv.getBytes();

        final byte[] encryptedBytes = Base64.getMimeDecoder().decode(encryptedFileText);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        final byte[] resultBytes = cipher.doFinal(encryptedBytes);
        return new String(resultBytes);
    }

    public static String readFile(String fileName) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }
    }

}
