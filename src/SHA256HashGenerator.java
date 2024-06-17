import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SHA256HashGenerator {

    public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string to hash with SHA-256: ");
        String input = scanner.nextLine().trim();

        String sha256Hash = generateSHA256Hash(input);
        System.out.println("SHA-256 hash of '" + input + "' is: " + sha256Hash);

        scanner.close();
    }

    public String generateSHA256Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            return null;
        }
    }
}
