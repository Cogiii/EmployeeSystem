import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class LoginPage{

    public static void showLogin(Stage window) throws Exception {
        // Header setup
        HBox header = new HBox(30);

        ImageView imageView = new ImageView("images/logo.png");
        imageView.getStyleClass().add("logo");;
        
        Label titleLabel = new Label("Employee\n  System");
        header.getChildren().addAll(imageView,titleLabel);

        // Input fields and login button setup
        VBox elements = new VBox(10);
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");

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
    
}
