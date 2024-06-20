public class EmployeeTimesheets {
    // this is for design only, ipang change or add ra mga
    private String date, timeInAM, timeOutAM, timeInPM, timeOutPM;

    public EmployeeTimesheets(String date, String timeInAM, String timeOutAM, String timeInPM, String timeOutPM) {
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
}
