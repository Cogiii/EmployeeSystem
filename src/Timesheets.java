import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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

        lateChecker(inMorningColumn, 8);
        lateChecker(inAfternoonColumn, 13);

        table = new TableView<>();
        table.setItems(getTimesheetsEmployees());
        table.getColumns().addAll(dateColumn, inMorningColumn, outMorningColumn, inAfternoonColumn, outAfternoonColumn);

        VBox vBox = new VBox(table);
        return vBox;
    }

    private ObservableList<EmployeeTimesheets> getTimesheetsEmployees(){
        ObservableList<EmployeeTimesheets> employees = FXCollections.observableArrayList(); // store all employees data
        ArrayList<HashMap<String, String>> timesheetsData = new ArrayList<>();
        
        Path usersDataPath = Paths.get("data/timesheets.txt");
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");

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

            // Add EmployeeTimesheets objects to employees list in reverse order and limit to the last 7 days
            for (int i = 0; i < 8; i++) {
                LocalDate prevDate = currentDate.minusDays(i);
                String date = prevDate.format(formatter);
                boolean dateExist = false;
                
                for (int j = 0; j < timesheetsData.size(); j++) {
                    HashMap<String, String> userTimesheets = timesheetsData.get(j);

                    if (date.equals(userTimesheets.get("date"))) {
                        employees.add(new EmployeeTimesheets(
                            userTimesheets.get("date"),
                            userTimesheets.get("timeInAM"),
                            userTimesheets.get("timeOutAM"),
                            userTimesheets.get("timeInPM"),
                            userTimesheets.get("timeOutPM")
                        ));
                        dateExist = true;
                        break;
                    }
                }

                if (!dateExist) {
                    HashMap<String, String> newEntry = new HashMap<>();
                    newEntry.put("ID", userData.get("ID"));
                    newEntry.put("date", date);
                    newEntry.put("timeInAM", "--");
                    newEntry.put("timeOutAM", "--");
                    newEntry.put("timeInPM", "--");
                    newEntry.put("timeOutPM", "--");
                    
                    // Write the new entry to the file
                    List<String> newEntryList = new ArrayList<>();
                    newEntryList.add(userData.get("ID") + "#" + date + "#--#--#--#--");
                    Files.write(usersDataPath, newEntryList, StandardOpenOption.APPEND);

                    employees.add(new EmployeeTimesheets(
                        newEntry.get("date"),
                        newEntry.get("timeInAM"),
                        newEntry.get("timeOutAM"),
                        newEntry.get("timeInPM"),
                        newEntry.get("timeOutPM")
                    ));
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return employees;
    }

    public void lateChecker(TableColumn<EmployeeTimesheets, String> column, int timeLate){
        // Add custom cell factory to inMorningColumn and inAfternoonColumn to change text color
        column.setCellFactory(new Callback<TableColumn<EmployeeTimesheets, String>, TableCell<EmployeeTimesheets, String>>() {
            @Override
            public TableCell<EmployeeTimesheets, String> call(TableColumn<EmployeeTimesheets, String> param) {
                return new TableCell<EmployeeTimesheets, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if (!item.equals("--")) {
                                try {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
                                    LocalTime time = LocalTime.parse(item, formatter);
                                    if (time.isAfter(LocalTime.of(timeLate, 0))) {
                                        setStyle("-fx-text-fill: red;");
                                    } else {
                                        setStyle("");
                                    }
                                } catch (DateTimeParseException e) {
                                    setStyle(""); // reset style if parsing fails
                                }
                            }
                        }
                    }
                };
            }
        });
    }
}
