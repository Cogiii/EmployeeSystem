import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private final String EMPLOYEE_DATA_FILE = "data/employee.txt";
    private final String USERS_DATA_FILE = "data/users.txt";

    // Method to delete a row from the employee data file based on user_ID
    public void deleteEmployeeData(String user_ID) {
        try {
            File inputFile = new File(EMPLOYEE_DATA_FILE);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemoveStr = "";
            String currentLine;
            
            int lineNum = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split("#");
                if (data[0].equals(user_ID)) {
                    lineToRemoveStr = currentLine; // Store the line we want to delete
                    lineNum++;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
                lineNum++;
            }
            writer.close();
            reader.close();

            // Delete the original file
            if (inputFile.delete()) {
                // Rename the temp file to the original file name
                if (!tempFile.renameTo(inputFile)) {
                    throw new IOException("Could not rename temp file to " + EMPLOYEE_DATA_FILE);
                }
            } else {
                System.out.println("Failed to delete the line.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a row from the users data file based on user_ID
    public void deleteUser(String user_ID) {
        try {
            File inputFile = new File(USERS_DATA_FILE);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemoveStr = "";
            String currentLine;
            
            int lineNum = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split("#");
                if (data[2].equals(user_ID)) {
                    lineToRemoveStr = currentLine; // Store the line we want to delete
                    lineNum++;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
                lineNum++;
            }
            writer.close();
            reader.close();

            // Delete the original file
            if (inputFile.delete()) {
                // Rename the temp file to the original file name
                if (!tempFile.renameTo(inputFile)) {
                    throw new IOException("Could not rename temp file to " + USERS_DATA_FILE);
                }
            } else {
                System.out.println("Failed to delete the line.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeData(String username, String name, String department, String designation, String grossPay, String user_ID) {
        Path employeeDataPath = Paths.get("data/employee.txt");
        Path usersDataPath = Paths.get("data/users.txt");

        try {
            List<String> employeeLines = Files.readAllLines(employeeDataPath);
            List<String> usersLines = Files.readAllLines(usersDataPath);

            // Update employee.txt
            for (int i = 1; i < employeeLines.size(); i++) { // Start from index 1 to skip header line
                String line = employeeLines.get(i);
                String[] row = line.split("#");
                if (row[0].equals(String.valueOf(user_ID))) {
                    // Update fields in the line
                    row[1] = name;
                    row[2] = department;
                    row[3] = designation;
                    row[12] = grossPay;

                    // Reconstruct the line
                    StringBuilder updatedLine = new StringBuilder();
                    for (int j = 0; j < row.length; j++) {
                        if (j > 0) {
                            updatedLine.append("#");
                        }
                        updatedLine.append(row[j]);
                    }

                    // Update the list
                    employeeLines.set(i, updatedLine.toString());
                    break; // Exit loop once updated
                }
            }

            // Update users.txt
            for (int i = 1; i < usersLines.size(); i++) { // Start from index 1 to skip header line
                String line = usersLines.get(i);
                String[] row = line.split("#");
                if (row[2].equals(String.valueOf(user_ID))) {
                    // Update username
                    row[0] = username;

                    // Reconstruct the line
                    StringBuilder updatedLine = new StringBuilder();
                    for (int j = 0; j < row.length; j++) {
                        if (j > 0) {
                            updatedLine.append("#");
                        }
                        updatedLine.append(row[j]);
                    }

                    // Update the list
                    usersLines.set(i, updatedLine.toString());
                    break; // Exit loop once updated
                }
            }

            // Write updated lines back to files
            Files.write(employeeDataPath, employeeLines);
            Files.write(usersDataPath, usersLines);

            System.out.println("Employee details updated successfully.");

        } catch (IOException e) {
            System.err.println("Error updating employee data: " + e.getMessage());
        }
    }
    
}
