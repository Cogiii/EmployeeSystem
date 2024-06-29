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
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEmployeeModal {
    DashboardPage dashboardPage = new DashboardPage();
    Data data = new Data();
    SHA256HashGenerator hash = new SHA256HashGenerator();

    Stage window, mainStage;
    String userID, destinationFile; 
    HashMap<String, String> userData = new HashMap<>();
    TextField usernameField, fullNameField, emailField, phoneNumberField, payPerDayField;
    ComboBox<String> genderField = new ComboBox<>();
    PasswordField passwordField, confirmPasswordField;
    DatePicker birthDateField = new DatePicker();
    ComboBox<String> departmentField = new ComboBox<>();
    ComboBox<String> designationField = new ComboBox<>();
    private String pictureDirectory;
    Label imageLabel, textPictureLabel;
    File file;
    Button uploadPhoto;
    Path usersPath = Paths.get("data/users.txt");
    Path employeePath = Paths.get("data/employee.txt");
    Path departmentPath = Paths.get("data/employee.txt");

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
        content.getChildren().addAll(
            firstLine(),
            secondLine(),
            thirdLine(),
            fourthLine(),
            fifthLine(),
            sixthLine(),
            createButtonLayout()
        );
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
        secondLine.getChildren().addAll(createPasswordLayout("Password: ", passwordField = new PasswordField()), createPasswordLayout("Confirm Password: ", confirmPasswordField = new PasswordField()));
        return secondLine;
    }
    
    private HBox thirdLine(){
        HBox thirdLine = new HBox(5);

        genderField.getItems().addAll("Male", "Female", "Prefer not to say");
        genderField.getStyleClass().add("gender-field");

        thirdLine.getChildren().addAll(createFieldLayout("Full Name: ", fullNameField = new TextField()), createComboBoxLayout("Gender: ", genderField, "gender"), createDatePickerLayout("Birth Date: ", birthDateField, "birthDate-field"));
        return thirdLine;
    }

    private HBox fourthLine(){
        HBox fourthLine = new HBox(5);
        fourthLine.getChildren().addAll(createFieldLayout("Email: ", emailField = new TextField()), createFieldLayout("Phone Number: ", phoneNumberField = new TextField()));
        return fourthLine;
    }

    private HBox fifthLine(){
        HBox fifthLine = new HBox(5);
        HashMap<String, ArrayList<String>> departmentData = getDepartment();
        ObservableList<String> departmentNames = departmentField.getItems();
        departmentData.keySet().forEach(departmentNames::add);

        departmentField.setOnAction(event -> {
            String selectedDepartment = departmentField.getValue();
            if (selectedDepartment != null) {
                ArrayList<String> roles = departmentData.get(selectedDepartment);
                designationField.getItems().setAll(roles);
            }
        });

        fifthLine.getChildren().addAll(createComboBoxLayout("Department: ", departmentField, "drop-down"), createComboBoxLayout("Designation: ", designationField, "drop-down"));
        return fifthLine;
    }

    public void setPictureDirectory(String directory) {
        this.pictureDirectory = directory;
    }

    public String getPictureDirectory() {
        return this.pictureDirectory;
    }

    private HBox sixthLine(){
        HBox sixthLine = new HBox(5);
        VBox payPerDayLayout = createFieldLayout("Pay per day: ", payPerDayField = new TextField());
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
        uploadPhoto.setOnAction(e -> uploadPhotoAction());
        sixthLine.getChildren().addAll(payPerDayLayout, pictureLayout);
        return sixthLine;
    }

    private void uploadPhotoAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open User Photo");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(window);
        if (file != null) {
            String[] fileDirectory = file.toString().replace('\\', '/').split("/");
            String fileName = fileDirectory[fileDirectory.length - 1];
            textPictureLabel.setText(fileName);
            setPictureDirectory(file.getAbsolutePath());
        }
    }

    private VBox createFieldLayout(String labelText, TextField textField) {
        VBox layout = new VBox();
        Label label = new Label(labelText);
        textField.setPromptText(labelText);
        layout.getChildren().addAll(label, textField);
        return layout;
    }

    private VBox createPasswordLayout(String labelText, PasswordField passwordField) {
        VBox layout = new VBox();
        Label label = new Label(labelText);
        passwordField.setPromptText(labelText);
        layout.getChildren().addAll(label, passwordField);
        return layout;
    }

    private VBox createComboBoxLayout(String labelText, ComboBox<String> comboBox, String styleClass) {
        VBox layout = new VBox();
        Label label = new Label(labelText);
        comboBox.setPromptText(labelText);
        comboBox.getStyleClass().add(styleClass);
        layout.getChildren().addAll(label, comboBox);
        return layout;
    }

    private VBox createDatePickerLayout(String labelText, DatePicker datePicker, String styleClass) {
        VBox layout = new VBox();
        Label label = new Label(labelText);
        datePicker.setPromptText(labelText);
        datePicker.getStyleClass().add(styleClass);
        datePicker.setEditable(false);
        layout.getChildren().addAll(label, datePicker);
        return layout;
    }

    private HBox createButtonLayout() {
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("create-button");
        submitButton.setOnAction(e -> createEmployeeAlert());
        HBox buttonLayout = new HBox(submitButton);
        buttonLayout.setAlignment(Pos.CENTER_RIGHT);
        return buttonLayout;
    }

    private HashMap<String, ArrayList<String>> getDepartment() {
        HashMap<String, ArrayList<String>> department = new HashMap<>();
        Path departmentPath = Paths.get("data/departmentDesignation.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(departmentPath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] departmentDesignation = line.split("#");
                String departmentName = departmentDesignation[0];
                String[] designations = departmentDesignation[1].split(",");
                ArrayList<String> roles = new ArrayList<>();
                for (String designation : designations) {
                    roles.add(designation.trim());
                }
                department.put(departmentName, roles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return department;
    }

    private void createEmployeeAlert() {
        // Show confirmation dialog for create operation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Create");
        alert.setHeaderText("Confirm Create");
        alert.setContentText("Are you sure you want to register this employee?");

        // Change button types to Yes and No
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                createEmployee(
                    usernameField.getText().trim(), 
                    passwordField.getText(), 
                    confirmPasswordField.getText(),
                    fullNameField.getText(), 
                    departmentField.getValue(), 
                    designationField.getValue(), 
                    payPerDayField.getText(), 
                    genderField.getValue(), 
                    birthDateField.getValue(), 
                    emailField.getText(), 
                    phoneNumberField.getText()
                );
            }
        });
    }

    private void createEmployee(String username, String password, String confirmPassword, String fullName, String department, String designation, String payPerDay, String gender, LocalDate birthDate, String email, String phoneNumber) {
        resetFieldBorders();
        List<String> errorMessages = new ArrayList<>();

        if (username.isEmpty()) {
            errorMessages.add("Username cannot be empty");
            highlightField(usernameField);
        } else if (username.length() < 5){
            errorMessages.add("Username must be atleast 5 characters long");
            highlightField(usernameField);
        } else if (Character.isDigit(username.charAt(0)) || username.charAt(username.length()-1) == '-' || username.charAt(username.length()-1) == '_') {
            errorMessages.add("Invalid username input");
            highlightField(usernameField);
        } else if (AlreadyExist(username)){
            errorMessages.add("Username is already exist.");
            highlightField(usernameField);
        }
        
        if (containsSpecialCharacter(username)){
            errorMessages.add("Username cannot contain a special character");
            highlightField(usernameField);
        }

        if (password.isEmpty() || !password.equals(confirmPassword)) {
            errorMessages.add("Passwords do not match");
            highlightField(passwordField);
            highlightField(confirmPasswordField);
        } else if (password.length() < 8){
            errorMessages.add("Passwords must be at least 8 characters long");
            highlightField(passwordField);
            highlightField(confirmPasswordField);
        }

        if (fullName.isEmpty()) {
            errorMessages.add("Full Name cannot be empty");
            highlightField(fullNameField);
        }

        if (department == null) {
            errorMessages.add("Department cannot be empty");
            highlightField(departmentField);
        }

        if (designation == null) {
            errorMessages.add("Designation cannot be empty");
            highlightField(designationField);
        }

        if (payPerDay.isEmpty() || !payPerDay.matches("\\d+(\\.\\d{1,2})?")) {
            errorMessages.add("Pay per day must be a number");
            highlightField(payPerDayField);
        }

        if (gender == null) {
            errorMessages.add("Gender cannot be empty");
            highlightField(genderField);
        }

        if (birthDate == null) {
            errorMessages.add("Birth Date cannot be empty");
            highlightField(birthDateField);
        } else if (birthDate.isAfter(LocalDate.now())){
            errorMessages.add("Birth Date cannot be the future");
            highlightField(birthDateField);
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            errorMessages.add("Invalid Email");
            highlightField(emailField);
        }

        if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d+") || phoneNumber.length() != 11) {
            errorMessages.add("Invalid Phone Number");
            highlightField(phoneNumberField);
        }

        if (file == null){
            errorMessages.add("File cannot be empty");
            highlightField(uploadPhoto);
        }

        if (!errorMessages.isEmpty()) {
            displayErrors(errorMessages);
            return;
        }

        //Proceed with saving the employee data
        saveEmployeeData(getNewID(), username, hash.generateSHA256Hash(password), fullName, department, designation, payPerDay, gender, birthDate, email, phoneNumber);
        copyPhoto();
        displaySuccessAlert("Employee Created Successfully");

        window.close();
    }

    private String getNewID() {
        int highest = 0;
        String newID = "";
        try (BufferedReader br = new BufferedReader(new FileReader(departmentPath.toFile()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employees = line.split("#");

                if (Integer.parseInt(employees[0]) > highest){
                    newID = String.valueOf(Integer.parseInt(employees[0])+1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newID;
    }

    private boolean AlreadyExist(String s){
        try {
            List<String> usersLines = Files.readAllLines(usersPath);

            for (int i = 1; i < usersLines.size(); i++) {
                String[] user = usersLines.get(i).split("#");

                if (s.equalsIgnoreCase(user[1])){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void resetFieldBorders() {
        String defaultStyle = "";
        usernameField.setStyle(defaultStyle);
        passwordField.setStyle(defaultStyle);
        confirmPasswordField.setStyle(defaultStyle);
        fullNameField.setStyle(defaultStyle);
        emailField.setStyle(defaultStyle);
        phoneNumberField.setStyle(defaultStyle);
        payPerDayField.setStyle(defaultStyle);
        genderField.setStyle(defaultStyle);
        birthDateField.setStyle(defaultStyle);
        departmentField.setStyle(defaultStyle);
        designationField.setStyle(defaultStyle);
        uploadPhoto.setStyle(defaultStyle);
    }

    private void highlightField(Button field) {
        field.setStyle("-fx-border-color: red;");
    }
    
    private void highlightField(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }

    private void highlightField(ComboBox<String> field) {
        field.setStyle("-fx-border-color: red;");
    }

    private void highlightField(DatePicker field) {
        field.setStyle("-fx-border-color: red;");
    }

    private boolean containsSpecialCharacter(String s) {
        char[] special_characters = {'#', '%', '&', '{', '}', '\\', '<', '>', '*', '?', '/', ' ', '$', '!', '\'', '"', ':', '@', '+', '`', '|', '=', '.'};
        for (char c : special_characters) {
            if (s.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private void displayErrors(List<String> errorMessages) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Form Error");
        alert.setHeaderText("Please correct the errors below:");
        alert.setContentText(String.join("\n", errorMessages));
        alert.showAndWait();
    }

    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        dashboardPage.showDashboard(mainStage, userID);
    }

    private void saveEmployeeData(String newID, String username, String password, String fullName, String department, String designation, String payPerDay, String gender, LocalDate birthDate, String email, String phoneNumber) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();

        String birthDateStr = birthDate.format(dateFormat);
        String hireDateStr = currentDate.format(dateFormat);

        try {
            if (Files.notExists(usersPath)) {
                Files.createFile(usersPath);
            }
            //[0]Id, [1]username, [2]hashPassword, [3]status
            FileWriter userFileWriter = new FileWriter(usersPath.toFile(), true);
            userFileWriter.write(String.join("#", newID, username, password, "employee") + "\n");
            userFileWriter.close();

            if (Files.notExists(employeePath)) {
                Files.createFile(employeePath);
            }

            //[0]Id, [1]name, [2]department, [3]designation, [4]gender, [5]birthDate, [6]hireDate, [7]email, [8]address, [9]phoneNumber, [10]payPerDay, [11]hoursWorked, [12]totalOvertime, [13]lates, [14]deductions, [15]grossPay, [16]timeIn, [17]timeOut, [18]status
            FileWriter employeeFileWriter = new FileWriter(employeePath.toFile(), true);
            employeeFileWriter.write(String.join("#", newID, fullName, department, designation, gender, birthDateStr, hireDateStr, email, "--", phoneNumber, payPerDay, "0", "0", "0", "0", "0", "--", "--", "employee") + "\n");
            employeeFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyPhoto() {
        if (file != null) {
            try {
                destinationFile = "src/images/userImage/" + usernameField.getText() + ".png";
                File destFile = new File(destinationFile);
                Files.copy(file.toPath(), destFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
