public class Employee {
    // this is for design only, ipang change or add ra mga
    private String ID, name, department, designation, timeIn, timeOut, payPerDay, hoursWorked, totalOvertime, lates, deductions, grossPay;

    public Employee(String ID, String name, String department, String designation, String timeIn, String timeOut) {
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public Employee(String ID, String name, String payPerDay, String hoursWorked, String totalOvertime, String lates, String deductions, String grossPay) {
        this.ID = ID;
        this.name = name;
        this.payPerDay = payPerDay;
        this.hoursWorked = hoursWorked;
        this.totalOvertime = totalOvertime;
        this.lates = lates;
        this.deductions = deductions;
        this.grossPay = grossPay;
    }

    public String getID(){
        return ID;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }

    public String getDesignation(){
        return designation;
    }
    public void setDesignation(String designation){
        this.designation = designation;
    }

    
    public String getTimeIn(){
        return timeIn;
    }
    public void setTimeIn(String timeIn){
        this.timeIn = timeIn;
    }

    public String getTimeOut(){
        return timeOut;
    }
    public void setTimeOut(String timeOut){
        this.timeOut = timeOut;
    }

    public String getPayPerDay(){
        return payPerDay;
    }
    public void setPayPerDay(String payPerDay){
        this.payPerDay = payPerDay;
    }

    public String getHoursWorked(){
        return hoursWorked;
    }
    public void setHoursWorked(String hoursWorked){
        this.hoursWorked = hoursWorked;
    }

    public String getTotalOvertime(){
        return totalOvertime;
    }
    public void setTotalOvertime(String totalOvertime){
        this.totalOvertime = totalOvertime;
    }

    public String getLates(){
        return lates;
    }
    public void setLates(String lates){
        this.lates = lates;
    }

    public String getDeductions(){
        return deductions;
    }
    public void setDeductions(String deductions){
        this.deductions = deductions;
    }

    

    public String getGrossPay(){
        return grossPay;
    }
    public void setGrossPay(String grossPay){
        this.grossPay = grossPay;
    }
}
