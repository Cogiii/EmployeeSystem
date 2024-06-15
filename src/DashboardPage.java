import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardPage {

    public void showDashboard(Stage window) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to the Dashboard");
        layout.getChildren().add(welcomeLabel);

        Scene dashboardPage = new Scene(layout, 750, 500);

        window.setTitle("Employee Management System");
        window.setScene(dashboardPage);
    }
}
