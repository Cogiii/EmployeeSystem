import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class DashboardPage {
    SidebarPanel sidebarPanel = new SidebarPanel();
    Table table = new Table();
    Data data = new Data();

    TableView<Employee> tableData;
    TextField searchEmployee;
    Stage mainStage;
    ObservableList<Employee> employees = FXCollections.observableArrayList(); // Make employees a class-level field
    HashMap<String, String> userData = new HashMap<>();

    public void showDashboard(Stage window, String ID) {
        mainStage = window;
        userData = data.getUserData(ID);

        HBox layout = new HBox();
        Scene dashboardPage = new Scene(layout, 1000, 600);

        VBox sidebar = sidebarPanel.createAdminSidebar(mainStage, ID, dashboardPage, "dashboard");
        VBox mainContent = createMainContent();

        HBox.setMargin(sidebar, new Insets(10));
        HBox.setMargin(mainContent, new Insets(30, 10, 10, 10));
        layout.getChildren().addAll(sidebar, mainContent);

        dashboardPage.getStylesheets().add("css/main.css");
        mainStage.setTitle("Employee Management System");
        mainStage.setScene(dashboardPage);
    }

    private VBox createMainContent() {
        String[][] tableHeader = {{"ID", "ID", "70"}, {"Employee Name", "name", "200"}, {"Department", "department", "125"}, {"Designation", "designation", "150"}, {"Time-In", "timeIn", "100"}, {"Time-Out", "timeOut", "100"}};

        VBox main = new VBox(10);

        HBox top = createMainTop("Dashboard Page", userData.get("name"), userData.get("designation"));
        StackPane userPanel = createUserPanel(userData.get("name"), userData.get("designation"), userData.get("address"));
        HBox mainHeader = createMainHeader();
        VBox mainTable = table.createTable(mainStage, tableHeader, searchEmployee, tableData, employees, "dashboard", userData.get("ID"));

        main.getChildren().addAll(top, userPanel, mainHeader, mainTable);
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

        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("top-user_name");

        Label userPositionLabel = new Label(position);
        userPositionLabel.getStyleClass().add("top-user_position");
        if (position.equals("Admin"))
            userPositionLabel.getStyleClass().add("admin_position");

        userLabel.getChildren().addAll(usernameLabel, userPositionLabel);

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
        if (position.equals("Admin")) 
            user_position.getStyleClass().add("admin_position");

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
            addModal.showAddModal(mainStage, userData.get("ID"));
        });

        header.getChildren().addAll(headerTitle, spacer1, searchEmployee, spacer2, addEmployeeButton);

        return header;
    }
}
