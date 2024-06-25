
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEmployeeModal {
    DashboardPage dashboardPage = new DashboardPage();
    Data data = new Data();

    Stage window;
    String userID; 
    HashMap<String, String> userData = new HashMap<>();
    TextField usernameField, fullNameField, emailField, phoneNumberField, payPerHourField;
    PasswordField passwordField, confirmPassword;
    DatePicker birthDate = new DatePicker();
    ComboBox<String> departmentField = new ComboBox<String>();
    ComboBox<String> designationField = new ComboBox<String>();
    private String pictureDirectory;

    Button uploadPhoto = new Button("Upload Photo");

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
 
        Scene detailsScene = new Scene(detailsLayout, 580, 500);
        Image icon = new Image("images/logo-icon.png");
        detailsScene.getStylesheets().add("css/addModal.css");
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
        submitButton.setOnAction(e -> {
            createEmployee(usernameField.getText(), passwordField.getText(), fullNameField.getText(), departmentField.getValue().toString(), designationField.getValue().toString(), payPerHourField.getText(), window);
        });
        submitButton.getStyleClass().add("create-button");
        buttonLayout.getChildren().add(submitButton);
        
        content.getChildren().addAll(firstLine, secondLine, thirdLine, fourthLine, fifthLine, sixthLine, buttonLayout);

        return content;
    }

    private HBox firstLine(){
        HBox firstLine = new HBox(5);
        VBox usernameBox = new VBox(5);
        Label username = new Label("Username: ");
        usernameField = new TextField();
        usernameField.setPromptText("username");

        usernameBox.getChildren().addAll(username, usernameField);
        firstLine.getChildren().addAll(usernameBox);
        return firstLine;
    }
    
    private HBox secondLine(){
        HBox secondLine = new HBox(5);

        VBox passwordLayout = new VBox();
        Label password = new Label("Password: ");
        passwordField = new PasswordField();
        passwordField.setPromptText("password");
        
        passwordLayout.getChildren().addAll(password, passwordField);

        VBox confirmPasswordLayout = new VBox();
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPassword = new PasswordField();
        confirmPassword.setPromptText("confirm password");
        
        confirmPasswordLayout.getChildren().addAll(confirmPasswordLabel, confirmPassword);

        secondLine.getChildren().addAll(passwordLayout, confirmPasswordLayout);

        return secondLine;
    }
    
    private HBox thirdLine(){
        HBox thirdLine = new HBox(5);

        VBox fullNameLayout = new VBox();
        Label fullNameLabel = new Label("Full Name: ");
        fullNameField = new TextField();
        fullNameField.setPromptText("fullname");

        fullNameLayout.getChildren().addAll(fullNameLabel, fullNameField);

        VBox genderLayout = new VBox();
        ComboBox<String> gender = new ComboBox<String>();
        gender.setPromptText("gender");
        gender.getStyleClass().add("gender-field");

        gender.getItems().add("Male");
        gender.getItems().add("Female");
        gender.getItems().add("Prefer not to say");
        Label genderLabel = new Label("Gender: ");
        genderLayout.getChildren().addAll(genderLabel, gender);

        VBox birthDateBox = new VBox();
        Label birthDateLabel = new Label("Birth Date: ");
        birthDate.setPromptText("birth date");
        birthDate.getStyleClass().add("birthDate-field");

        birthDateBox.getChildren().addAll(birthDateLabel, birthDate);

        thirdLine.getChildren().addAll(fullNameLayout, genderLayout, birthDateBox);

        return thirdLine;
    }

    private HBox fourthLine(){
        HBox fourthLine = new HBox(5);

        VBox emailLayout = new VBox();
        Label emailLabel = new Label("Email: ");
        emailField = new TextField();
        emailField.setPromptText("email");
        emailLayout.getChildren().addAll(emailLabel, emailField);

        VBox phoneNumberLayout = new VBox();
        Label phoneNumberLabel = new Label("Phone Number: ");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("phone number");
        phoneNumberLayout.getChildren().addAll(phoneNumberLabel, phoneNumberField);

        fourthLine.getChildren().addAll(emailLayout, phoneNumberLayout);

        return fourthLine;
    }

    private HBox fifthLine(){
        HBox fifthLine = new HBox(5);

        // Populate department ComboBox with department names
        HashMap<String, ArrayList<String>> departmentData = getDepartment();
        ObservableList<String> departmentNames = departmentField.getItems();
        departmentData.keySet().forEach(departmentNames::add);

        // Event handler for department selection
        departmentField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedDepartment = departmentField.getValue();
                if (selectedDepartment != null) {
                    ArrayList<String> roles = departmentData.get(selectedDepartment);
                    designationField.getItems().setAll(roles);
                }
            }
        });

        VBox departmentLayout = new VBox();
        Label departmentLabel = new Label("Department: ");
        departmentField.setPromptText("department");
        departmentField.getStyleClass().add("drop-down");
        departmentLayout.getChildren().addAll(departmentLabel, departmentField);

        VBox designationLayout = new VBox();
        Label designationLabel = new Label("Designation: ");
        designationField.setPromptText("designation");
        designationField.getStyleClass().add("drop-down");
        designationLayout.getChildren().addAll(designationLabel, designationField);
        fifthLine.getChildren().addAll(departmentLayout, designationLayout);

        return fifthLine;
    }

    
    public void setPictureDirectory(String directory) {
        this.pictureDirectory = directory;
    }

    // Use this to get picture directory @Kharl
    public String getPictureDirectory() {
        return this.pictureDirectory;
    }

    private HBox sixthLine(){
        HBox sixthLine = new HBox(5);

        VBox payPerHourLayout = new VBox();
        Label payPerHourLabel = new Label("Pay per hour: ");
        payPerHourField = new TextField();
        payPerHourField.setPromptText("pay per hour");
        
        payPerHourLayout.getChildren().addAll(payPerHourLabel, payPerHourField);

        VBox pictureLayout = new VBox();
        Label pictureLabel = new Label("Picture: ");
        pictureLayout.getChildren().addAll(pictureLabel, uploadPhoto);
        uploadPhoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open User Photo");

            // Set extension filter to only allow image files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                setPictureDirectory(file.getAbsolutePath()); // Set directory containing the selected image
                System.out.println("Selected file: " + getPictureDirectory());
            }
        });



        

        sixthLine.getChildren().addAll(payPerHourLayout, pictureLayout);

        return sixthLine;
    }

    private HashMap<String, ArrayList<String>> getDepartment() {
        HashMap<String, ArrayList<String>> department = new HashMap<>();
        Path departmentPath = Paths.get("data/departmentDesignation.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(departmentPath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] departmentDetails = line.split("#");
                String departmentName = departmentDetails[0];
                String[] roles = departmentDetails[1].split(",");
                ArrayList<String> roleList = new ArrayList<>();
                for (String role : roles) {
                    roleList.add(role.trim());  // trim to remove any leading/trailing whitespace
                }
                department.put(departmentName, roleList);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return department;
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
        //[0]Id, [1]name, [2]department, [3]designation, [4]Birth Date, [5]Hire Date, [6]Address, [7],Phone Number, [8]Pay/Day, [9]Total Hours Worked, [10]Total Overtime, [11]Gross Deductions, [12]GrossPay, [13]Check-In, [14]Check-Out, [15] Status
        String newName = "--", newDeparment= "--", newDesignation = "--", newBirthDate = "--", newHireDate = "--", newAddress = "--", newPhoneNumber = "--", newPayPerDay = "--", newTotalHoursWorked = "--", newTotalOvertime = "--", newTotalLates = "0", newGrossDeductions = "--", newGrossPay = "--", newCheckIn = "--", newCheckOut = "--", newStatus = "--";

        newName = nameField;
        newDeparment = departmentField;
        newDesignation = designationField;
        newPayPerDay = payPerDayField;
        newStatus = "employee";

        String addEmployeeLine = newID+"#"+newName+"#"+newDeparment+"#"+newDesignation+"#"+newBirthDate+"#"+newHireDate+"#"+newAddress+"#"+newPhoneNumber+"#"+newPayPerDay+"#"+newTotalHoursWorked+"#"+newTotalOvertime+"#"+newTotalLates+"#"+newGrossDeductions+"#"+newGrossPay+"#"+newCheckIn+"#"+newCheckOut+"#"+newStatus;

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
