package collins.inventorysystem;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the MainPageInventory.fxml document and is the home base of all operations in the system.  */
public class MainPageInventoryController implements Initializable {
    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partInvLvlCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productInvLvlCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TextField productSearchBar;

    @FXML
    private TableView<Product> productTableView;
    public static ObservableList<ObservableList> allAssociatedPartsData = FXCollections.observableArrayList();

    /** This method adds a products associated parts.
     This method adds associated parts from a new product upon its initial creation.
     @param productId a new Product ID
     @param data The initial ObservableList of associated parts
     */
    public void addToAllAssociatedParts(int productId, ObservableList<Part> data) {
        allAssociatedPartsData.add(productId - 1, data);
    }

    /** This method updates a products associated parts.
     This method updates a products associated parts when modified.
     @param productId an existing Product ID
     @param updatedData an ObservableList of updated associated parts
     */
    public void updateAllAssociatedParts(int productId, ObservableList<Part> updatedData) {
        allAssociatedPartsData.set(productId - 1, updatedData);
    }

    /** This method switches the user's view.
     This method is called when the user needs to be redirected to a different view.
     @param event an ActionEvent
     @param viewName an FXML file
     */
    public void switchView(String viewName, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(viewName));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method directs a user to add a new part.
     This method is called when the 'Add' button is clicked on MainPageInventory.fxml and takes the user to the AddPart.fxml view.
     @param event clicking the 'Add' button for Parts
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {
        switchView("AddPart.fxml", event);
    }
    
    /** This method directs a user to add a new product.
     This method is called when the 'Add' button is clicked on MainPageInventory.fxml and takes the user to the addProduct.fxml view.
     @param event clicking the 'Add' button for products
     */
    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {
        switchView("AddProduct.fxml", event);
    }

    /** This method deletes a part.
     This method is called when a user wants to delete a part from the main page inventory.
     <p><b>
     FUTURE ENHANCEMENT
     When a part or product is deleted, the id does not stay contiguous. For example, if there are 5 parts with id's 1-5
     and part with id 3 is deleted the ids would now be 1,2,4,5. If a new part was added, it would take id 6, and id 3 would
     never be recycled. It would be useful to have a contiguous set of ids that updated based on deletion also so the user
     knew how many parts were in the system as the highest id number would be the total number of parts. After some time,
     part and product id's could have substantial gaps and the order wouldn't make sense. part id 5 could be followed by part id 123.
     Having some structure to the ids could help categorize parts visually. All in house parts could start with 1XX, outsoruced with 2XX.
     </b></p>
     @param event clicking the 'Delete' button for Parts
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) {
        ObservableList<Part> parts = partTableView.getItems();
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            Optional<ButtonType> alertResult = alert.showAndWait();
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                parts.remove(selectedPart);
            }
            partTableView.setItems(parts);
        }
    }

    /** This method deletes a product.
     This method is called when a user wants to delete a product from the main page inventory.
     @param event clicking the 'Delete' button for products
     */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) throws IOException {
        ObservableList<Product> products = productTableView.getItems();
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to delete");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            Optional<ButtonType> alertResult = alert.showAndWait();
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                try {
                    int productId = selectedProduct.getId();

                    if (allAssociatedPartsData.get(productId - 1).isEmpty()) {
                        products.remove(selectedProduct);
                    } else {
                        throw new IOException();

                    }
                } catch (IOException e) {
                    Alert partsAlert = new Alert(Alert.AlertType.ERROR, "This product has associated parts. Please remove them before deleting.");
                    partsAlert.showAndWait();
                }

            }
        }
        productTableView.setItems(products);
    }
    /** This method kills the application.
     This method is called when the 'Exit' button is clicked on the main page.
     @param event clicking the 'Exit' button
     */
    @FXML
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /** This method directs a user to modify a part.
     This method is called when the user wants to modify any details about an existing part.
     @param event clicking the 'Modify' button for parts
     */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPart.fxml"));
            loader.load();

            ModifyPartController mpcController = loader.getController();
            mpcController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            Stage stage = stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to modify");
            alert.showAndWait();
        }
    }

    /** This method directs a user to modify a product.
     This method is called when the user wants to modify any details about an existing product.
     @param event clicking the 'Modify' button for products
     */
    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
            loader.load();

            ModifyProductController mpcController = loader.getController();
            mpcController.sendProduct(productTableView.getSelectionModel().getSelectedItem());
            int selectedProductId = productTableView.getSelectionModel().getSelectedItem().getId();
            mpcController.sendAssociatedPartsData(selectedProductId, allAssociatedPartsData.get(selectedProductId - 1));

            Stage stage = stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify");
            alert.showAndWait();
        }
    }
    /** This method searches for a product.
     This method is called when a user wants to search a product by name or id number.
     @param event A key typed in the search bar for products
     */
    @FXML
    public void searchProduct(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String search = productSearchBar.getText();
            ObservableList<Product> products = Inventory.lookupProduct(search);
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Parts found");
            if (products.isEmpty()) {
                try {
                    int id = Integer.parseInt(search);
                    Product product = Inventory.lookupProduct(id);
                    if (product != null) {
                        productTableView.getSelectionModel().select(id - 1);
                        productTableView.setItems(Inventory.getAllProducts());
                    }
                    else {
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    alert.showAndWait();
                }
            } else {
                productTableView.setItems(products);
            }
        }
    }

    /** This method searches for a part.
     This method is called when a user wants to search a part by name or id number.
     @param event A key typed in the search bar for parts
     */
    @FXML
    public void searchPart(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String search = partSearchBar.getText();
            ObservableList<Part> parts = Inventory.lookupPart(search);
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Parts found");
            if (parts.isEmpty()) {
                try {
                    int id = Integer.parseInt(search);
                    Part part = Inventory.lookupPart(id);
                    if (part != null) {
                        partTableView.getSelectionModel().select(id - 1);
                        partTableView.setItems(Inventory.getAllParts());
                    }
                    else {
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    alert.showAndWait();
                }
            } else {
                partTableView.setItems(parts);
            }
        }
    }

    /** This method is called when the controller is first invoked.
     This sets the initial tableviews and column headers.
     @param url URL
     @param resourceBundle A resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}


