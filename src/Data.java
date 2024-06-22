import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Data {
    Path employeeDataPath = Paths.get("data/employee.txt");
    Path usersDataPath = Paths.get("data/users.txt");
    Path userTimesheetsPath = Paths.get("data/timesheets.txt");

    public HashMap<String,String> getUserData(String ID){
        HashMap<String, String> userData = new HashMap<>();

        try {
            List<String> employeeLines = Files.readAllLines(employeeDataPath);

            String[] headerLine = employeeLines.get(0).split("#");
            for (String key : headerLine) {
                userData.put(key, "");
            }

            // set user status to inactive in employee.txt
            for (int i = 1; i < employeeLines.size(); i++) { // Start from index 1 to skip header line
                String line = employeeLines.get(i);
                String[] row = line.split("#");

                if (row[0].equals(String.valueOf(ID))) {;
                    for (int j = 0; j < row.length; j++) {
                        userData.put(headerLine[j], row[j]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userData;
    }

    // Method to delete the row of data file based on user_ID (set to inactive instead of deleting)
    public void deleteEmployeeData(String user_ID) {
        try {
            List<String> employeeLines = Files.readAllLines(employeeDataPath);
            List<String> usersLines = Files.readAllLines(usersDataPath);

            // set user status to inactive in employee.txt
            for (int i = 1; i < employeeLines.size(); i++) { // Start from index 1 to skip header line
                String line = employeeLines.get(i);
                String[] row = line.split("#");
                if (row[0].equals(String.valueOf(user_ID))) {
                    row[15] = "deleted";

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

            // set user status to inactive in users.txt
            for (int i = 1; i < usersLines.size(); i++) { // Start from index 1 to skip header line
                String line = usersLines.get(i);
                String[] row = line.split("#");
                if (row[0].equals(String.valueOf(user_ID))) {
                    // Update username
                    row[3] = "deleted";

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
