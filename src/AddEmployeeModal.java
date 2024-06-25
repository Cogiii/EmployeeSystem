
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEmployeeModal {
    DashboardPage dashboardPage = new DashboardPage();
    Data data = new Data();

    Stage window;
    String userID; 
    HashMap<String, String> userData = new HashMap<>();


    void showAddModal(Stage stage, String ID) {
        userID = ID;
        userData = data.getUserData(ID);

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Employee Details");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10, 20, 10, 20));

        Label titleLabel = new Label("Register Employee");
        titleLabel.getStyleClass().add("label-header");
        VBox content = showContent();

        detailsLayout.setAlignment(Pos.TOP_LEFT);
        detailsLayout.getChildren().addAll(titleLabel, content);
 
        Scene detailsScene = new Scene(detailsLayout, 650, 450);
        Image icon = new Image("images/logo-icon.png");
        detailsScene.getStylesheets().add("css/modal.css");
        window.getIcons().add(icon);
        window.setScene(detailsScene);
        window.setResizable(false);
        window.showAndWait();
    }

    private VBox showContent(){
        VBox content = new VBox(5);
        HBox firstLine = firstLine();
        HBox secondLine = secondLine();
        HBox thirdLine = thirdLine();
        HBox fourthLine = fourthLine();
        HBox fifthLine = fifthLine();
        HBox sixthLine = sixthLine();

        HBox buttonLayout = new HBox();
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("create-button");
        buttonLayout.getChildren().add(submitButton);
        


        content.getChildren().addAll(firstLine, secondLine, thirdLine, fourthLine, fifthLine, sixthLine, buttonLayout);

        return content;
    }

    private HBox firstLine(){
        HBox firstLine = new HBox(5);

        VBox usernameBox = new VBox(5);

        Label username = new Label("Username: ");
        TextField usernameField = new TextField();

        usernameBox.getChildren().addAll(username, usernameField);
        firstLine.getChildren().addAll(usernameBox);
        return firstLine;
    }
    
    private HBox secondLine(){
        HBox secondLine = new HBox(5);

        VBox passwordLayout = new VBox();
        Label password = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        passwordLayout.getChildren().addAll(password, passwordField);

        VBox confirmPasswordLayout = new VBox();
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        PasswordField confirmPassword = new PasswordField();

        confirmPasswordLayout.getChildren().addAll(confirmPasswordLabel, confirmPassword);

        secondLine.getChildren().addAll(passwordLayout, confirmPasswordLayout);

        return secondLine;
    }
    
    private HBox thirdLine(){
        HBox thirdLine = new HBox(5);

        VBox fullNameLayout = new VBox();
        Label fullNameLabel = new Label("Full Name: ");
        TextField fullNameField = new TextField();
        fullNameLayout.getChildren().addAll(fullNameLabel, fullNameField);

        VBox genderLayout = new VBox();
        Label genderLabel = new Label("Gender: ");
        ComboBox gender = new ComboBox();
        genderLayout.getChildren().addAll(genderLabel, gender);

        VBox birthDateBox = new VBox();
        Label birthDateLabel = new Label("Birth Date: ");
        DatePicker birthDate = new DatePicker();
        birthDateBox.getChildren().addAll(birthDateLabel, birthDate);

        thirdLine.getChildren().addAll(fullNameLayout, genderLayout, birthDateBox);

        return thirdLine;
    }

    private HBox fourthLine(){
        HBox fourthLine = new HBox(5);

        VBox emailLayout = new VBox();
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();
        emailLayout.getChildren().addAll(emailLabel, emailField);

        VBox phoneNumberLayout = new VBox();
        Label phoneNumberLabel = new Label("Phone Number: ");
        TextField phoneNumberField = new TextField();
        phoneNumberLayout.getChildren().addAll(phoneNumberLabel, phoneNumberField);

        fourthLine.getChildren().addAll(emailLayout, phoneNumberLayout);

        return fourthLine;
    }

    private HBox fifthLine(){
        HBox fifthLine = new HBox(5);

        VBox departmentLayout = new VBox();
        Label departmentLabel = new Label("Department: ");
        ComboBox department = new ComboBox();
        departmentLayout.getChildren().addAll(departmentLabel, department);

        VBox designationLayout = new VBox();
        Label designationLabel = new Label("Designation: ");
        ComboBox designation = new ComboBox();
        designationLayout.getChildren().addAll(designationLabel, designation);

        fifthLine.getChildren().addAll(departmentLayout, designationLayout);

        return fifthLine;
    }

    private HBox sixthLine(){
        HBox sixthLine = new HBox(5);

        VBox payPerHourLayout = new VBox();
        Label payPerHourLabel = new Label("Pay per hour: ");
        TextField payPerHourField = new TextField();
        payPerHourLayout.getChildren().addAll(payPerHourLabel, payPerHourField);

        VBox pictureLayout = new VBox();
        Label pictureLabel = new Label("Picture: ");
        Button uploadPhoto = new Button("Upload Photo");
        pictureLayout.getChildren().addAll(pictureLabel, uploadPhoto);

        sixthLine.getChildren().addAll(payPerHourLayout, pictureLayout);

        return sixthLine;
    }

    


    private VBox leftContent(){
        VBox leftContent = new VBox(5);
        leftContent.setAlignment(Pos.TOP_LEFT);

        //---------------------IMAGE-----------------------------
        Image originalImage = new Image("images/userImage/hannipham.jpg");

        // Calculate dimensions for the square
        double squareSize = Math.min(originalImage.getWidth(), originalImage.getHeight());
        double startX = (originalImage.getWidth() - squareSize) / 2;
        double startY = (originalImage.getHeight() - squareSize) / 2;

        // Create a viewport to crop the original image to square
        Rectangle2D viewportRect = new Rectangle2D(startX, startY, squareSize, squareSize);

        ImageView userPicture = new ImageView(originalImage);
        userPicture.setViewport(viewportRect);
        userPicture.setFitWidth(150);
        userPicture.setFitHeight(150);
        userPicture.setPreserveRatio(false);

        // Create a Rectangle with rounded corners (as a clipping mask)
        Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(30); // Adjust the arc width as needed
        clip.setArcHeight(30); // Adjust the arc height as needed

        // Apply clipping to the ImageView
        userPicture.setClip(clip);

        // Create a StackPane and add the Rectangle and ImageView
        StackPane imagePane = new StackPane();
        imagePane.getChildren().addAll(userPicture);
        imagePane.setAlignment(Pos.TOP_LEFT);
        //--------------------IMAGE END---------------------------------

        Label header2 = new Label("Personal Details: ");

        
        
        
        
        
        
        

        Button registerButton = new Button("Register");



        return leftContent;
    }

    private VBox rightContent(){
        VBox rightContent = new VBox(5);

        return rightContent;
    }

    private void createEmployee(String usernameField, String passwordField, String nameField, String departmentField, String designationField, String payPerDayField, Stage stage){
        // Check if any field is empty
        if(usernameField.isEmpty() || passwordField.isEmpty() || nameField.isEmpty() || departmentField.isEmpty() || designationField.isEmpty() || payPerDayField.isEmpty()){
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
        //[0]Id, [1]name, [2]department, [3]designation, [4]Birth Date, [5]Hire Date, [6]Address, [7],Phone Number, [8]Pay/Day, [9]Total Hours Worked, [10]Total Overtime, [11]Gross Deductions, [12]GrossPay, [13]Check-In, [14]Check-Out
        String newName = "--", newDeparment= "--", newDesignation = "--", newBirthDate = "--", newHireDate = "--", newAddress = "--", newPhoneNumber = "--", newPayPerDay = "--", newTotalHoursWorked = "--", newTotalOvertime = "--", newGrossDeductions = "--", newGrossPay = "--", newCheckIn = "--", newCheckOut = "--", newStatus = "--";

        newName = nameField;
        newDeparment = departmentField;
        newDesignation = designationField;
        newPayPerDay = payPerDayField;
        newStatus = "employee";

        String addEmployeeLine = newID+"#"+newName+"#"+newDeparment+"#"+newDesignation+"#"+newBirthDate+"#"+newHireDate+"#"+newAddress+"#"+newPhoneNumber+"#"+newPayPerDay+"#"+newTotalHoursWorked+"#"+newTotalOvertime+"#"+newGrossDeductions+"#"+newGrossPay+"#"+newCheckIn+"#"+newCheckOut+"#"+newStatus;

        SHA256HashGenerator hash = new SHA256HashGenerator();

        String hashString = hash.generateSHA256Hash(passwordField);
        String addUserLine = newID+"#"+usernameField+"#"+hashString+"#"+newStatus;

        try {
            FileWriter addEmployee = new FileWriter("data/employee.txt", true); // Set true for append mode
            addEmployee.write(addEmployeeLine + System.lineSeparator()); // Append new line separator to create a new line
            addEmployee.close();

            FileWriter addUser = new FileWriter("data/users.txt", true); // Set true for append mode
            addUser.write(addUserLine + System.lineSeparator()); // Append new line separator to create a new line
            addUser.close();

            window.close();
            dashboardPage.showDashboard(stage, userID);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
        
        
    }
    
}
