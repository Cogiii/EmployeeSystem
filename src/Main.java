import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application{
    Stage window;
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Image icon = new Image("images/logo-icon.png");
        window.getIcons().add(icon);

        LoginPage.showLogin(window); // show login page first

        window.show();
    }
}
