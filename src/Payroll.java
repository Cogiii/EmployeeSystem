import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    TableView<Employee> table;

    SidebarPanel sidebarPanel = new SidebarPanel();

    public void showPayroll(Stage window) {
        HBox layout = new HBox();
        Scene dashboardPage = new Scene(layout, 1000, 600);

        VBox sidebar = sidebarPanel.createSidebar(window, dashboardPage, "payroll");
        VBox mainContent = createMainContent();

        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(mainContent, new Insets(30,10,10,10));
        layout.getChildren().addAll(sidebar, mainContent);

        dashboardPage.getStylesheets().add("css/main.css");
        window.setTitle("Employee Management System");
        window.setScene(dashboardPage);
    }

    private VBox createMainContent(){
        VBox main = new VBox(10);

        HBox top = createMainTop("Admin Dashboard", "Hanni Pham", "Admin");
        StackPane userPanel = createUserPanel("Hanni Pham", "Senior Admin Janitor", "Davao");
        HBox mainHeader = createMainHeader();
        VBox table = createTable();

        main.getChildren().addAll(top, userPanel, mainHeader, table);
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
        TextField searchEmployee = new TextField();
        searchEmployee.setPromptText("Search Employee");
        searchEmployee.getStyleClass().add("search");
        searchEmployee.setAlignment(Pos.CENTER_LEFT);
        
        header.getChildren().addAll(headerTitle, searchEmployee);
        
        return header;
    }
    
    private VBox createTable(){
        TableColumn<Employee, Integer> ID_Column = new TableColumn<>("Employee ID");
        ID_Column.setMinWidth(100);
        ID_Column.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("ID"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Employee Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));

        TableColumn<Employee, Integer> pay_per_hourColumn = new TableColumn<>("Pay/Hour");
        pay_per_hourColumn.setMinWidth(80);
        pay_per_hourColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("pay_per_hour"));

        TableColumn<Employee, Integer> total_hours_workColumn = new TableColumn<>("Total Hours Worked");
        total_hours_workColumn.setMinWidth(160);
        total_hours_workColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("total_hours_work"));
        
        TableColumn<Employee, Integer> total_overtimeColumn = new TableColumn<>("Total Overtime");
        total_overtimeColumn.setMinWidth(130);
        total_overtimeColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("total_overtime"));

        TableColumn<Employee, Integer> gross_payColumn = new TableColumn<>("Gross Pay");
        gross_payColumn.setMinWidth(80);
        gross_payColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("gross_pay"));

        table = new TableView<>();
        table.setItems(getEmployees());
        table.getColumns().addAll(ID_Column, nameColumn, pay_per_hourColumn, total_hours_workColumn, total_overtimeColumn, gross_payColumn);

        VBox vBox = new VBox(table);
        return vBox;
    }

    private ObservableList<Employee> getEmployees(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.add(new Employee("1","Laurence Lesmoras","2000","40","5","5000"));
        employees.add(new Employee("2","Laurence Kharl Devera","2000","40","5","5000"));
        employees.add(new Employee("3","Meriah Borja","2000","40","5","5000"));
      
        return employees;
    }
}
