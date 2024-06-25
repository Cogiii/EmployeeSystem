import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewEmployeeModal {
    Data data = new Data();
    DashboardPage dashboardPage = new DashboardPage();
    Stage detailsStage;
    Stage window;
    String rowID;
    HashMap <String, String> rowData = new HashMap<>();
    HashMap <String, String> userData = new HashMap<>();

    TextField textFieldID, textFieldDepartment, textFieldDesignation, textFieldHireDate, textFieldUsername, textFieldFullname, textFieldAge, textFieldGender, textFieldbirthDate, textFieldEmail, textFieldPhoneNumber, textFieldAddress;

    void showEmployeeDetails(Stage w, Employee employee, String ID) {
        rowID = employee.getID();
        rowData = data.getUserData(rowID);
        userData = data.getUserData(ID);
        window = w;

        detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("View Employee");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10,20,10,20));

        Label titleLabel = new Label("View Employee");
        titleLabel.getStyleClass().add("label-header");

        HBox content = showContent();
        HBox buttons = createButton();

        detailsLayout.setAlignment(Pos.TOP_CENTER);
        detailsLayout.getChildren().addAll(titleLabel, content, buttons);

        Scene detailsScene = new Scene(detailsLayout, 700, 500);
        Image icon = new Image("images/logo-icon.png");
        detailsStage.getIcons().add(icon);
        detailsScene.getStylesheets().add("css/viewModal.css");
        detailsStage.setScene(detailsScene);
        detailsStage.setResizable(false);
        detailsStage.showAndWait();
    }

    private HBox showContent(){
        HBox content = new HBox(20);
        
        VBox leftContent = leftContent();
        VBox rightContent = rightContent();

        content.getChildren().addAll(leftContent, rightContent);

        return content;
    }

    private VBox leftContent(){
        VBox leftContent = new VBox(5);
        leftContent.setAlignment(Pos.TOP_LEFT);

        //---------------------IMAGE-----------------------------
        Image originalImage = new Image("images/userImage/" + rowData.get("username") + ".png");

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

        Label labelID = new Label("ID:");
        textFieldID = new TextField(rowData.get("ID"));
        textFieldID.setEditable(false);

        Label labelDeperment = new Label("Department:");
        textFieldDepartment = new TextField(rowData.get("department"));

        Label labelDesignation = new Label("Designation:");
        textFieldDesignation = new TextField(rowData.get("designation"));
        
        Label labelHireDate = new Label("Hire Date:");
        textFieldHireDate = new TextField(rowData.get("hireDate"));

        leftContent.getChildren().addAll(imagePane, labelID, textFieldID, labelDeperment, textFieldDepartment, labelDesignation, textFieldDesignation, labelHireDate, textFieldHireDate);

        return leftContent;
    }

    private VBox rightContent(){
        VBox rightContent = new VBox(10);

        Label personalDetailsLabel = new Label("Personal Details");
        personalDetailsLabel.getStyleClass().add("content-header");

        HBox usernameAndPass = usernameAndPass();
        HBox employeeInfo = employeeInfo();
        HBox birthDate = birthDateInfo();


        Label contactsLabel = new Label("Contacts");
        contactsLabel.getStyleClass().add("content-header");

        HBox emailAndPhone = emailAndPhone();
        HBox address = address();

        rightContent.getChildren().addAll(personalDetailsLabel, usernameAndPass, employeeInfo, birthDate, contactsLabel, emailAndPhone, address);

        return rightContent;
    }

    private HBox usernameAndPass(){
        HBox usernameAndPass = new HBox(10);

        VBox username = new VBox();
        Label usernamLabel = new Label("Username:");
        textFieldUsername = new TextField(rowData.get("username"));
        textFieldUsername.getStyleClass().add("long-field");
        username.getChildren().addAll(usernamLabel, textFieldUsername);

        VBox password = new VBox();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordFieldPassword = new PasswordField();
        passwordFieldPassword.setText("********");
        passwordFieldPassword.getStyleClass().add("long-field");
        password.getChildren().addAll(passwordLabel, passwordFieldPassword);

        usernameAndPass.getChildren().addAll(username, password);

        return usernameAndPass;
    }

    private HBox employeeInfo(){
        HBox employeeInfo = new HBox(10);

        VBox fullname = new VBox();
        Label fullnameLabel = new Label("Full Name:");
        textFieldFullname = new TextField(rowData.get("name"));
        textFieldFullname.getStyleClass().add("long-field");
        fullname.getChildren().addAll(fullnameLabel, textFieldFullname);

        VBox age = new VBox();
        Label ageLabel = new Label("Age:");
        textFieldAge = new TextField(getAge(rowData.get("birthDate")));
        textFieldAge.getStyleClass().add("short-field");
        age.getChildren().addAll(ageLabel, textFieldAge);

        VBox gender = new VBox();
        Label genderLabel = new Label("Gender:");
        textFieldGender = new TextField(rowData.get("gender"));
        textFieldGender.getStyleClass().add("short-field");
        gender.getChildren().addAll(genderLabel, textFieldGender);

        employeeInfo.getChildren().addAll(fullname, age, gender);

        return employeeInfo;
    }

    private HBox birthDateInfo(){
        HBox birthDate = new HBox(10);

        VBox birthDateBox = new VBox();
        Label birthDateLabel = new Label("Birth date:");
        textFieldbirthDate = new TextField(rowData.get("birthDate"));
        textFieldbirthDate.getStyleClass().add("long-field");
        birthDateBox.getChildren().addAll(birthDateLabel, textFieldbirthDate);

        birthDate.getChildren().add(birthDateBox);

        return birthDate;
    }

    private HBox emailAndPhone(){
        HBox emailAndPhone = new HBox(10);

        VBox email = new VBox();
        Label emailLabel = new Label("Email:");
        textFieldEmail = new TextField(rowData.get("email"));
        textFieldEmail.getStyleClass().add("long-field");
        email.getChildren().addAll(emailLabel, textFieldEmail);

        VBox phoneNumber = new VBox();
        Label phoneNumberLabel = new Label("Phone Number:");
        textFieldPhoneNumber = new TextField(rowData.get("name"));
        textFieldPhoneNumber.getStyleClass().add("long-field");
        phoneNumber.getChildren().addAll(phoneNumberLabel, textFieldPhoneNumber);

        emailAndPhone.getChildren().addAll(email, phoneNumber);

        return emailAndPhone;
    }

    private HBox address(){
        HBox address = new HBox(10);

        VBox addressBox = new VBox();
        Label addressLabel = new Label("Address:");
        textFieldAddress = new TextField(rowData.get("address"));
        textFieldAddress.getStyleClass().add("long-field");
        addressBox.getChildren().addAll(addressLabel, textFieldAddress);

        address.getChildren().add(addressBox);

        return address;
    }

    public String getAge(String birthDate) {
        if (birthDate == null || birthDate.equals("--")) {
            return "--";
        }

        // DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday;

        try {
            birthday = LocalDate.parse(birthDate, dateFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "--";
        }

        LocalDate currentDate = LocalDate.now();
        return String.valueOf(Period.between(birthday, currentDate).getYears());
    }



    private HBox createButton(){
        HBox layout = new HBox(5);

        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        deleteButton.getStyleClass().add("delete-button");
        updateButton.getStyleClass().add("update-button");

        deleteButton.setOnAction(e -> deleteEmployee());
        updateButton.setOnAction(e -> updateEmployee());

        layout.getChildren().addAll(deleteButton,updateButton);
        return layout;
    }

    private void deleteEmployee(){
        // Show confirmation dialog for delete operation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete this employee?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                data.deleteEmployeeData(String.valueOf(rowData.get("ID")));
                detailsStage.close();
                dashboardPage.showDashboard(window, userData.get("ID"));
            }
        });

    }
    private void updateEmployee(){
        // Show confirmation dialog for update operation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText("Confirm Update");
        alert.setContentText("Are you sure you want to update this employee?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                data.updateEmployeeData(rowData.get("ID"), textFieldFullname.getText(), textFieldDepartment.getText(), textFieldDesignation.getText(), textFieldGender.getText(), textFieldbirthDate.getText(), textFieldHireDate.getText(), textFieldEmail.getText(),textFieldAddress.getText(),textFieldPhoneNumber.getText(), textFieldUsername.getText());
                detailsStage.close();
                dashboardPage.showDashboard(window, userData.get("ID"));
            }
        });
        
    }

    
}
