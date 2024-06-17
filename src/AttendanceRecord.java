import java.util.*;

// Class representing an attendance record
class AttendanceRecord {
    private String date;
    private String timeIn;
    private String timeOut;

    public AttendanceRecord(String date, String timeIn, String timeOut) {
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Time In: " + timeIn + ", Time Out: " + timeOut;
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
            attendanceMap.put("Time In", record.getTimeIn());
            attendanceMap.put("Time Out", record.getTimeOut());
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
