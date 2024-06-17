import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEmployeeModal {
    private String grossPay, name, department, designation, username;
    Stage window;
    DashboardPage dashboardPage = new DashboardPage();

    void showAddModal(Stage stage) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Employee Details");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10, 30, 10, 30));

        Label titleLabel = new Label("Add An Employee");
        VBox addFields = showTextFields(stage);


        detailsLayout.setAlignment(Pos.TOP_LEFT);
        detailsLayout.getChildren().addAll(titleLabel, addFields);
 
        Scene detailsScene = new Scene(detailsLayout, 600, 380);
        Image icon = new Image("images/logo-icon.png");
        detailsScene.getStylesheets().add("css/modal.css");
        window.getIcons().add(icon);
        window.setScene(detailsScene);
        window.setResizable(false);
        window.showAndWait();
    }

    private VBox showTextFields(Stage stage){
        VBox addFields = new VBox(10);
        VBox.setMargin(addFields, new Insets(10,0,5,0));

        HBox userLoginDetails = new HBox(5);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        userLoginDetails.getChildren().addAll(usernameField, passwordField);
        
        TextField nameField = new TextField();
        nameField.getStyleClass().add("long-Field");
        nameField.setPromptText("Full Name");
        
        TextField departmentField = new TextField();
        departmentField.getStyleClass().add("long-Field");
        departmentField.setPromptText("Department");

        TextField designationField = new TextField();
        designationField.getStyleClass().add("long-Field");
        designationField.setPromptText("Designation");

        TextField pay_hourField = new TextField();
        pay_hourField.getStyleClass().add("long-Field");
        pay_hourField.setPromptText("Pay per hour");

        Button createButton = new Button("Create Employee");
        createButton.getStyleClass().add("create-button");
        createButton.setOnAction(e -> createEmployee(usernameField.getText(), passwordField.getText(), nameField.getText(), departmentField.getText(), designationField.getText(), pay_hourField.getText(), stage));

        addFields.getChildren().addAll(userLoginDetails, nameField, departmentField, designationField, pay_hourField, createButton);
        return addFields;
    }

    private void createEmployee(String usernameField, String passwordField, String nameField, String departmentField, String designationField, String pay_hourField, Stage stage){
        // Check if any field is empty
        if(usernameField.isEmpty() || passwordField.isEmpty() || nameField.isEmpty() || departmentField.isEmpty() || designationField.isEmpty() || pay_hourField.isEmpty()){
            // Show alert using JavaFX
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill all the fields.");

            alert.showAndWait();
            return; // Exit the method if any field is empty
        }

        // Find the Highest ID then add 1 and set as new ID to the new employee
        int newID = 0;
        Path usersDataPath = Paths.get("data/employee.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;
            int employeeID;

            // Skip the first line/header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split("#");
                employeeID = Integer.parseInt(employeeDetails[0]);

                if(employeeID > newID){
                    newID = employeeID;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        newID++;
        //[0]Id, [1]name, [2]department, [3]designation, [4]Birth Date, [5]Hire Date, [6]Address, [7],Phone Number, [8]Pay/Hour, [9]Total Hours Worked, [10]Total Overtime, [11]Gross Deductions, [12]GrossPay, [13]Check-In, [14]Check-Out
        String newName = " ", newDeparment= " ", newDesignation = " ", newBirthDate = " ", newHireDate = " ", newAddress = " ", newPhoneNumber = " ", newPayHour = " ", newTotalHoursWorked = " ", newTotalOvertime = " ", newGrossDeductions = " ", newGrossPay = " ", newCheckIn = " ", newCheckOut = " ";

        newName = nameField;
        newDeparment = departmentField;
        newDesignation = designationField;
        newPayHour = pay_hourField;

        String addEmployeeLine = newID+"#"+newName+"#"+newDeparment+"#"+newDesignation+"#"+newBirthDate+"#"+newHireDate+"#"+newAddress+"#"+newPhoneNumber+"#"+newPayHour+"#"+newTotalHoursWorked+"#"+newTotalOvertime+"#"+newGrossDeductions+"#"+newGrossPay+"#"+newCheckIn+"#"+newCheckOut;

        SHA256HashGenerator hash = new SHA256HashGenerator();

        String hashString = hash.generateSHA256Hash(passwordField);
        String addUserLine = usernameField+"#"+hashString+"#"+newID;

        try {
            FileWriter addEmployee = new FileWriter("data/employee.txt", true); // Set true for append mode
            addEmployee.write(addEmployeeLine + System.lineSeparator()); // Append new line separator to create a new line
            addEmployee.close();

            FileWriter addUser = new FileWriter("data/users.txt", true); // Set true for append mode
            addUser.write(addUserLine + System.lineSeparator()); // Append new line separator to create a new line
            addUser.close();

            window.close();
            dashboardPage.showDashboard(stage);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
        
        
    }
    
}
