import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardPage {
    TableView<Employee> table;

    public void showDashboard(Stage window) {
        HBox layout = new HBox();

        VBox sidebar = sideBarPanel();

        VBox main = main();

        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(main, new Insets(30,10,10,10));
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

        // Buttons for sidebar navigation
        Button dashboardButton = createSidebarButton("Dashboard", "images/article.png");
        dashboardButton.getStyleClass().add("active-Button");
        Button timesheetsButton = createSidebarButton("Timesheets", "images/calendar_month.png");
        Button payrollButton = createSidebarButton("Payroll", "images/contract.png");

        // Logout Button
        Button logoutButton = createSidebarButton("Log Out | Time Out", "images/door_open.png");
        logoutButton.getStyleClass().add("logout-button");

        // Spacer to push logoutButton to the bottom
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logo, titleLabel, dashboardButton, timesheetsButton, payrollButton, spacer, logoutButton);

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
        VBox main = new VBox(10);

        HBox top = topMain();

        StackPane userPanel = userPanel();

        HBox mainHeader = mainHeader();

        VBox table = table();
        

        main.getChildren().addAll(top, userPanel, mainHeader, table);

        return main;
    }

    private HBox topMain(){
        HBox top = new HBox();
    
        // Left side: Title
        Label title = new Label("Admin Dashboard");
        HBox.setMargin(title, new Insets(0, 10, 0, 0));
        
        // Right side: User information
        HBox topRight = new HBox();
        topRight.setAlignment(Pos.CENTER_RIGHT); // Align items to the right
    
        VBox userLabel = new VBox();
        userLabel.setAlignment(Pos.CENTER_RIGHT); // Align labels to the center right
    
        Label userName = new Label("Hanni Pham");
        userName.getStyleClass().add("top-user_name");
    
        Label userPosition = new Label("Admin");
        userPosition.getStyleClass().add("top-user_position");
    
        userLabel.getChildren().addAll(userName, userPosition);
    
        ImageView userPicture = new ImageView(new Image("images/HanniPham.png"));
        userPicture.setFitWidth(50);
        userPicture.setFitHeight(50);
    
        topRight.getChildren().addAll(userLabel, userPicture);
        topRight.setSpacing(5);

         // Add spacer to align user information to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        top.getChildren().addAll(title, spacer, topRight);
        HBox.setHgrow(topRight, Priority.ALWAYS); // Allow topRight to grow horizontally
        top.setAlignment(Pos.CENTER_LEFT);
    
        return top;
    }

    private StackPane userPanel(){
        StackPane panel = new StackPane();
        panel.getStyleClass().add("panel");
        
        HBox panel_data = new HBox(5);

        ImageView userPicture = new ImageView(new Image("images/HanniPham.png"));
        userPicture.getStyleClass().add("image");

        VBox userInfo = new VBox();
        Label user_name = new Label("Hanni Pham"); 
        user_name.getStyleClass().add("panel-user_name");
        Label user_position = new Label("Senior Admin Janitor");
        user_position.getStyleClass().add("panel-user_position");

        HBox loc_data = new HBox();
        Label locationLabel = new Label("Location: ");
        locationLabel.getStyleClass().add("loc-label");
        Label user_Location = new Label("Davao");
        user_Location.getStyleClass().add("loc-user_label");
        loc_data.setPadding(new Insets(60, 0, 0, 0));
        loc_data.getChildren().addAll(locationLabel,user_Location);

        userInfo.getChildren().addAll(user_name,user_position,loc_data);
        panel_data.getChildren().addAll(userPicture, userInfo);

        panel.getChildren().addAll(panel_data);
        return panel;
    }

    private HBox mainHeader(){
        HBox header = new HBox();
        
        // Label for header title
        Label headerTitle = new Label("Employee Details");
        headerTitle.setAlignment(Pos.CENTER_LEFT); // Align left
        
        // Add a region to create space between title and search field
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS); 
        
        // TextField for searching employees
        TextField searchEmployee = new TextField();
        searchEmployee.setPromptText("Search Employee");
        searchEmployee.getStyleClass().add("search");
        searchEmployee.setAlignment(Pos.CENTER_LEFT);
        
        // Add a region to create space between search field and button
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        // Button for adding employees
        Button addEmployeeButton = new Button("Add Employee");
        addEmployeeButton.getStyleClass().add("add-button");
        addEmployeeButton.setAlignment(Pos.CENTER_RIGHT);
        
        header.getChildren().addAll(headerTitle, spacer1, searchEmployee, spacer2, addEmployeeButton);
        
        return header;
    }
    
    private VBox table(){
        TableColumn<Employee, Integer> ID_Column = new TableColumn<>("Employee ID");
        ID_Column.setMinWidth(100);
        ID_Column.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("ID"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Employee Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setMinWidth(130);
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));

        TableColumn<Employee, String> designationColumn = new TableColumn<>("Designation");
        designationColumn.setMinWidth(150);
        designationColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("designation"));
        
        TableColumn<Employee, String> check_inColumn = new TableColumn<>("Check In");
        check_inColumn.setMinWidth(80);
        check_inColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("check_in"));

        TableColumn<Employee, String> check_outColumn = new TableColumn<>("Check Out");
        check_outColumn.setMinWidth(80);
        check_outColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("check_out"));

        table = new TableView<>();
        table.setItems(getEmployee());
        table.getColumns().addAll(ID_Column, nameColumn, departmentColumn, designationColumn, check_inColumn, check_outColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        return vBox;
    }

    private ObservableList<Employee> getEmployee(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.add(new Employee(1,"Laurence Lesmoras","IT","Software Engineer","8:15AM","4:30PM"));
        employees.add(new Employee(2,"Laurence Kharl Devera","IT","Software Engineer","8:10AM","--"));
        employees.add(new Employee(3,"Meriah Borja","Accounting","Accountant","--","--"));
        employees.add(new Employee(1,"Laurence Lesmoras","IT","Software Engineer","8:15AM","4:30PM"));
        employees.add(new Employee(2,"Laurence Kharl Devera","IT","Software Engineer","8:10AM","--"));
        employees.add(new Employee(3,"Meriah Borja","Accounting","Accountant","--","--"));
        employees.add(new Employee(1,"Laurence Lesmoras","IT","Software Engineer","8:15AM","4:30PM"));
        employees.add(new Employee(2,"Laurence Kharl Devera","IT","Software Engineer","8:10AM","--"));
        employees.add(new Employee(3,"Meriah Borja","Accounting","Accountant","--","--"));
        employees.add(new Employee(1,"Laurence Lesmoras","IT","Software Engineer","8:15AM","4:30PM"));
        employees.add(new Employee(2,"Laurence Kharl Devera","IT","Software Engineer","8:10AM","--"));
        employees.add(new Employee(3,"Meriah Borja","Accounting","Accountant","--","--"));
        employees.add(new Employee(1,"Laurence Lesmoras","IT","Software Engineer","8:15AM","4:30PM"));
        employees.add(new Employee(2,"Laurence Kharl Devera","IT","Software Engineer","8:10AM","--"));
        employees.add(new Employee(3,"Meriah Borja","Accounting","Accountant","--","--"));

        return employees;
    }
}
