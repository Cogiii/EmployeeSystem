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
        FileAuthenticator auth = new FileAuthenticator();
        boolean isAuthenticated = auth.authenticateUser(usernameInput.getText(), passwordInput.getText());
        String status = auth.status;
        String userID = auth.userID;

        if (isAuthenticated && status.equals("employee")) 
            proceedToEmployeeDashboard(window, userID, status);
        else if (isAuthenticated && status.equals("admin"))
            proceedToAdminDashboard(window, userID);
        else if(isAuthenticated && status.equals("deleted"))
            showAlertInformation("Invalid User", "Invalid User. This user has been deleted.");
        else 
            showAlerError("Invalid credentials", "Invalid credentials. Please try again.");
    }

    private void proceedToAdminDashboard(Stage window, String userID) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.showDashboard(window, userID);
    }

    private void proceedToEmployeeDashboard(Stage window, String userID, String status){
        Timesheets timesheets = new Timesheets();
        timesheets.showTimesheets(window, userID, status);
    }

    private void showAlertInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlerError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
