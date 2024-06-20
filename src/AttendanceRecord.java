import java.util.*;

// Class representing an attendance record
class AttendanceRecord {
    private String date, timeInAM, timeOutAM, timeInPM, timeOutPM;

    public AttendanceRecord(String date, String timeInAM, String timeOutAM, String timeInPM, String timeOutPM) {
        this.date = date;
        this.timeInAM = timeInAM;
        this.timeOutAM = timeOutAM;
        this.timeInPM = timeInPM;
        this.timeOutPM = timeOutPM;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeInAM() {
        return timeInAM;
    }

    public void setTimeInAM(String timeInAM) {
        this.timeInAM = timeInAM;
    }

    public String getTimeOutAM() {
        return timeOutAM;
    }

    public void setTimeOutAM(String timeOutAM) {
        this.timeOutAM = timeOutAM;
    }

    public String getTimeInPM() {
        return timeInPM;
    }

    public void setTimeInPM(String timeInPM) {
        this.timeInPM = timeInPM;
    }

    public String getTimeOutPM() {
        return timeOutPM;
    }

    public void setTimeOutPM(String timeOutPM) {
        this.timeOutPM = timeOutPM;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Time In: " + timeInAM + ", Time Out: " + timeOutAM;
    }
}

// Class representing a user with attendance records
class User {
    private String username;
    private String fullName;
    private String position;
    private String location;
    private List<AttendanceRecord> attendanceRecords;

    public User(String username, String fullName, String position, String location) {
        this.username = username;
        this.fullName = fullName;
        this.position = position;
        this.location = location;
        this.attendanceRecords = new ArrayList<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getLocation() {
        return location;
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }

    // Method to add attendance record
    public void addAttendanceRecord(AttendanceRecord record) {
        attendanceRecords.add(record);
    }

    // Method to return user data as HashMap
    public Map<String, Object> getUserDataAsMap() {
        Map<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("Username", username);
        userDataMap.put("Full Name", fullName);
        userDataMap.put("Position", position);
        userDataMap.put("Location", location);

        List<Map<String, String>> attendanceList = new ArrayList<>();
        for (AttendanceRecord record : attendanceRecords) {
            Map<String, String> attendanceMap = new HashMap<>();
            attendanceMap.put("Date", record.getDate());
            attendanceMap.put("Time In", record.getTimeInAM());
            attendanceMap.put("Time Out", record.getTimeOutAM());
            attendanceList.add(attendanceMap);
        }
        userDataMap.put("Attendance Records", attendanceList);

        return userDataMap;
    }
}


// // Example usage in a main class
// public class UserDataManager {
//     private static Map<String, User> userMap = new HashMap<>(); // Map to store users by username

//     public static void main(String[] args) {
//         // Example: Create users
//         User johndoe = new User("johndoe", "John Doe", "Senior Consultant", "New York");
//         johndoe.addAttendanceRecord(new AttendanceRecord("2024-06-17", "09:00", "17:00"));
//         johndoe.addAttendanceRecord(new AttendanceRecord("2024-06-18", "09:15", "17:30"));
//         userMap.put(johndoe.getUsername(), johndoe);

//         User janedoe = new User("janedoe", "Jane Doe", "Junior Developer", "San Francisco");
//         janedoe.addAttendanceRecord(new AttendanceRecord("2024-06-17", "08:45", "16:45"));
//         janedoe.addAttendanceRecord(new AttendanceRecord("2024-06-18", "09:00", "17:15"));
//         userMap.put(janedoe.getUsername(), janedoe);

//         // Example: Get user data for "johndoe"
//         String username = "johndoe";
//         if (userMap.containsKey(username)) {
//             User loggedInUser = userMap.get(username);
//             Map<String, Object> userDataMap = loggedInUser.getUserDataAsMap();
//             printUserData(userDataMap);
//         } else {
//             System.out.println("User not found.");
//         }
//     }

//     // Method to print user data from HashMap
//     private static void printUserData(Map<String, Object> userDataMap) {
//         userDataMap.forEach((key, value) -> {
//             if (value instanceof List) {
//                 System.out.println(key + ": ");
//                 List<Map<String, String>> records = (List<Map<String, String>>) value;
//                 for (Map<String, String> record : records) {
//                     record.forEach((k, v) -> {
//                         System.out.println("  " + k + ": " + v);
//                     });
//                 }
//             } else {
//                 System.out.println(key + ": " + value);
//             }
//         });
//     }
// }
