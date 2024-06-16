import javafx.application.Application;
import javafx.scene.image.Image;
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

        LoginPage login = new LoginPage();
        DashboardPage dashboard = new DashboardPage();

        // dashboard.showDashboard(window); // show dashboard
        
        login.showLogin(window); // show login page first
        
        window.show();
    }
}
