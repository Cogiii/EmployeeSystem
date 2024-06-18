import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
    public VBox createTable(Stage window, String[][] tableLists, TextField search, TableView<Employee> table, ObservableList<Employee> employees, String from) {

        // Create Header Columns from the table lists
        ArrayList<TableColumn<Employee, String>> columns = new ArrayList<>();
        for (String[] header : tableLists) {
            String headerLabel = header[0];
            String employeeVariable = header[1];
            int size = Integer.parseInt(header[2]);

            TableColumn<Employee, String> column = new TableColumn<>(headerLabel);
            column.setMinWidth(size);
            column.setCellValueFactory(new PropertyValueFactory<Employee, String>(employeeVariable));

            columns.add(column);
        }

        // Initialize the table and data
        table = new TableView<>();  
        employees = getEmployees(tableLists, from);

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
                    if (from.equals("dashboard")){
                        Employee rowData = row.getItem();
                        ViewEmployeeModal viewEmployee = new ViewEmployeeModal();
                        viewEmployee.showEmployeeDetails(window, rowData);
                    }else if (from.equals("payroll")){
                        // View Payroll functions
                    }
                    
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

    private ObservableList<Employee> getEmployees(String[][] tableLists, String from) {
        ObservableList<Employee> employees = FXCollections.observableArrayList(); // store all employees data
        HashMap<String, String> data = new HashMap<>(); // store data that get from the employee.txt

        Path usersDataPath = Paths.get("data/employee.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(usersDataPath.toFile()))) {
            String line;

            // Get the header of the text file and add the key to the data(map)
            line = br.readLine();
            String[] dataHeader = line.split("#");
            for (String header : dataHeader) {
                data.put(header, "");
            }

            // check if line exists then get the data and add it to the employees
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split("#");
                for (int i = 0; i < employeeDetails.length; i++) {
                    data.put(dataHeader[i], (!employeeDetails[i].equals(" ")) ? employeeDetails[i] : "--");
                }
                
                if (data.get("status").equals("active")){
                    if (from.equals("dashboard")) 
                        employees.add(new Employee(data.get(tableLists[0][1]), data.get(tableLists[1][1]), data.get(tableLists[2][1]), data.get(tableLists[3][1]), data.get(tableLists[4][1]), data.get(tableLists[5][1])));
                    else if (from.equals("payroll"))
                        employees.add(new Employee(data.get(tableLists[0][1]), data.get(tableLists[1][1]), data.get(tableLists[2][1]), data.get(tableLists[3][1]), data.get(tableLists[4][1]), data.get(tableLists[5][1]), ""));
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
    public void updateTable(TableView<Employee> table, ObservableList<Employee> employees,  String[][] tableLists, String from) {
        employees.clear();
        employees.addAll(getEmployees(tableLists, from));
        table.refresh();
    }
}
