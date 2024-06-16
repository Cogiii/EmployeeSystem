import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardPage {

    public void showDashboard(Stage window) {
        HBox layout = new HBox();

        VBox sidebar = sideBarPanel();

        VBox main = main();

        HBox.setMargin(sidebar, new Insets(10));
        layout.getChildren().addAll(sidebar, main);

        Scene dashboardPage = new Scene(layout, 1000, 600);
        dashboardPage.getStylesheets().add("css/dashboard.css");
        window.setTitle("Employee Management System");
        window.setScene(dashboardPage);
    }

    public VBox sideBarPanel(){
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setPadding(new Insets(20));

        ImageView logo = new ImageView(new Image("images/logo.png"));
        logo.getStyleClass().add("logo");

        Label titleLabel = new Label("Employee System");
        titleLabel.getStyleClass().add("title");

        // button for side bar
        Button dashboardButton = createSidebarButton("Dashboard", "images/article.png");
        dashboardButton.getStyleClass().add("active-Button");
        Button timesheetsButton = createSidebarButton("Timesheets", "images/calendar_month.png");
        Button payrollButton = createSidebarButton("Payroll", "images/contract.png");
        
        // ambot gi vbox nalang nako para maubos siya tas set lang top margin hahaha kapoy na maghuna huna
        Button logoutButton = createSidebarButton("Log Out | Time Out", "images/door_open.png");
        VBox logoutButtonBox = new VBox(logoutButton);
        VBox.setMargin(logoutButton, new Insets(210, 0, 0, 0));

        sidebar.getChildren().addAll(logo, titleLabel, dashboardButton, timesheetsButton, payrollButton,logoutButtonBox);

        return sidebar;
    }
    private Button createSidebarButton(String text, String iconPath) {
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(20);
        icon.setFitHeight(20);

        Button button = new Button(text, icon);
        button.getStyleClass().add("sidebar-button");

        HBox.setMargin(button, new Insets(5, 0, 5, 0)); // Add margin between buttons

        return button;
    }

    private VBox main(){
        VBox main = new VBox();

        return main;
    }

    private HBox topMain(){
        HBox top = new HBox();
        Label title = new Label("Admin Dashboard");

        return top;
    }
}
