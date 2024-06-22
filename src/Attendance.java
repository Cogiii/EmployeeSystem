import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Attendance {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    String currentDate = date.format(dateFormatter);
    String currentTime = time.format(timeFormatter);

    Path timesheetsPath = Paths.get("data/timesheets.txt");

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
                            if(time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))){
                                if (!parts[2].equals("--") ) {
                                    // User has already clocked in, return early
                                    return;
                                }
        
                                parts[2] = currentTime; // Set time in for morning
                            } else if (time.isAfter(LocalTime.of(12, 30)) && time.isBefore(LocalTime.of(16, 0))) {
                                if (!parts[4].equals("--")){
                                    // User has already clocked in, return early
                                    return; 
                                }
        
                                parts[4] = currentTime; // Set time in for afternoon
                            }
                            break;
                        case "time Out": 
                            // Check the time if they can log out and check if they time in (cannot time out if did not time in)
                            if(time.isAfter(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(12, 30)) && !parts[2].equals("--")){
                                if (!parts[3].equals("--")) {
                                    // User has already clocked in, return early
                                    return;
                                }
        
                                parts[3] = currentTime; // Set time in for morning
                            } else if (time.isAfter(LocalTime.of(16, 0)) && !parts[4].equals("--")) {
                                if (!parts[5].equals("--")){
                                    // User has already clocked in, return early
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
                        if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))) {
                            newEntry = userID + "#" + currentDate + "#" + currentTime + "#--#--#--";
                        } else if (time.isAfter(LocalTime.of(12, 30)) && time.isBefore(LocalTime.of(16, 0))) {
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
}
