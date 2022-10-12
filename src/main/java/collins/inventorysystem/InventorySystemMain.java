package collins.inventorysystem;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This class creates an inventory management system that allows for CRUD operations. */
public class InventorySystemMain extends Application {

    /** This method loads the main page.
     This is the first method that displays MainPageInventory.fxml when the program is run.
     @param stage a new stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystemMain.class.getResource("MainPageInventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    /** This method creates pre-set parts.
     This method preloads hardcoded parts into the parts tableview on the main page.
     */
    private static void setTestData() {
        InHouse brakes = new InHouse(1, "Brakes", 249.99, 5, 1, 10, 2390);
        InHouse tires = new InHouse(2, "Tires", 499.95, 10, 1, 15, 2211);
        Outsourced rims = new Outsourced(3, "Rims", 129.99, 9, 1, 20, "Specialized");

        Inventory.addPart(brakes);
        Inventory.addPart(tires);
        Inventory.addPart(rims);

    }
    /** This is the main method.
     This is the first method called when the program is run.
     @param args Main args method
     */
    public static void main(String[] args) {
        setTestData();
        launch();

    }
}