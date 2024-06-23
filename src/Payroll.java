import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Payroll {
    
    Path employeePath = Paths.get("data/employee.txt");

    public void updatePayroll(){
        HashMap<String, String> userData = new HashMap<>();

        try {
            List<String> employeeLines = Files.readAllLines(employeePath);

            String[] header = employeeLines.get(0).split("#");

            for (int i = 1; i < employeeLines.size(); i++) {
                String[] parts = employeeLines.get(i).split("#");

                for (int j = 0; j < parts.length; j++) {
                    userData.put(header[j], parts[j]);
                }

                String userID = userData.get("ID");
                double payPerDay = Integer.parseInt(userData.get("payPerDay"));
                int hoursWorked = Integer.parseInt(userData.get("hoursWorked"));
                int totalOvertime = Integer.parseInt(userData.get("totalOvertime"));
                int lates = Integer.parseInt(userData.get("lates"));

                double grossPay = ((hoursWorked/8)*payPerDay) + (totalOvertime*0.02);

            }

        } catch (Exception e) {
  
        }
    }
}
