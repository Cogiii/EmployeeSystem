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
    
        ImageView userPicture = new ImageView(new Image("images/HanniPham.png"));
        userPicture.setFitWidth(50);
        userPicture.setFitHeight(50);
    
        topRight.getChildren().addAll(userLabel, userPicture);
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

        ImageView userPicture = new ImageView(new Image("images/HanniPham.png"));
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
        panel_data.getChildren().addAll(userPicture, userInfo);

        panel.getChildren().addAll(panel_data);
        return panel;
    }

    private HBox createMainHeader(){
        HBox header = new HBox();
        
        // Label for header title
        Label headerTitle = new Label("Employee Details");
        headerTitle.setAlignment(Pos.CENTER_LEFT); // Align left
        
        // Add a region to create space dynamically between title and search field
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS); 
        
        // TextField for searching employees
        TextField searchEmployee = new TextField();
        searchEmployee.setPromptText("Search Employee");
        searchEmployee.getStyleClass().add("search");
        searchEmployee.setAlignment(Pos.CENTER_LEFT);
        
        // Add a region to create space dynamically between search field and button
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        // Button for adding employees
        Button addEmployeeButton = new Button("Add Employee");
        addEmployeeButton.getStyleClass().add("add-button");
        addEmployeeButton.setAlignment(Pos.CENTER_RIGHT);
        
        header.getChildren().addAll(headerTitle, spacer1, searchEmployee, spacer2, addEmployeeButton);
        
        return header;
    }
    
    private VBox createTable(){
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
        designationColumn.setMinWidth(160);
        designationColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("designation"));
        
        TableColumn<Employee, String> check_inColumn = new TableColumn<>("Check In");
        check_inColumn.setMinWidth(80);
        check_inColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("check_in"));

        TableColumn<Employee, String> check_outColumn = new TableColumn<>("Check Out");
        check_outColumn.setMinWidth(80);
        check_outColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("check_out"));

        table = new TableView<>();
        table.setItems(getEmployees());
        table.getColumns().addAll(ID_Column, nameColumn, departmentColumn, designationColumn, check_inColumn, check_outColumn);

        VBox vBox = new VBox(table);
        return vBox;
    }

    private ObservableList<Employee> getEmployees(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.add(new Employee(1,"Laurence Lesmoras","IT","Software Engineer","8:15AM","4:30PM"));
        employees.add(new Employee(2,"Laurence Kharl Devera","IT","Software Engineer","8:10AM","--"));
        employees.add(new Employee(3,"Meriah Borja","Accounting","Accountant","--","--"));
      
        return employees;
    }
}
