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

public class ViewEmployeeModal {
    private int user_ID;
    private String grossPay, name, department, designation, username;

    void showEmployeeDetails(Employee employee) {
        this.user_ID = employee.getID();

        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Employee Details");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(20, 30, 10, 30));

        Label titleLabel = new Label("View an Employee (Employee ID #"+ user_ID+")");
        StackPane userImage = showImage();
        VBox userInfo = showUserInfo(employee);

        detailsLayout.setAlignment(Pos.TOP_LEFT);
        detailsLayout.getChildren().addAll(titleLabel, userImage, userInfo);

        Scene detailsScene = new Scene(detailsLayout, 600, 540);
        Image icon = new Image("images/logo-icon.png");
        detailsStage.getIcons().add(icon);
        detailsScene.getStylesheets().add("css/modal.css");
        detailsStage.setScene(detailsScene);
        detailsStage.setResizable(false);
        detailsStage.showAndWait();
    }

    private void getEmployeeDetails(String id){
        Path employeeDataPath = Paths.get("data/employee.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(employeeDataPath.toFile()))) {
            String line;

            // Skip the first line/header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split("#");
                
                if (row[0].equals(id)){
                    this.name = row[1];
                    this.department = row[2];
                    this.designation = row[3];
                    this.grossPay = row[12];
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        Path usersDataPath = Paths.get("data/users.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;

            // Skip the first line/header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split("#");
                
                if (row[2].equals(id))
                    this.username = row[0];
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
    private VBox showUserInfo(Employee employee){
        VBox userInfo = new VBox(10);

        getEmployeeDetails(String.valueOf(employee.getID()));

        HBox userLoginDetails = new HBox(5);
        TextField usernameField = new TextField(username);
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField("*******");
        passwordField.setPromptText("Password");


        userLoginDetails.getChildren().addAll(usernameField,passwordField);

        TextField nameField = new TextField(name);
        nameField.getStyleClass().add("long-Field");
        nameField.setPromptText("Name");

        TextField departmentField = new TextField(department);
        departmentField.getStyleClass().add("long-Field");
        departmentField.setPromptText("Department");

        TextField designationField = new TextField(designation);
        designationField.getStyleClass().add("long-Field");
        designationField.setPromptText("Designation");

        TextField grossPayField = new TextField(grossPay);
        grossPayField.getStyleClass().add("long-Field");
        grossPayField.setPromptText("Gross Pay");

        HBox button = createButton();

        passwordField.setEditable(false);

        userInfo.getChildren().addAll(userLoginDetails, nameField, departmentField, designationField, grossPayField, button);

        return userInfo;
    }

    private StackPane showImage(){
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
        clip.setArcWidth(20); // Adjust the arc width as needed
        clip.setArcHeight(20); // Adjust the arc height as needed

        // Apply clipping to the ImageView
        userPicture.setClip(clip);

        // Create a StackPane and add the Rectangle and ImageView
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(userPicture);

        return stackPane;
    }

    private HBox createButton(){
        HBox layout = new HBox(5);

        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        deleteButton.getStyleClass().add("delete-button");
        updateButton.getStyleClass().add("update-button");

        deleteButton.setOnAction(e -> deleteEmployee());

        layout.getChildren().addAll(deleteButton,updateButton);
        return layout;
    }

    private void deleteEmployee(){}
}
