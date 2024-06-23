import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");
    DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    String currentDate = date.format(dateFormatter);
    String currentTime = time.format(timeFormatter);
    String currentMonth = date.format(monthFormatter);

    Path timesheetsPath = Paths.get("data/timesheets.txt");
    Path employeePath = Paths.get("data/employee.txt");

    public void record(String userID, String timeLog) {
        List<String> fileLines = new ArrayList<>();
        boolean userFound = false;
        boolean timeUpdated = false;

        try {
            List<String> employeeTimesheetsLines = Files.readAllLines(timesheetsPath);

            // Read file and check if the user has already clocked in
            for (int index = 0; index < employeeTimesheetsLines.size(); index++) {
                String line = employeeTimesheetsLines.get(index);
                String[] parts = line.split("#");

                if (parts[0].equals(userID) && parts[1].equals(currentDate)){
                    userFound = true;

                    switch (timeLog) {
                        case "time in":
                            // Check time in for morning (Valid for 6AM to 12PM) or afternoon (Valid for 12:30 to 4PM)
                            if(time.isAfter(LocalTime.of(5, 59)) && time.isBefore(LocalTime.of(12, 0))){
                                if (!parts[2].equals("--") ) {
                                    // User has already clocked in, return early
                                    return;
                                }
        
                                parts[2] = currentTime; // Set time in for morning
                            } else if (time.isAfter(LocalTime.of(12, 29)) && time.isBefore(LocalTime.of(16, 0))) {
                                if (!parts[4].equals("--")){
                                    // User has already clocked in, return early
                                    return; 
                                }
        
                                parts[4] = currentTime; // Set time in for afternoon
                            }
                            break;
                        case "time out": 
                            // Check the time if they can log out and check if they time in (cannot time out if did not time in)
                            // Check time out for morning (Valid for 12PM to 12:30PM) or afternoon (Valid for 4PM onwards)
                            if(time.isAfter(LocalTime.of(11, 59)) && time.isBefore(LocalTime.of(12, 30)) && !parts[2].equals("--")){
                                if (!parts[3].equals("--")) {
                                    // User has already clocked out, return early
                                    return;
                                }
        
                                parts[3] = currentTime; // Set time in for morning
                            } else if (time.isAfter(LocalTime.of(16, 0)) && !parts[4].equals("--")) {
                                if (!parts[5].equals("--")){
                                    // User has already clocked out, return early
                                    return; 
                                }
        
                                parts[5] = currentTime; // Set time in for afternoon
                            }
                            break;
                    }

                    // Update the line with new times
                    fileLines.add(String.join("#", parts));
                    timeUpdated = true;
                } else {
                    fileLines.add(line);
                }
            }            

            // If user entry not found for the current date, create a new entry
            if (!userFound) {
                String newEntry = userID + "#" + currentDate + "#--#--#--#--";

                // Only for time in because they cannot time out if they did not time in
                switch (timeLog) {
                    case "time in":
                        // Check time in for morning (Valid for 6AM to 12PM) or afternoon (Valid for 12:30 to 4PM)
                        if (time.isAfter(LocalTime.of(5, 59)) && time.isBefore(LocalTime.of(12, 0))) {
                            newEntry = userID + "#" + currentDate + "#" + currentTime + "#--#--#--";
                        } else if (time.isAfter(LocalTime.of(12, 29)) && time.isBefore(LocalTime.of(16, 0))) {
                            newEntry = userID + "#" + currentDate + "#--#--#" + currentTime + "#--";
                        }
                        break;
                }


                fileLines.add(newEntry);
                timeUpdated = true;
            }

            // Write back to the file if any changes were made
            if (timeUpdated) {
                Files.write(timesheetsPath, fileLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> lateCounts(){
        HashMap<String, Integer> lateCounts = new HashMap<>();
        HashMap<String, String> timesheetsData = new HashMap<>();
        
        try {
            List<String> timesheetsLines = Files.readAllLines(timesheetsPath);
            
            String[] timesheetsHeader = timesheetsLines.get(0).split("#");

            for (int i = 1; i < timesheetsLines.size(); i++) {
                String[] parts = timesheetsLines.get(i).split("#");

                for (int j = 0; j < parts.length; j++) {
                    timesheetsData.put(timesheetsHeader[j], parts[j]);
                }

                String userID = timesheetsData.get("ID");
                String dateStr = timesheetsData.get("date");
                String timeInMorning = timesheetsData.get("timeInAM");
                String timeInAfternoon = timesheetsData.get("timeInPM");

                // Count employee lates
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                String month = date.getMonth().toString();

                if (month.equalsIgnoreCase(currentMonth)) {
                    if (!timeInMorning.equals("--") && !timeInMorning.trim().isEmpty()){
                        LocalTime morningTime = LocalTime.parse(timeInMorning, timeFormatter);
    
                        if (morningTime.isAfter(LocalTime.of(8, 0))){
                            lateCounts.put(userID, lateCounts.getOrDefault(userID, 0) + 1);
                        }
                    }
                    if (!timeInAfternoon.equals("--") && !timeInAfternoon.trim().isEmpty()) {
                        LocalTime afternoonTime = LocalTime.parse(timeInAfternoon, timeFormatter);
    
                        if (afternoonTime.isAfter(LocalTime.of(13, 0))){
                            lateCounts.put(userID, lateCounts.getOrDefault(userID, 0) + 1);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lateCounts;
    }

    public HashMap<String, Map<String, Integer>> countHoursWorked(){
        HashMap<String, Map<String, Integer>> hoursWorked = new HashMap<>();
        HashMap<String, String> timesheetsData = new HashMap<>();
        
        try {
            List<String> timesheetsLines = Files.readAllLines(timesheetsPath);
            
            String[] timesheetsHeader = timesheetsLines.get(0).split("#");

            for (int i = 1; i < timesheetsLines.size(); i++) {
                String[] parts = timesheetsLines.get(i).split("#");

                for (int j = 0; j < parts.length; j++) {
                    timesheetsData.put(timesheetsHeader[j], parts[j]);
                }

                String userID = timesheetsData.get("ID");
                String dateStr = timesheetsData.get("date");
                String timeInMorning = timesheetsData.get("timeInAM");
                String timeInAfternoon = timesheetsData.get("timeInPM");
                String timeOutMorning = timesheetsData.get("timeOutAM");
                String timeOutAfternoon = timesheetsData.get("timeOutPM");

                // Count employee lates
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                String month = date.getMonth().toString();

                if (month.equalsIgnoreCase(currentMonth)) {
                    int totalWorkedHours=0, morningHours=0, afternoonHours=0;
                    
                    if (!timeInMorning.equals("--") && !timeOutMorning.equals("--") && !timeInMorning.trim().isEmpty() && !timeOutMorning.trim().isEmpty()){
                        LocalTime startTimeAM = LocalTime.parse(timeInMorning, timeFormatter);
                        LocalTime endTimeAM = LocalTime.parse(timeOutMorning, timeFormatter);

                        long hours = java.time.Duration.between(startTimeAM, endTimeAM).toHours();
                        morningHours = (int) hours;
                        totalWorkedHours += morningHours;
                    }
                    if (!timeInAfternoon.equals("--") && !timeOutAfternoon.equals("--") && !timeInAfternoon.trim().isEmpty() && !timeOutAfternoon.trim().isEmpty()) {
                        LocalTime startTimePM = LocalTime.parse(timeInAfternoon, timeFormatter);
                        LocalTime endTimePM = LocalTime.parse(timeOutAfternoon, timeFormatter);

                        long hours = java.time.Duration.between(startTimePM, endTimePM).toHours();
                        afternoonHours = (int) hours;
                        totalWorkedHours += afternoonHours;
                    }

                    int regularHours = Math.min(totalWorkedHours, 8);
                    int overtimeHours = Math.max(totalWorkedHours - 8, 0);

                    Map<String, Integer> workedHours = hoursWorked.getOrDefault(userID, new HashMap<>());
                    workedHours.put("regularHours", workedHours.getOrDefault("regularHours", 0) + regularHours);
                    workedHours.put("overtimeHours", workedHours.getOrDefault("overtimeHours", 0) + overtimeHours);

                    hoursWorked.put(userID, workedHours);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return hoursWorked;
    }

    public void updateEmployee() {
        Map<String, Integer> lateCounts = lateCounts();
        Map<String, Map<String, Integer>> countHoursWorked = countHoursWorked();
        HashMap<String, String> timesheetsData = new HashMap<>();

        try {
            List<String> employeeLines = Files.readAllLines(employeePath);
            List<String> updatedLines = new ArrayList<>();

            String[] employeeHeader = employeeLines.get(0).split("#");

            for (String line : employeeLines) {
                String[] parts = line.split("#");
                for (int i = 0; i < parts.length; i++) {
                    timesheetsData.put(employeeHeader[i], parts[i]);
                }

                String userID = timesheetsData.get("ID");

                if (lateCounts.containsKey(userID)) {
                    parts[indexOf(employeeHeader, "lates")] = String.valueOf(lateCounts.get(userID));
                }

                if (countHoursWorked.containsKey(userID)) {
                    Map<String, Integer> workedHours = countHoursWorked.get(userID);
                    parts[indexOf(employeeHeader, "hoursWorked")] = String.valueOf(workedHours.get("regularHours"));
                    parts[indexOf(employeeHeader, "totalOvertime")] = String.valueOf(workedHours.get("overtimeHours"));
                }

                updatedLines.add(String.join("#", parts));
            }

            Files.write(employeePath, updatedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
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
