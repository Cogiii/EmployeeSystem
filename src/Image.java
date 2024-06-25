import java.nio.file.Path;
import java.nio.file.Paths;

public class Image {
    public static Path getFilePath(String relativePath) {
        return Paths.get(relativePath);
    }

    public static void main(String[] args) {
        // Example usage:
        String relativePath = "data/file.txt";
        Path filePath = getFilePath(relativePath);

        System.out.println("Absolute path: " + filePath.toAbsolutePath());
    }
}
