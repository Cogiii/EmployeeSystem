import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FileAuthenticator {

    private static final String FILE_PATH = "data/users.txt"; // Path to your file containing usernames and hashed passwords
    public boolean authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            reader.readLine(); // skip first line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 3) {
                    String storedUsername = parts[0];
                    String storedHashedPassword = parts[1];
                    if (storedUsername.equals(username) && verifyPassword(password, storedHashedPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return false;
    }

    private static boolean verifyPassword(String password, String storedHashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(password.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }
            String hashedPassword = sb.toString();

            return hashedPassword.equals(storedHashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            return false;
        }
    }
}
