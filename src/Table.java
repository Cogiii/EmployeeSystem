import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Table {
    public VBox createTable(Stage window, String[][] tableLists, TextField search, TableView<Employee> table, ObservableList<Employee> employees) {
        // Create Header Columns from the table lists
        ArrayList<TableColumn<Employee, String>> columns = new ArrayList<>();;
        for (String[] header : tableLists) {
            String headerLabel = header[0];
            String employeeVariable = header[1];

            int size = (headerLabel.length()*headerLabel.length())+15;

            TableColumn<Employee, String> column = new TableColumn<>(headerLabel);
            column.setMinWidth(size);
            column.setCellValueFactory(new PropertyValueFactory<Employee, String>(employeeVariable));

            columns.add(column);
        }

        // Initialize the table and data
        table = new TableView<>();  
        employees = getEmployees();

        // sort data dynamically with search
        SortedList<Employee> sortedData = new SortedList<>(searchData(employees, search));
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        // add all data 
        for (TableColumn<Employee, String> addColumn : columns) {
            table.getColumns().add(addColumn);            
        }

        // Set row factory to handle row clicks
        table.setRowFactory(e -> {
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

    public FilteredList<Employee> searchData(ObservableList<Employee> employeeData, TextField search){
        // Create a filtered list
        FilteredList<Employee> filteredData = new FilteredList<>(employeeData, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
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

        return filteredData;
    }

    private ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        Path usersDataPath = Paths.get("data/employee.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;
            String employeeID, employeeName, department, designation, checkIn, checkOut;

            // Skip the first line/header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split("#");

                employeeID = (!employeeDetails[0].equals(" ")) ? employeeDetails[0] : "--";
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

    public TableView<Employee> getTable(TableView<Employee> table){
        return table;
    }

    // Method to update the table after changes (delete/update)
    public void updateTable(TableView<Employee> table, ObservableList<Employee> employees) {
        employees.clear();
        employees.addAll(getEmployees());
        table.refresh();
    }
}
