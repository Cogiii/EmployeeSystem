public class EmployeeTimesheets {
    // this is for design only, ipang change or add ra mga
    private String date, check_in_AM,  check_out_AM, check_in_PM, check_out_PM;

    public EmployeeTimesheets(){
        this.date = "";
        this.check_in_AM = "";
        this.check_in_AM = "";
        this.check_out_PM = "";
        this.check_out_PM = "";
    }
    public EmployeeTimesheets(String date, String check_in_AM, String check_out_AM, String check_in_PM, String check_out_PM){
        this.date = date;
        this.check_in_AM = check_in_AM;
        this.check_out_AM = check_out_AM;
        this.check_in_PM = check_in_PM;
        this.check_out_PM = check_out_PM;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getCheck_in_AM(){
        return check_in_AM;
    }
    public void setCheck_in_AM(String check_in_AM){
        this.check_in_AM = check_in_AM;
    }

    public String getCheck_out_AM(){
        return check_out_AM;
    }
    public void setCheck_out_AM(String check_out_AM){
        this.check_out_AM = check_out_AM;
    }

    public String getCheck_in_PM(){
        return check_in_PM;
    }
    public void setCheck_in_PM(String check_in_PM){
        this.check_in_PM = check_in_PM;
    }

    public String getCheck_out_PM(){
        return check_out_PM;
    }
    public void setCheck_out_PM(String check_out_PM){
        this.check_out_PM = check_out_PM;
    }
}
