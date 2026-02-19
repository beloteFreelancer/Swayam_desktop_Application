package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils
 {
    // Generate a salt
    private static String getSalt()
     {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash password with salt
    private static String hashPassword(String password, String salt) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
          }
         catch (NoSuchAlgorithmException e)
          {
            throw new RuntimeException("Error hashing password", e);
          }
    }

    // Public method to generate salt+hash
    // MODIFIED: Normalizes password to lowercase before hashing to support case-insensitive login
    public static String hashPassword(String password) {
        String salt = getSalt();
        // Force lowercase for new hashes to ensure case-insensitivity
        String hash = hashPassword(password.toLowerCase(), salt);
        return salt + "$" + hash;
    }

    // Verify password (handles both salted hash and legacy plain text)
    public static boolean verifyPassword(String providedPassword, String storedValue) {
        if (storedValue == null) return false;

        String[] parts = storedValue.split("\\$");
        if (parts.length != 2) {
            // Assume legacy plain text
            // MODIFIED: Use equalsIgnoreCase for legacy plain text support
            return providedPassword.equalsIgnoreCase(storedValue);
        }
        String salt = parts[0];
        String hash = parts[1];

        // 1. Check exact match (legacy mixed-case support)
        // If the stored password was created before this change (mixed case), we try to verify it as-is.
        String calculatedHash = hashPassword(providedPassword, salt);
        if (calculatedHash.equals(hash)) {
            return true;
        }

        // 2. Check lowercase match (new case-insensitive support)
        // If the stored password was created with this new logic (lowercased),
        // OR if we want to allow user to type "Admin" for a password stored as "admin".
        String calculatedHashLower = hashPassword(providedPassword.toLowerCase(), salt);
        if (calculatedHashLower.equals(hash))
         {

            return true;

         }

        return false;
    }
}
