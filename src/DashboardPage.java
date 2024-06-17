import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardPage {
    TableView<Employee> table;
    SidebarPanel sidebarPanel = new SidebarPanel();
    TextField searchEmployee;
    ObservableList<Employee> employees = FXCollections.observableArrayList(); // Make employees a class-level field

    public void showDashboard(Stage window) {
        HBox layout = new HBox();
        Scene dashboardPage = new Scene(layout, 1000, 600);

        VBox sidebar = sidebarPanel.createSidebar(window, dashboardPage, "dashboard");
        VBox mainContent = createMainContent(window);

        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(mainContent, new Insets(30, 10, 10, 10));
        layout.getChildren().addAll(sidebar, mainContent);

        dashboardPage.getStylesheets().add("css/main.css");
        window.setTitle("Employee Management System");
        window.setScene(dashboardPage);
    }

    private VBox createMainContent(Stage window) {
        VBox main = new VBox(10);

        HBox top = createMainTop("Admin Dashboard", "Hanni Pham", "Admin");
        StackPane userPanel = createUserPanel("Hanni Pham", "Senior Admin Janitor", "Davao");
        HBox mainHeader = createMainHeader();
        VBox table = createTable(window);

        main.getChildren().addAll(top, userPanel, mainHeader, table);
        return main;
    }

    HBox createMainTop(String title, String username, String position) {
        HBox top = new HBox();

        Label titleLabel = new Label(title);
        HBox.setMargin(titleLabel, new Insets(0, 10, 0, 0));

        HBox topRight = new HBox();
        topRight.setAlignment(Pos.CENTER_RIGHT);

        VBox userLabel = new VBox();
        userLabel.setAlignment(Pos.CENTER_RIGHT); // Align labels to the center right

        Label userName = new Label("Hanni Pham");
        userName.getStyleClass().add("top-user_name");

        Label userPosition = new Label("Admin");
        userPosition.getStyleClass().add("top-user_position");

        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("top-user_name");

        Label userPositionLabel = new Label(position);
        userPositionLabel.getStyleClass().add("top-user_position");

        userLabel.getChildren().addAll(userName, userPosition);

        // IMAGE CODE -- CURVED EDGES

        Image originalImage = new Image("images/userImage/hannipham.jpg");

        // Calculate dimensions for the square
        double squareSize = Math.min(originalImage.getWidth(), originalImage.getHeight());
        double startX = (originalImage.getWidth() - squareSize) / 2;
        double startY = (originalImage.getHeight() - squareSize) / 2;

        //
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

        // END IMAGE EDGE CURVE CODE

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

    StackPane createUserPanel(String username, String position, String location) {
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
        loc_data.getChildren().addAll(locationLabel, user_Location);

        userInfo.getChildren().addAll(user_name, user_position, loc_data);
        panel_data.getChildren().addAll(userPicture, userInfo);

        panel.getChildren().addAll(panel_data);
        return panel;
    }

    private HBox createMainHeader() {
        HBox header = new HBox();

        // Label for header title
        Label headerTitle = new Label("Employee Details");
        headerTitle.setAlignment(Pos.CENTER_LEFT); // Align left

        // Add a region to create space dynamically between title and search field
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        // TextField for searching employees
        searchEmployee = new TextField();
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
        addEmployeeButton.setOnAction(e -> {
            AddEmployeeModal addModal = new AddEmployeeModal();
            addModal.showAddModal();
        });

        header.getChildren().addAll(headerTitle, spacer1, searchEmployee, spacer2, addEmployeeButton);

        return header;
    }

    private VBox createTable(Stage window) {
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

        table = new TableView<>();  // Initialize the table here

        employees = getEmployees();

        // Create a filtered list
        FilteredList<Employee> filteredData = new FilteredList<>(employees, p -> true);

        searchEmployee.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (employee.getDepartment().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getDesignation().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(employee.getID()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
        table.getColumns().addAll(ID_Column, nameColumn, departmentColumn, designationColumn, check_inColumn, check_outColumn);

        // Set row factory to handle row clicks
        table.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Employee rowData = row.getItem();
                    ViewEmployeeModal viewEmployee = new ViewEmployeeModal();
                    viewEmployee.showEmployeeDetails(window, rowData);  // Pass the DashboardPage instance
                }
            });
            return row;
        });

        VBox vBox = new VBox(table);
        return vBox;
    }

    private ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        Path usersDataPath = Paths.get("data/employee.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;
            int employeeID;
            String employeeName, department, designation, checkIn, checkOut;

            // Skip the first line/header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split("#");

                employeeID = Integer.parseInt(employeeDetails[0]);
                employeeName = (!employeeDetails[1].equals(" ")) ? employeeDetails[1] : "--";
                department = (!employeeDetails[2].equals(" ")) ? employeeDetails[2] : "--";
                designation = (!employeeDetails[3].equals(" ")) ? employeeDetails[3] : "--";
                checkIn = (!employeeDetails[13].equals(" ")) ? employeeDetails[13] : "--";
                checkOut = (!employeeDetails[14].equals(" ")) ? employeeDetails[14] : "--";

                if (employeeDetails.length >= 14) {
                    employees.add(new Employee(employeeID, employeeName, department, designation, checkIn, checkOut));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return employees;
    }

    // Method to update the table after changes (delete/update)
    public void updateTable() {
        employees.clear();
        employees.addAll(getEmployees());
        table.refresh();
    }
}
