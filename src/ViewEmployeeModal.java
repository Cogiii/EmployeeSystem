import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewEmployeeModal {
    void showEmployeeDetails(Employee employee) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Employee Details");

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10));

        Label titleLabel = new Label("View an Employee");
        StackPane userImage = showImage();

        Label nameLabel = new Label("Employee Name: " + employee.getName());
        detailsLayout.setAlignment(Pos.TOP_LEFT);
        detailsLayout.getChildren().addAll(titleLabel, userImage, nameLabel);

        Scene detailsScene = new Scene(detailsLayout, 600, 500);
        Image icon = new Image("images/logo-icon.png");
        detailsStage.getIcons().add(icon);
        detailsScene.getStylesheets().add("css/main.css");
        detailsStage.setScene(detailsScene);
        detailsStage.showAndWait();
        
    }

    private StackPane showImage(){
        Image originalImage = new Image("images/userImage/hannipham.jpg");

        // Calculate dimensions for the square
        double squareSize = Math.min(originalImage.getWidth(), originalImage.getHeight());
        double startX = (originalImage.getWidth() - squareSize) / 2;
        double startY = (originalImage.getHeight() - squareSize) / 2;

        // Create a viewport to crop the original image to square
        Rectangle2D viewportRect = new Rectangle2D(startX, startY, squareSize, squareSize);

        ImageView userPicture = new ImageView(originalImage);
        userPicture.setViewport(viewportRect);
        userPicture.setFitWidth(150);
        userPicture.setFitHeight(150);
        userPicture.setPreserveRatio(false);

        // Create a Rectangle with rounded corners (as a clipping mask)
        Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(20); // Adjust the arc width as needed
        clip.setArcHeight(20); // Adjust the arc height as needed

        // Apply clipping to the ImageView
        userPicture.setClip(clip);

        // Create a StackPane and add the Rectangle and ImageView
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(userPicture);

        return stackPane;
    }
}
