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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewEmployeeModal {
    private String user_ID, grossPay, name, department, designation, username;
    Data data = new Data();
    DashboardPage dashboardPage = new DashboardPage();
    Stage detailsStage;
    String rowID;
    String userID;
    HashMap <String, String> rowData = new HashMap<>();
    HashMap <String, String> userData = new HashMap<>();

    void showEmployeeDetails(Stage window, Employee employee, String ID) {
        rowID = employee.getID();
        rowData = data.getUserData(rowID);
        userID = ID;
        userData = data.getUserData(ID);

        detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("View Employee");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10,20,10,20));

        Label titleLabel = new Label("View Employee");
        titleLabel.getStyleClass().add("label-header");

        HBox content = showContent();

        detailsLayout.setAlignment(Pos.TOP_CENTER);
        detailsLayout.getChildren().addAll(titleLabel, content);

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

        Label labelID = new Label("ID:");
        TextField textFieldID = new TextField(rowData.get("ID"));
        textFieldID.setEditable(false);

        Label labelDeperment = new Label("Department:");
        TextField textFieldDepartment = new TextField(rowData.get("department"));

        Label labelDesignation = new Label("Designation:");
        TextField textFieldDesignation = new TextField(rowData.get("designation"));
        
        Label labelHireDate = new Label("Hire Date:");
        TextField textFieldHireDate = new TextField(rowData.get("hireDate"));

        leftContent.getChildren().addAll(imagePane, labelID, textFieldID, labelDeperment, textFieldDepartment, labelDesignation, textFieldDesignation, labelHireDate, textFieldHireDate);

        return leftContent;
    }

    private VBox rightContent(){
        VBox rightContent = new VBox(10);

        Label personalDetailsLabel = new Label("Personal Details");
        personalDetailsLabel.getStyleClass().add("content-header");

        HBox usernameAndPass = usernameAndPass();
        HBox rowDetails = new HBox();
        

        rightContent.getChildren().addAll(personalDetailsLabel, usernameAndPass);

        return rightContent;
    }

    private HBox usernameAndPass(){
        HBox usernameAndPass = new HBox(10);

        VBox username = new VBox();
        Label usernamLabel = new Label("Username:");
        TextField textFieldUsername = new TextField(rowData.get("username"));
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

    private HBox createButton(TextField usernameField, PasswordField passwordField, TextField nameField, TextField departmentField, TextField designationField, TextField grossPayField, Stage window){
        HBox layout = new HBox(5);

        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        deleteButton.getStyleClass().add("delete-button");
        updateButton.getStyleClass().add("update-button");

        deleteButton.setOnAction(e -> deleteEmployee(window));
        updateButton.setOnAction(e -> updateEmployee(window, usernameField.getText(), nameField.getText(), departmentField.getText(), designationField.getText(), grossPayField.getText(), String.valueOf(user_ID)));

        layout.getChildren().addAll(deleteButton,updateButton);
        return layout;
    }

    private void deleteEmployee(Stage window){
        // Show confirmation dialog for delete operation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete this employee?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                data.deleteEmployeeData(String.valueOf(user_ID));
                detailsStage.close();
                dashboardPage.showDashboard(window, userID);
            }
        });

    }
    private void updateEmployee(Stage window, String newUsername, String newName, String newDepartment, String newDesignation, String newGrossPay, String user_ID){
        // Show confirmation dialog for update operation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText("Confirm Update");
        alert.setContentText("Are you sure you want to update this employee?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                data.updateEmployeeData(newUsername, newName, newDepartment, newDesignation, newGrossPay, user_ID);
                detailsStage.close();
                dashboardPage.showDashboard(window, userID);
            }
        });
        
    }

    
}
