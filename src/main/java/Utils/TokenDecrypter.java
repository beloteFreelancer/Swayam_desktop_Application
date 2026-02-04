/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.security.MessageDigest;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TokenDecrypter {

    private static final String BASE_KEY = "g4t7shj48h";

    public static Map<String, String> decryptToken(String base64Token) throws Exception {
        // Decode base64
        byte[] decodedBytes = Base64.getDecoder().decode(base64Token);
        String encrypted = new String(decodedBytes, "UTF-8");

        // Reverse the 10-layer encryption
        String decrypted = encrypted;
        int first = 1, last = 1;
        List<String> keys = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String key = first + BASE_KEY + last;
            keys.add(key);
            if (i <= 5) {
                last++;
            } else if (i <= 10) {
                last += first;
            }
        }

        // Reverse order of encryption keys for decryption
        Collections.reverse(keys);
        for (String key : keys) {
            decrypted = decrypt(decrypted, key);
        }

        // Extract token fields
        String[] parts = decrypted.split("\\|");
        if (parts.length != 5) { // Updated to expect 5 parts
            throw new IllegalArgumentException("Invalid decrypted token format. Expected 5 parts.");
        }

        Map<String, String> tokenData = new LinkedHashMap<>();
        tokenData.put("installDate", parts[0]);
        tokenData.put("tokenExpiry", parts[1]);
        tokenData.put("planExpiry", parts[2]);
        tokenData.put("password", parts[3]);
        tokenData.put("cid", parts[4]); // License ID

        return tokenData;
    }

    private static String decrypt(String strToDecrypt, String secret) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] key = sha.digest(secret.getBytes("UTF-8"));
        key = Arrays.copyOf(key, 16); // AES-128
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decoded = Base64.getDecoder().decode(strToDecrypt);
        return new String(cipher.doFinal(decoded), "UTF-8");
    }
}
