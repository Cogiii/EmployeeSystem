import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Payroll {

    private DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    private Path employeePath = Paths.get("data/employee.txt");
    private Path timesheetsPath = Paths.get("data/timesheets.txt");

    public void updatePayroll() {
        HashMap<String, String> userData = new HashMap<>();
        List<String> updatedLines = new ArrayList<>();

        try {
            List<String> employeeLines = Files.readAllLines(employeePath);
            List<String> timesheetsLines = Files.readAllLines(timesheetsPath);
            
            updatedLines.add(employeeLines.get(0));

            String[] header = employeeLines.get(0).split("#");

            // Get today's date in the format used in timesheets.txt
            LocalDate today = LocalDate.now();
            String todayString = today.format(DATE_FORMATTER);

            for (int i = 1; i < employeeLines.size(); i++) {
                String[] parts = employeeLines.get(i).split("#");

                // Load data into userData HashMap
                for (int j = 0; j < parts.length; j++) {
                    userData.put(header[j], parts[j]);
                }

                // Find corresponding timesheet entry for today
                String employeeID = parts[0];
                String timeIn = "--";
                String timeOut = "--";

                for (String timesheetLine : timesheetsLines) {
                    String[] timesheetParts = timesheetLine.split("#");
                    if (timesheetParts[0].equals(employeeID) && timesheetParts[1].equals(todayString)) {
                        if (!timesheetParts[4].equals("--"))
                            timeIn = timesheetParts[4]; // Assuming timeInAM from timesheets.txt
                        else if(!timesheetParts[2].equals("--"))
                            timeIn = timesheetParts[2]; // Assuming timeInAM from timesheets.txt
                        
                        if (!timesheetParts[5].equals("--"))
                            timeOut = timesheetParts[5]; // Assuming timeOutPM from timesheets.txt
                        else if (!timesheetParts[3].equals("--"))
                            timeOut = timesheetParts[3]; // Assuming timeOutPM from timesheets.txt
                            
                        break;
                    }
                }

                // Append timeIn and timeOut to parts array
                parts[indexOf(header, "timeIn")] = timeIn;
                parts[indexOf(header, "timeOut")] = timeOut;

                // Calculate necessary fields (assuming this logic is correct based on previous implementation)

                double payPerDay = Double.parseDouble(userData.get("payPerDay"));
                int hoursWorked = Integer.parseInt(userData.get("hoursWorked"));
                int totalOvertime = Integer.parseInt(userData.get("totalOvertime"));
                int lates = Integer.parseInt(userData.get("lates"));

                double initialPay = ((hoursWorked / 8) * payPerDay);
                double overtimePay = (totalOvertime) * (initialPay * 0.05);
                double deductions = (lates) * (initialPay * 0.01);
                double grossPay = initialPay - deductions + overtimePay;

                parts[indexOf(header, "deductions")] = String.format("%.2f", deductions);
                parts[indexOf(header, "grossPay")] = String.format("%.2f", grossPay);

                updatedLines.add(String.join("#", parts));
            }

            Files.write(employeePath, updatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int indexOf(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}
