import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage{

    public static void showLogin(Stage window) throws Exception {
        window.setTitle("Login Page");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setId("bold-label");
        GridPane.setConstraints(usernameLabel,0,0);
        TextField usernameInput = new TextField("Laurence");
        GridPane.setConstraints(usernameInput,1,0);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setId("bold-label");
        GridPane.setConstraints(passwordLabel,0,1);
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput,1,1);

        Button loginBtn = new Button("Login");
        GridPane.setConstraints(loginBtn,1,2);

        grid.getChildren().addAll(usernameLabel,usernameInput,passwordLabel,passwordInput,loginBtn);

        Scene scene = new Scene(grid);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show(); 
    }
    
}
