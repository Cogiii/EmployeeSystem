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

    public VBox createSidebar(Stage window, String userID, Scene scene, String activeButton, String status){
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

        if (status.equals("admin")){
            switch (activeButton) {
                case "dashboard":
                    dashboardButton.getStyleClass().add("active-Button"); 
                    break;
                case "timesheets":
                    timesheetsButton.getStyleClass().add("active-Button");
                    break;
                case "payroll":
                    payrollButton.getStyleClass().add("active-Button"); 
                    break;    
            }
            dashboardButton.setOnAction(e -> {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.showDashboard(window, userID);
            });
            timesheetsButton.setOnAction(e -> {
                Timesheets timesheetsPage = new Timesheets();
                timesheetsPage.showTimesheets(window, userID, status);
            });
            payrollButton.setOnAction(e -> {
                Payroll payrollPage = new Payroll();
                payrollPage.showPayroll(window, userID);
            });

            sidebar.getChildren().addAll(logo, titleLabel, dashboardButton, timesheetsButton, payrollButton);
        } else {
            // Buttons for sidebar navigation
            timesheetsButton = createSidebarButton("Timesheets", "images/calendar_month.png");
            timesheetsButton.getStyleClass().add("active-Button");

            sidebar.getChildren().addAll(logo, titleLabel, timesheetsButton);
        }

        // Logout Button
        Button logoutButton = createSidebarButton("Log Out | Time Out", "images/door_open.png");
        logoutButton.getStyleClass().add("logout-button");

        logoutButton.setOnAction(e -> {
            LoginPage login = new LoginPage();
            Attendance attendance = new Attendance();
            try {
                login.showLogin(window);
                attendance.record(userID, status);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Spacer to push logoutButton to the bottom
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(spacer, logoutButton);

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