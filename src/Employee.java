public class Employee {
    // this is for design only, ipang change or add ra mga
    private int ID;
    private String name, department, designation, check_in, check_out;

    public Employee(){
        this.ID = 1;
        this.name = "";
        this.department = "";
        this.designation = "";
        this.check_in = "";
        this.check_out = "";
    }
    public Employee(int ID, String name, String department, String designation, String check_in, String check_out){
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.check_in = check_in;
        this.check_out = check_out;
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
}
