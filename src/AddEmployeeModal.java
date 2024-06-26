
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    Stage window, mainStage;
    String userID, destinationFile; 
    HashMap<String, String> userData = new HashMap<>();
    TextField usernameField, fullNameField, emailField, phoneNumberField, payPerDayField;
    ComboBox<String> gender = new ComboBox<String>();
    PasswordField passwordField, confirmPassword;
    DatePicker birthDate = new DatePicker();
    ComboBox<String> departmentField = new ComboBox<String>();
    ComboBox<String> designationField = new ComboBox<String>();
    private String pictureDirectory;
    Label imageLabel, textPictureLabel;
    File file;
    Button uploadPhoto;
    Path usersPath = Paths.get("data/users.txt");
    Path employeePath = Paths.get("data/employee.txt");

    void showAddModal(Stage stage, String ID) {
        userID = ID;
        userData = data.getUserData(ID);
        mainStage = stage;

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
 
        Scene detailsScene = new Scene(detailsLayout, 580, 490);
        Image icon = new Image("images/logo-icon.png");
        detailsScene.getStylesheets().add("css/addModal.css");
        window.getIcons().add(icon);
        window.setScene(detailsScene);
        window.setResizable(false);
        window.showAndWait();
    }

    private VBox showContent(){
        VBox content = new VBox(10);
        HBox firstLine = firstLine();
        HBox secondLine = secondLine();
        HBox thirdLine = thirdLine();
        HBox fourthLine = fourthLine();
        HBox fifthLine = fifthLine();
        HBox sixthLine = sixthLine();

        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("create-button");
        HBox buttonLayout = new HBox(submitButton);
        submitButton.setOnAction(e -> {
            createEmployee(usernameField.getText(), passwordField.getText(), confirmPassword.getText(),fullNameField.getText(), departmentField.getValue(), designationField.getValue(), payPerDayField.getText(), gender.getValue(), birthDate.getValue(), emailField.getText(), phoneNumberField.getText());
        });
        
        content.getChildren().addAll(firstLine, secondLine, thirdLine, fourthLine, fifthLine, sixthLine, buttonLayout);

        return content;
    }

    private HBox firstLine(){
        HBox firstLine = new HBox(5);
        VBox usernameBox = new VBox(5);
        Label username = new Label("Username: ");
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        usernameBox.getChildren().addAll(username, usernameField);
        firstLine.getChildren().addAll(usernameBox);
        return firstLine;
    }
    
    private HBox secondLine(){
        HBox secondLine = new HBox(5);

        VBox passwordLayout = new VBox();
        Label password = new Label("Password: ");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        passwordLayout.getChildren().addAll(password, passwordField);

        VBox confirmPasswordLayout = new VBox();
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        
        confirmPasswordLayout.getChildren().addAll(confirmPasswordLabel, confirmPassword);

        secondLine.getChildren().addAll(passwordLayout, confirmPasswordLayout);

        return secondLine;
    }
    
    private HBox thirdLine(){
        HBox thirdLine = new HBox(5);

        VBox fullNameLayout = new VBox();
        Label fullNameLabel = new Label("Full Name: ");
        fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        fullNameLayout.getChildren().addAll(fullNameLabel, fullNameField);

        VBox genderLayout = new VBox();
        gender.setPromptText("gender");
        gender.getStyleClass().add("gender-field");

        gender.getItems().add("Male");
        gender.getItems().add("Female");
        gender.getItems().add("Prefer not to say");
        Label genderLabel = new Label("Gender: ");
        genderLayout.getChildren().addAll(genderLabel, gender);

        VBox birthDateBox = new VBox();
        Label birthDateLabel = new Label("Birth Date: ");
        birthDate.setPromptText("Birth Date");
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
        emailField.setPromptText("Email");
        emailLayout.getChildren().addAll(emailLabel, emailField);

        VBox phoneNumberLayout = new VBox();
        Label phoneNumberLabel = new Label("Phone Number: ");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
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
        departmentField.setPromptText("Department");
        departmentField.getStyleClass().add("drop-down");
        departmentLayout.getChildren().addAll(departmentLabel, departmentField);

        VBox designationLayout = new VBox();
        Label designationLabel = new Label("Designation: ");
        designationField.setPromptText("Designation");
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

        VBox payPerDayLayout = new VBox();
        Label payPerDayLabel = new Label("Pay per day: ");
        payPerDayField = new TextField();
        payPerDayField.setPromptText("Pay Per Day");
        
        payPerDayLayout.getChildren().addAll(payPerDayLabel, payPerDayField);

        VBox pictureLayout = new VBox();
        Label pictureLabel = new Label("Picture: ");
        HBox uploadAndPictureLabel = new HBox();
        uploadPhoto = new Button("Upload Photo");
        uploadPhoto.getStyleClass().add("upload-btn");
        textPictureLabel = new Label();
        textPictureLabel.getStyleClass().add("textPictureLabel");
        uploadAndPictureLabel.setAlignment(Pos.CENTER_LEFT);

        uploadAndPictureLabel.getChildren().addAll(uploadPhoto, textPictureLabel);
        pictureLayout.getChildren().addAll(pictureLabel, uploadAndPictureLabel);
        uploadPhoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open User Photo");

            // Set extension filter to only allow image files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);

            file = fileChooser.showOpenDialog(window);
            if (file != null) {
                String[] fileDirectory = file.toString().replace('\\', '/').split("/");
                String fileName = fileDirectory[fileDirectory.length-1];
                textPictureLabel.setText(fileName);
                setPictureDirectory(file.getAbsolutePath()); // Set directory containing the selected image
                destinationFile = "src/images/userImage/" + usernameField.getText() + ".png";

            }
        });



        

        sixthLine.getChildren().addAll(payPerDayLayout, pictureLayout);

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

    private void createEmployee(String usernameField, String passwordField, String confirmPassword, String nameField, Object departmentField, Object designationField, String payPerDayField, Object genderField, Object birthdateField, String emailField, String phoneNumberField){
        // Check if any field is empty
        if(usernameField.isEmpty() || passwordField.isEmpty() || confirmPassword.isEmpty() || nameField.isEmpty() || departmentField == null || designationField == null || payPerDayField.isEmpty() || genderField == null || birthdateField == null || emailField.isEmpty() || phoneNumberField.isEmpty() || file == null){
            showAlert("Warning Dialog", "Missing Information", "Please fill all the fields.", "warning");
            return;
        }
        
        // Find the Highest ID then add 1 and set as new ID to the new employee
        int newID = 0;
        Path employeeDataPath = Paths.get("data/employee.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(employeeDataPath.toFile()))) {
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
        //[0]Id, [1]name, [2]department, [3]designation, [4]gender, [5]birthDate, [6]hireDate, [7]email, [8]address, [9]phoneNumber, [10]payPerDay, [11]hoursWorked, [12]totalOvertime, [13]lates, [14]deductions, [15]grossPay, [16]timeIn, [17]timeOut, [18]status
        String newName = "--", newDepartment= "--", newDesignation = "--", newGender = "--", newBirthDate = "--", newHireDate = "--", newEmail = "--", newAddress = "--", newPhoneNumber = "--", newPayPerDay = "0", newTotalHoursWorked = "0", newTotalOvertime = "0", newTotalLates = "0", newGrossDeductions = "0", newGrossPay = "0", newCheckIn = "--", newCheckOut = "--", newStatus = "--";

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        
        newName = nameField;
        newDepartment = departmentField.toString();
        newDesignation = designationField.toString();
        newEmail = emailField;
        newPayPerDay = payPerDayField;
        newGender = genderField.toString();
        newBirthDate = birthDate.getValue().format(formatter);
        newPhoneNumber = phoneNumberField;
        newStatus = "employee";

        newHireDate = currentDate.format(formatter);

        String addEmployeeLine = newID + "#" + newName + "#" + newDepartment + "#" + newDesignation + "#" + newGender + "#" + newBirthDate + "#" + newHireDate + "#" + newEmail + "#" + newAddress + "#" + newPhoneNumber + "#" + newPayPerDay + "#" + newTotalHoursWorked + "#" + newTotalOvertime + "#" + newTotalLates + "#" + newGrossDeductions + "#" + newGrossPay + "#" + newCheckIn + "#" + newCheckOut + "#" + newStatus;


        
        try {
            List<String> usersTxtLines = Files.readAllLines(usersPath);

            // check if that username is already exist
            boolean usernameAlreadExist = false;
            for (int i = 1; i < usersTxtLines.size(); i++) {
                String[] usersParts = usersTxtLines.get(i).split("#");
                if(usersParts[1].equals(usernameField))
                    usernameAlreadExist = true;
            }
            if (!usernameAlreadExist) {
                if (passwordField.equals(confirmPassword)){
                    SHA256HashGenerator hash = new SHA256HashGenerator();
    
                    String hashString = hash.generateSHA256Hash(passwordField);
                    String addUserLine = newID+"#"+usernameField+"#"+hashString+"#"+newStatus;
        
                    FileWriter addEmployee = new FileWriter(employeePath.toString(), true); // Set true for append mode
                    addEmployee.write(addEmployeeLine + System.lineSeparator()); // Append new line separator to create a new line
                    addEmployee.close();
        
                    FileWriter addUser = new FileWriter(usersPath.toString(), true); // Set true for append mode
                    addUser.write(addUserLine + System.lineSeparator()); // Append new line separator to create a new line
                    addUser.close();

                    try {
                        // Create Path objects from the file paths
                        Path sourcePath = Paths.get(getPictureDirectory());
                        Path destinationPath = Paths.get(destinationFile);
            
                        // Perform the copy using Files.copy() method
                        Files.copy(sourcePath, destinationPath);
    
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Picture Uploaded!");
                        alert.setHeaderText("User Picture Uploaded");
                        alert.setContentText("The user's picture has been uploaded successfully.");
    
                        alert.showAndWait();
                        return; // Exit the method if any field is empty
            
                    } catch (IOException s) {
                        showAlert("Error", "Failes to upload", "Failed to upload user's photo. Please try again.", "information");
                    }
        
                    window.close();
                    dashboardPage.showDashboard(mainStage, userID);
                } else {
                    showAlert("Invalid Password", "Password does not match", "Both password textfield should match", "error");
                    return;
                }
            } else {
                showAlert("Invalid Username", "This username is alreadyy exist", "Please enter another username", "information");
                return;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private void showAlert(String title, String header, String message, String type){
        // Show alert using JavaFX
        Alert alert;

        if (type.equals("warning")){
            alert = new Alert(AlertType.WARNING);
        } else if (type.equals("information")){
            alert = new Alert(AlertType.INFORMATION);
        } else {
            alert = new Alert(AlertType.ERROR);
        }
        
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
