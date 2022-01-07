// base package
package supermarketFinal;

// Importing required module, libary, and package
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import supermarketFinal.classes.FxmlLoader;

// Main class extends Application
public class Main extends Application {
    
    private final FxmlLoader loader = new FxmlLoader();
    
    // Start the JavaFX application
    @Override
    public void start(Stage stage) throws Exception {
        loader.showStage("/supermarketFinal/fxml/Login.fxml");
    }

    public static void main(String[] args) {
        // Launch application
        launch(args);
    } 
}