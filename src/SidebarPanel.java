import java.sql.Time;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SidebarPanel{
    public VBox createSidebar(Stage window, Scene scene, String activeButton){
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setPadding(new Insets(20));

        ImageView logo = new ImageView(new Image("images/logo.png"));
        logo.getStyleClass().add("logo");

        Label titleLabel = new Label("Employee System");
        titleLabel.getStyleClass().add("title");

        // Buttons for sidebar navigation
        Button dashboardButton = createSidebarButton("Dashboard", "images/article.png");
        Button timesheetsButton = createSidebarButton("Timesheets", "images/calendar_month.png");
        Button payrollButton = createSidebarButton("Payroll", "images/contract.png");
        if (activeButton.equals("dashboard"))    
            dashboardButton.getStyleClass().add("active-Button");
        else if(activeButton.equals("timesheets"))
            timesheetsButton.getStyleClass().add("active-Button");
        else if(activeButton.equals("payroll"))
            payrollButton.getStyleClass().add("active-Button");

        dashboardButton.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.showDashboard(window);
        });
        timesheetsButton.setOnAction(e -> {
            Timesheets timesheetsPage = new Timesheets();
            timesheetsPage.showTimesheets(window);
        });
        payrollButton.setOnAction(e -> {
            Payroll payrollPage = new Payroll();
            payrollPage.showPayroll(window);
        });

        // Logout Button
        Button logoutButton = createSidebarButton("Log Out | Time Out", "images/door_open.png");
        logoutButton.getStyleClass().add("logout-button");

        logoutButton.setOnAction(e -> {
            LoginPage login = new LoginPage();
            try {
                login.showLogin(window);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Spacer to push logoutButton to the bottom
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logo, titleLabel, dashboardButton, timesheetsButton, payrollButton, spacer, logoutButton);

        scene.getStylesheets().add("css/main.css");
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
}