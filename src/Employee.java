public class Employee {
    // this is for design only, ipang change or add ra mga
    private int ID, pay_per_hour, total_hours_work, total_overtime, gross_pay;
    private String name, department, designation, check_in, check_out;

    public Employee(){
        this.ID = 1;
        this.name = "";
        this.department = "";
        this.designation = "";
        this.check_in = "";
        this.check_out = "";
        this.pay_per_hour = 0;
        this.total_hours_work = 0;
        this.total_overtime = 0;
        this.gross_pay = 0;
    }
    public Employee(int ID, String name, String department, String designation, String check_in, String check_out){
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.check_in = check_in;
        this.check_out = check_out;
    }

    public Employee(int ID, String name, int pay_per_hour, int total_hours_work, int total_overtime, int gross_pay){
        this.ID = ID;
        this.name = name;
        this.pay_per_hour = pay_per_hour;
        this.total_hours_work = total_hours_work;
        this.total_overtime = total_overtime;
        this.gross_pay = gross_pay;
    }

    public int getID(){
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

    
    public String getCheck_in(){
        return check_in;
    }
    public void setCheck_in(String check_in){
        this.check_in = check_in;
    }

    public String getCheck_out(){
        return check_out;
    }
    public void setCheck_out(String check_out){
        this.check_out = check_out;
    }

    public int getPay_per_hour(){
        return pay_per_hour;
    }
    public void setPay_per_hour(int pay_per_hour){
        this.pay_per_hour = pay_per_hour;
    }

    public int getTotal_hours_work(){
        return total_hours_work;
    }
    public void setTotal_hours_work(int total_hours_work){
        this.total_hours_work = total_hours_work;
    }

    public int getTotal_overtime(){
        return total_hours_work;
    }
    public void setTotal_overtime(int total_overtime){
        this.total_overtime = total_overtime;
    }

    public int getGross_pay(){
        return gross_pay;
    }
    public void setGross_pay(int gross_pay){
        this.gross_pay = gross_pay;
    }
}
