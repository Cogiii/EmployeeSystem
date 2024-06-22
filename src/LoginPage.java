import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

        if (isAuthenticated){
            timeIn(userID);
            
            if (status.equals("employee"))
                proceedToEmployeeDashboard(window, userID, status);
            else if (status.equals("admin"))
                proceedToAdminDashboard(window, userID);

        }
        else if(isAuthenticated && status.equals("deleted"))
            showAlertInformation("Invalid User", "Invalid User. This user has been deleted.");
        else 
            showAlerError("Invalid credentials", "Invalid credentials. Please try again.");
    }

    private void timeIn(String userID) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String currentDate = date.format(dateFormatter);
        String currentTime = time.format(timeFormatter);

        Path timesheetsPath = Paths.get("data/timesheets.txt");
        List<String> fileLines = new ArrayList<>();
        boolean userFound = false;
        boolean timeUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(timesheetsPath.toFile()))) {
            String line;

            // Read file and check if the user has already clocked in
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("#");

                if (parts[0].equals(userID) && parts[1].equals(currentDate)) {
                    userFound = true;

                    // Check if time in for morning or afternoon is already set
                    // else set time in morning (Valid for 6AM to 12PM) or afternoon (Valid for 12:30 to 4PM)
                    if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))) {
                        if (!parts[2].equals("--") ) {
                            // User has already clocked in, return early
                            return;
                        }

                        parts[2] = currentTime; // Set time in for morning
                    } else if (time.isAfter(LocalTime.of(12, 30)) && time.isBefore(LocalTime.of(16, 0))) {
                        if (!parts[4].equals("--")){
                            // User has already clocked in, return early
                            return; 
                        }

                        parts[4] = currentTime; // Set time in for afternoon
                    }

                    // Update the line with new times
                    fileLines.add(String.join("#", parts));
                    timeUpdated = true;
                } else {
                    fileLines.add(line);
                }
            }

            // If user entry not found for the current date, create a new entry
            if (!userFound) {
                String newEntry = "";

                // Check time in for morning (Valid for 6AM to 12PM) or afternoon (Valid for 12:30 to 4PM)
                if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))) {
                    newEntry = userID + "#" + currentDate + "#" + currentTime + "#--#--#--";
                } else if (time.isAfter(LocalTime.of(12, 30)) && time.isBefore(LocalTime.of(16, 0))) {
                    newEntry = userID + "#" + currentDate + "#--#--#" + currentTime + "#--";
                }
                fileLines.add(newEntry);
                timeUpdated = true;
            }

            // Write back to the file if any changes were made
            if (timeUpdated) {
                Files.write(timesheetsPath, fileLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
