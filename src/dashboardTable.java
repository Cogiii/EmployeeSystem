import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class dashboardTable {

    public static void main(String[] args) {
        dashboardTable resources = new dashboardTable();
        resources.openUserFile();
    }

    public void openUserFile() {
        Path usersDataPath = Paths.get("data/employee.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;

            // Skip the first line/header line
            br.readLine();  
            while ((line = br.readLine()) != null) {
                String[] usersInfo = line.split("#");
                if (usersInfo.length >= 3) {
                    System.out.print(usersInfo[0] + " " + usersInfo[1] + " " + usersInfo[2]);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}
