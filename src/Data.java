import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
            List<String> usersLines = Files.readAllLines(usersDataPath);

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

            for (String users : usersLines) {
                String[] user = users.split("#");

                if (user[0].equals(ID)){
                    userData.put("username", user[1]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userData;
    }

    // Method to delete the row of data file based on user_ID (set to inactive instead of deleting)
    public void deleteEmployeeData(String userID) {
        List<String> employeeUpdatedLines = new ArrayList<>();
        List<String> usersUpdatedLines = new ArrayList<>();

        HashMap<String, String> employeeData = new HashMap<>();
        HashMap<String, String> usersData = new HashMap<>();

        try {
            List<String> employeeLines = Files.readAllLines(employeeDataPath);
            List<String> usersLines = Files.readAllLines(usersDataPath);

            String[] employeeHeader = employeeLines.get(0).split("#");
            String[] usersHeader = usersLines.get(0).split("#");

            employeeUpdatedLines.add(employeeLines.get(0));
            usersUpdatedLines.add(usersLines.get(0));

            int index = 0;
            for (int i = 1; i < employeeLines.size(); i++) {
                String employee = employeeLines.get(i);
                String[] employeeParts = employee.split("#");

                for (int k = 0; k < employeeParts.length; k++) {
                    employeeData.put(employeeHeader[k], employeeParts[k]);
                    if (employeeHeader[k].equals("status"))
                        index = k;
                }

                if (employeeData.get("ID").equals(userID))
                    employeeParts[index] = "deleted";

                employeeUpdatedLines.add(String.join("#", employeeParts));
            }            

            for (int i = 1; i < usersLines.size(); i++) {
                String user = usersLines.get(i);
                String[] usersParts = user.split("#");

                for (int k = 0; k < usersParts.length; k++) {
                    usersData.put(usersHeader[k], usersParts[k]);
                    if (usersHeader[k].equals("status"))
                        index = k;
                }

                if (usersData.get("ID").equals(userID))
                    usersParts[index] = "deleted";

                usersUpdatedLines.add(String.join("#", usersParts));
            
            }

            Files.write(employeeDataPath, employeeUpdatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(usersDataPath, usersUpdatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Employee details updated successfully.");

        } catch (IOException e) {
            System.err.println("Error updating employee data: " + e.getMessage());
        }
    }

    public void updateEmployeeData(String userID, String name, String department, String designation, String gender, String birthDate, String hireDate, String email, String address, String phoneNumber) {
        List<String> employeeUpdatedLines = new ArrayList<>();
        List<String> usersUpdatedLines = new ArrayList<>();

        HashMap<String, String> employeeData = new HashMap<>();
        HashMap<String, String> usersData = new HashMap<>();

        try {
            List<String> employeeLines = Files.readAllLines(employeeDataPath);
            List<String> usersLines = Files.readAllLines(usersDataPath);

            String[] employeeHeader = employeeLines.get(0).split("#");
            String[] usersHeader = usersLines.get(0).split("#");

            employeeUpdatedLines.add(employeeLines.get(0));
            usersUpdatedLines.add(usersLines.get(0));

            int nameIndex = 0;
            int departmentIndex = 0;
            int designationIndex = 0;
            int genderIndex = 0;
            int birthDateInex = 0;
            int hireDateIndex = 0;
            int emailIndex = 0;
            int addressIndex = 0;
            int phoneNumberIndex = 0;
            for (int i = 1; i < employeeLines.size(); i++) {
                String employee = employeeLines.get(i);
                String[] employeeParts = employee.split("#");

                for (int k = 0; k < employeeParts.length; k++) {
                    employeeData.put(employeeHeader[k], employeeParts[k]);
                    if (employeeHeader[k].equals("name"))
                        nameIndex = 0;
                    else if(employeeHeader[k].equals("name")){

                    }
                        
                }

                if (employeeData.get("ID").equals(userID)){

                }
                    

                employeeUpdatedLines.add(String.join("#", employeeParts));
            }            

            for (int i = 1; i < usersLines.size(); i++) {
                String user = usersLines.get(i);
                String[] usersParts = user.split("#");

                for (int k = 0; k < usersParts.length; k++) {
                    usersData.put(usersHeader[k], usersParts[k]);
                    if (usersHeader[k].equals("status")){
                        
                    }
                        
                }

                if (usersData.get("ID").equals(userID)){

                }
                    

                usersUpdatedLines.add(String.join("#", usersParts));
            
            }

            Files.write(employeeDataPath, employeeUpdatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(usersDataPath, usersUpdatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Employee details updated successfully.");

        } catch (IOException e) {
            System.err.println("Error updating employee data: " + e.getMessage());
        }
    }

    

}
