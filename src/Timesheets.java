import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Timesheets {
    TableView<EmployeeTimesheets> table;

    SidebarPanel sidebarPanel = new SidebarPanel();

    public void showTimesheets(Stage window){
        HBox layout = new HBox();
        Scene dashboardPage = new Scene(layout, 1000, 600);
    
        VBox sidebar = sidebarPanel.createSidebar(window, dashboardPage, "timesheets");
        VBox mainContent = createMainContent();
    
        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(mainContent, new Insets(30,10,10,10));
        layout.getChildren().addAll(sidebar, mainContent);
    
        dashboardPage.getStylesheets().add("css/main.css");
        window.setTitle("Employee Management System");
        window.setScene(dashboardPage);
        
    }
    // Time sheets Main Content
    private VBox createMainContent(){
        DashboardPage getLayout = new DashboardPage();

        VBox main = new VBox(10);

        HBox top = getLayout.createMainTop("Admin Dashboard", "Hanni Pham", "Admin");
        StackPane userPanel = getLayout.createUserPanel("Hanni Pham", "Senior Admin Janitor", "Davao");
        HBox mainHeader = createMainHeader();
        VBox table = createTable();

        main.getChildren().addAll(top, userPanel, mainHeader, table);
        return main;
    }

    private HBox createMainHeader(){
        HBox header = new HBox();
        
        // Label for header title
        Label headerTitle = new Label("Timesheets");
        headerTitle.setAlignment(Pos.CENTER_LEFT); // Align left
        
        header.getChildren().addAll(headerTitle);
        
        return header;
    }
    
    private VBox createTable(){
        TableColumn<EmployeeTimesheets, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<EmployeeTimesheets, String> inMorningColumn = new TableColumn<>("Check-In Morning");
        inMorningColumn.setMinWidth(150);
        inMorningColumn.setCellValueFactory(new PropertyValueFactory<>("check_in_AM"));

        TableColumn<EmployeeTimesheets, String> outMorningColumn = new TableColumn<>("Check-Out Morning");
        outMorningColumn.setMinWidth(150);
        outMorningColumn.setCellValueFactory(new PropertyValueFactory<>("check_out_AM"));
        
        TableColumn<EmployeeTimesheets, String> inAfternoonColumn = new TableColumn<>("Check-In Afternoon");
        inAfternoonColumn.setMinWidth(150);
        inAfternoonColumn.setCellValueFactory(new PropertyValueFactory<>("check_in_PM"));

        TableColumn<EmployeeTimesheets, String> outAfternoonColumn = new TableColumn<>("Check-Out Afternoon");
        outAfternoonColumn.setMinWidth(150);
        outAfternoonColumn.setCellValueFactory(new PropertyValueFactory<>("check_out_PM"));

        table = new TableView<>();
        table.setItems(getEmployees());
        table.getColumns().addAll(dateColumn, inMorningColumn, outMorningColumn, inAfternoonColumn, outAfternoonColumn);

        VBox vBox = new VBox(table);
        return vBox;
    }

    private ObservableList<EmployeeTimesheets> getEmployees(){
        ObservableList<EmployeeTimesheets> employees = FXCollections.observableArrayList();
        employees.add(new EmployeeTimesheets("Monday, June 10 2024 ","8:15AM","12:03PM","8:15AM","4:30PM"));
        employees.add(new EmployeeTimesheets("Saturday, June 8 2024","8:10AM","12:01PM","8:15AM","4:30PM"));
        employees.add(new EmployeeTimesheets("Friday, June 7 2024","8:15AM","12:03PM","8:15AM","4:30PM"));
      
        return employees;
    }
}
