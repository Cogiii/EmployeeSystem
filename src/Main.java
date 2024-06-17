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
        Timesheets timesheets = new Timesheets();
        Payroll payroll = new Payroll();

        // dashboard.showDashboard(window); // show dashboard
        // timesheets.showTimesheets(window); // show timesheets
        // payroll.showPayroll(window); // show timesheets
        
        login.showLogin(window); // show login page first

        window.setResizable(false);
        window.show();
    }
}