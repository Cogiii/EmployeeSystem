import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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

    void showAddModal() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Employee Details");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10, 30, 10, 30));

        Label titleLabel = new Label("Add An Employee");
        VBox addFields = showTextFields();
        Button createButton = new Button("Create Employee");
        createButton.getStyleClass().add("create-button");

        detailsLayout.setAlignment(Pos.TOP_LEFT);
        detailsLayout.getChildren().addAll(titleLabel, addFields, createButton);
 
        Scene detailsScene = new Scene(detailsLayout, 600, 380);
        Image icon = new Image("images/logo-icon.png");
        detailsScene.getStylesheets().add("css/modal.css");
        window.getIcons().add(icon);
        window.setScene(detailsScene);
        window.setResizable(false);
        window.showAndWait();
    }

    private VBox showTextFields(){
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

        addFields.getChildren().addAll(userLoginDetails, nameField, departmentField, designationField, pay_hourField);
        return addFields;
    }

    
}
