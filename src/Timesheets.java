import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
    SidebarPanel sidebarPanel = new SidebarPanel();
    DashboardPage getLayout = new DashboardPage();
    Data data = new Data();

    TableView<EmployeeTimesheets> table;
    Stage mainStage;
    HashMap<String, String> userData;

    public void showTimesheets(Stage window, String ID, String status){
        mainStage = window;
        userData = data.getUserData(ID);

        HBox layout = new HBox();
        Scene dashboardPage = new Scene(layout, 1000, 600);

        VBox sidebar;
        if (status.equals("admin"))
            sidebar = sidebarPanel.createAdminSidebar(mainStage, ID, dashboardPage, "timesheets");
        else
            sidebar = sidebarPanel.createEmployeeSidebar(mainStage, dashboardPage);
        VBox mainContent = createMainContent();
    
        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(mainContent, new Insets(30,10,10,10));
        layout.getChildren().addAll(sidebar, mainContent);
    
        dashboardPage.getStylesheets().add("css/main.css");
        mainStage.setTitle("Employee Management System");
        mainStage.setScene(dashboardPage);
        
    }
    // Time sheets Main Content
    public VBox createMainContent(){
        VBox main = new VBox(10);

        HBox top = getLayout.createMainTop("Timesheets Page", userData.get("name"), userData.get("designation"));
        StackPane userPanel = getLayout.createUserPanel(userData.get("name"), userData.get("designation"), userData.get("address"));
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
    
    @SuppressWarnings("unchecked")
    private VBox createTable(){
        TableColumn<EmployeeTimesheets, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<EmployeeTimesheets, String> inMorningColumn = new TableColumn<>("Time-In AM");
        inMorningColumn.setCellValueFactory(new PropertyValueFactory<>("timeInAM"));

        TableColumn<EmployeeTimesheets, String> outMorningColumn = new TableColumn<>("Time-Out AM");
        outMorningColumn.setCellValueFactory(new PropertyValueFactory<>("timeOutAM"));
        
        TableColumn<EmployeeTimesheets, String> inAfternoonColumn = new TableColumn<>("Time-In PM");
        inAfternoonColumn.setCellValueFactory(new PropertyValueFactory<>("timeInPM"));

        TableColumn<EmployeeTimesheets, String> outAfternoonColumn = new TableColumn<>("Time-Out PM");
        outAfternoonColumn.setCellValueFactory(new PropertyValueFactory<>("timeOutPM"));

        final double dateColumnWidth = 169; // already set
        final double widthPerColumn = 144;

        dateColumn.setMinWidth(dateColumnWidth);
        inMorningColumn.setMinWidth(widthPerColumn);
        outMorningColumn.setMinWidth(widthPerColumn);
        inAfternoonColumn.setMinWidth(widthPerColumn);
        outAfternoonColumn.setMinWidth(widthPerColumn);

        table = new TableView<>();
        table.setItems(getEmployees());
        table.getColumns().addAll(dateColumn, inMorningColumn, outMorningColumn, inAfternoonColumn, outAfternoonColumn);

        VBox vBox = new VBox(table);
        return vBox;
    }

    private ObservableList<EmployeeTimesheets> getEmployees(){
        ObservableList<EmployeeTimesheets> employees = FXCollections.observableArrayList(); // store all employees data
        ArrayList<HashMap<String, String>> timesheetsData = new ArrayList<>();
        
        Path usersDataPath = Paths.get("data/timesheets.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;
            
            // Get the header of the text file and add the key to the data(map)
            line = br.readLine();
            String[] dataHeader = line.split("#");
            
            // check if line exists then get the data and add it to the employees
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split("#");
                
                if (employeeDetails[0].equals(userData.get("ID"))){
                    HashMap<String, String> data = new HashMap<>(); // store data that get from the timesheets.txt
                    for (int i = 0; i < employeeDetails.length; i++) {
                        data.put(dataHeader[i], (!employeeDetails[i].equals(" ")) ? employeeDetails[i] : "--");
                    }
                    timesheetsData.add(data);
                }
            }

            // Add EmployeeTimesheets objects to employees list in reverse order
            for (int i = timesheetsData.size() - 1; i >= 0; i--) {
                HashMap<String, String> userTimesheets = timesheetsData.get(i);
                employees.add(new EmployeeTimesheets(
                    userTimesheets.get("date"),
                    userTimesheets.get("timeInAM"),
                    userTimesheets.get("timeOutAM"),
                    userTimesheets.get("timeInPM"),
                    userTimesheets.get("timeOutPM")
                ));
            }

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return employees;
    }
}
