import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Payroll {
    Table table = new Table();
    SidebarPanel sidebarPanel = new SidebarPanel();

    TableView<Employee> tableData;
    TextField searchEmployee;
    ObservableList<Employee> employees = FXCollections.observableArrayList();

    public void showPayroll(Stage window) {
        HBox layout = new HBox();
        Scene dashboardPage = new Scene(layout, 1000, 600);

        VBox sidebar = sidebarPanel.createSidebar(window, dashboardPage, "payroll");
        VBox mainContent = createMainContent(window);

        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(mainContent, new Insets(30,10,10,10));
        layout.getChildren().addAll(sidebar, mainContent);

        dashboardPage.getStylesheets().add("css/main.css");
        window.setTitle("Employee Management System");
        window.setScene(dashboardPage);
    }

    private VBox createMainContent(Stage window){
        // width, variable, column width
        String[][] tableHeader = {{"ID", "ID", "70"}, {"Employee Name", "name", "200"}, {"Pay/Hour", "hour_pay", "100"}, {"Hours Worked", "hours_worked", "130"}, {"Total Overtime", "total_overtime", "130"}, {"Gross Pay", "gross_pay", "115"}};
        
        VBox main = new VBox(10);

        HBox top = createMainTop("Admin Dashboard", "Hanni Pham", "Admin");
        StackPane userPanel = createUserPanel("Hanni Pham", "Senior Admin Janitor", "Davao");
        HBox mainHeader = createMainHeader();
        VBox tablelayout = table.createTable(window, tableHeader, searchEmployee, tableData, employees, "payroll");

        main.getChildren().addAll(top, userPanel, mainHeader, tablelayout);
        return main;
    }

    HBox createMainTop(String title, String username, String position){
        HBox top = new HBox();
    
        Label titleLabel = new Label(title);
        HBox.setMargin(titleLabel, new Insets(0, 10, 0, 0));
        
        HBox topRight = new HBox();
        topRight.setAlignment(Pos.CENTER_RIGHT);
    
        VBox userLabel = new VBox();
        userLabel.setAlignment(Pos.CENTER_RIGHT); // Align labels to the center right
    
        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("top-user_name");
    
        Label userPositionLabel = new Label(position);
        userPositionLabel.getStyleClass().add("top-user_position");
    
        userLabel.getChildren().addAll(usernameLabel, userPositionLabel);
    
        Image originalImage = new Image("images/userImage/hannipham.jpg");

        // Calculate dimensions for the square
        double squareSize = Math.min(originalImage.getWidth(), originalImage.getHeight());
        double startX = (originalImage.getWidth() - squareSize) / 2;
        double startY = (originalImage.getHeight() - squareSize) / 2;

        // Create a viewport to crop the original image to square
        Rectangle2D viewportRect = new Rectangle2D(startX, startY, squareSize, squareSize);
        
        ImageView userPicture = new ImageView(originalImage);
        userPicture.setViewport(viewportRect);
        userPicture.setFitWidth(50);
        userPicture.setFitHeight(50);
        userPicture.setPreserveRatio(false);

        // Create a Rectangle with rounded corners (as a clipping mask)
        Rectangle clip = new Rectangle(50, 50);
        clip.setArcWidth(20); // Adjust the arc width as needed
        clip.setArcHeight(20); // Adjust the arc height as needed

        // Apply clipping to the ImageView
        userPicture.setClip(clip);

        // Create a StackPane and add the Rectangle and ImageView
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(userPicture); 

        userPicture.setFitWidth(50);
        userPicture.setFitHeight(50);
    
        topRight.getChildren().addAll(userLabel, stackPane);
        topRight.setSpacing(5);

         // Add spacer to align user information to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        top.getChildren().addAll(titleLabel, spacer, topRight);
        HBox.setHgrow(topRight, Priority.ALWAYS); // Allow topRight to grow horizontally
        top.setAlignment(Pos.CENTER_LEFT);
    
        return top;
    }

    StackPane createUserPanel(String username, String position, String location){
        StackPane panel = new StackPane();
        panel.getStyleClass().add("panel");
        
        HBox panel_data = new HBox(5);

        Image originalImage = new Image("images/userImage/hannipham.jpg");

        // Calculate dimensions for the square
        double squareSize = Math.min(originalImage.getWidth(), originalImage.getHeight());
        double startX = (originalImage.getWidth() - squareSize) / 2;
        double startY = (originalImage.getHeight() - squareSize) / 2;

        // Create a viewport to crop the original image to square
        Rectangle2D viewportRect = new Rectangle2D(startX, startY, squareSize, squareSize);
        
        ImageView userPicture = new ImageView(originalImage);
        userPicture.setViewport(viewportRect);
        userPicture.setFitWidth(100);
        userPicture.setFitHeight(100);
        userPicture.setPreserveRatio(false);

        // Create a Rectangle with rounded corners (as a clipping mask)
        Rectangle clip = new Rectangle(130, 130);
        clip.setArcWidth(20); // Adjust the arc width as needed
        clip.setArcHeight(20); // Adjust the arc height as needed

        // Apply clipping to the ImageView
        userPicture.setClip(clip);

        // Create a StackPane and add the Rectangle and ImageView
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(userPicture); 

        userPicture.getStyleClass().add("image");

        VBox userInfo = new VBox();
        Label user_name = new Label(username); 
        user_name.getStyleClass().add("panel-user_name");
        Label user_position = new Label(position);
        user_position.getStyleClass().add("panel-user_position");

        HBox loc_data = new HBox();
        Label locationLabel = new Label("Location: ");
        locationLabel.getStyleClass().add("loc-label");
        Label user_Location = new Label(location);
        user_Location.getStyleClass().add("loc-user_label");
        loc_data.setPadding(new Insets(60, 0, 0, 0));
        loc_data.getChildren().addAll(locationLabel,user_Location);

        userInfo.getChildren().addAll(user_name,user_position,loc_data);
        panel_data.getChildren().addAll(stackPane, userInfo);

        panel.getChildren().addAll(panel_data);
        return panel;
    }

    private HBox createMainHeader(){
        HBox header = new HBox(236);
        
        // Label for header title
        Label headerTitle = new Label("Payroll");
        headerTitle.setAlignment(Pos.CENTER_LEFT); // Align left
        
        // TextField for searching employees
        searchEmployee = new TextField();
        searchEmployee.setPromptText("Search Employee");
        searchEmployee.getStyleClass().add("search");
        searchEmployee.setAlignment(Pos.CENTER_LEFT);
        
        header.getChildren().addAll(headerTitle, searchEmployee);
        
        return header;
    }
}
