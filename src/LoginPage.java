import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class LoginPage {

    private TextField usernameInput;
    private PasswordField passwordInput;

    public void showLogin(Stage window) throws Exception {
        // Header setup
        HBox header = new HBox(30);

        ImageView imageView = new ImageView("images/logo.png");
        imageView.getStyleClass().add("logo");
        
        Label titleLabel = new Label("Employee\n  System");
        header.getChildren().addAll(imageView, titleLabel);

        // Input fields and login button setup
        VBox elements = new VBox(10);
        usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        
        passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        loginButton.setOnAction(e -> validateAndLogin(window));

        // Add all fields, button, header into elements
        elements.getChildren().addAll(header, usernameInput, passwordInput, loginButton);
        elements.setAlignment(Pos.CENTER);

        // StackPane to center elements
        StackPane centerPane = new StackPane();
        centerPane.getStyleClass().add("stack-pane");
        centerPane.getChildren().add(elements);

        BorderPane layout = new BorderPane();
        layout.setCenter(centerPane);

        Scene loginPage = new Scene(layout, 750, 500);
        loginPage.getStylesheets().add("css/login.css");
        window.setResizable(false);
        window.setTitle("Login Page");
        window.setScene(loginPage);
    }

    private void validateAndLogin(Stage window) {
        if (authenticate(usernameInput, passwordInput)) 
            proceedToDashboard(window);
        else 
            showAlert("Invalid credentials", "Invalid credentials. Please try again.");
    }

    private boolean authenticate(TextField usernameInput, PasswordField passwordInput) {
        String inputUsername = usernameInput.getText();
        String inputPassword = passwordInput.getText();

        // This line is for testing only
        String usernameTest = "admin";
        String passwordTest = "123";

        return inputUsername.equals(usernameTest) && inputPassword.equals(passwordTest);
    }

    private void proceedToDashboard(Stage window) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.showDashboard(window);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
