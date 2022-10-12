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

/** This class controls the ModifyProduct.fxml document and allows the user to modify and update existing products. */
public class ModifyProductController implements Initializable {

    @FXML
    private TableColumn<?, ?> associatedPartIdCol;

    @FXML
    private TableColumn<?, ?> associatedPartInvLvlCol;

    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedPartPriceCol;

    @FXML
    private TextField productIdText;

    @FXML
    private TextField productInvText;

    @FXML
    private TextField productMaxText;

    @FXML
    private TextField productMinText;

    @FXML
    private TextField productNameText;

    @FXML
    private TextField productPriceText;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partInvLvlCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TextField partSearchBar;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int productId;

    @FXML
    private Label exceptionTextInv;

    @FXML
    private Label exceptionTextMax;

    @FXML
    private Label exceptionTextMin;

    @FXML
    private Label exceptionTextName;

    @FXML
    private Label exceptionTextPrice;

    @FXML
    private Label invException;

    @FXML
    private Label maxException;

    @FXML
    private Label minException;

    @FXML
    private Label nameException;

    @FXML
    private Label priceException;

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

    /** This method adds an associated part.
     This method is called when a user wants to add a new associated part to an existing product.
     @param event clicking the 'Add' button
     */
    @FXML
    public void onActionAddAssociatedPart(ActionEvent event) throws IOException {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        if (part != null) {
            associatedParts.add(part);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.showAndWait();
        }
    }
    /** This method populates textfields with existing product data.
     This method sets the existing product data sent from the main page for a user to modify.
     @param product an existing Product object.
     */
    public void sendProduct(Product product) {
        productIdText.setText(String.valueOf(product.getId()));
        productNameText.setText(product.getName());
        productInvText.setText(String.valueOf(product.getStock()));
        productPriceText.setText(String.valueOf(product.getPrice()));
        productMaxText.setText(String.valueOf(product.getMax()));
        productMinText.setText(String.valueOf(product.getMin()));
    }

    /** This method returns the user to the main page.
     This method sends unchanged associated part data back to the main page and switches the users view.
     @param event clicking the 'Cancel' button
     */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageInventory.fxml"));
        Parent root = loader.load();

        MainPageInventoryController mainController = loader.getController();
        mainController.updateAllAssociatedParts(productId, associatedParts);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** This method removes an associated part.
     This method removes the selected associated part from the product
     @param event clicking the 'Remove Associated Part' button
     */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent event) throws IOException {
        Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alertRemove = new Alert(Alert.AlertType.ERROR, "Please select a part to delete");
            alertRemove.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
            Optional<ButtonType> alertResult = alert.showAndWait();
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                associatedParts.remove(part);
            }
        }
    }

    /** This method checks if a textfield is a double.
     @param text user input from a textfield
     @return true if double false if not
     */
    public boolean isDouble(TextField text) {
        try {
            double num = Double.parseDouble(text.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /** This method checks if a textfield is an integer.
     @param text user input from a textfield
     @return true if integer false if not
     */
    public boolean isInteger(TextField text) {
        try {
            int num = Integer.parseInt(text.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /** This method sets all exception labels blank.
     This method is used to clear exception errors that have been fixed. */
    public void clearExceptionText() {
        exceptionTextName.setText("");
        nameException.setText("");
        exceptionTextInv.setText("");
        invException.setText("");
        exceptionTextPrice.setText("");
        priceException.setText("");
        exceptionTextMax.setText("");
        maxException.setText("");
        exceptionTextMin.setText("");
        minException.setText("");
    }

    /** This method saves a product.
     This method is called when modifications to an existing product are made.
     @param event clicking the 'Save' button
     */
    @FXML
    public void onActionSave(ActionEvent event) {
        boolean valid = true;
        clearExceptionText();
        try {
            int productId = Integer.parseInt(productIdText.getText());
            String productName = String.valueOf(productNameText.getText());
            int productInv = Integer.parseInt(productInvText.getText());
            double productPrice = Double.parseDouble(productPriceText.getText());
            int productMin = Integer.parseInt(productMinText.getText());
            int productMax = Integer.parseInt(productMaxText.getText());

            if (String.valueOf(productNameText.getText()).isEmpty()) {
                throw new IOException();
            }

            if (productMax <= productMin || (productInv < productMin || productInv > productMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid Max, Min, and Inv values.");
                alert.showAndWait();
                valid = false;
            }
            if (valid) {
                Inventory.updateProduct(productId, new Product(productId, productName, productPrice, productInv, productMin, productMax));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageInventory.fxml"));
                Parent root = loader.load();

                MainPageInventoryController mainController = loader.getController();
                mainController.updateAllAssociatedParts(this.productId, associatedParts);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (NumberFormatException | IOException e) {
            if (String.valueOf(productNameText.getText()).isEmpty()) {
                exceptionTextName.setText("Exception: ");
                nameException.setText("No data in Name field");
            }
            if (!(isInteger(productInvText))) {
                exceptionTextInv.setText("Exception: ");
                invException.setText("Inv is not an integer value");
            }
            if (!(isDouble(productPriceText) || isInteger(productPriceText))) {
                exceptionTextPrice.setText("Exception: ");
                priceException.setText("Price is not a double value");
            }
            if (!(isInteger(productMaxText))) {
                exceptionTextMax.setText("Exception: ");
                maxException.setText("Max is not an integer value");
            }
            if (!(isInteger(productMinText))) {
                exceptionTextMin.setText("Exception: ");
                minException.setText("Min is not an integer value");
            }
        }
    }
    /** This method searches for parts.
     This method is called when a user searches for a part by full/partial name or id number.
     @param event A key typed in the search bar
     */
    @FXML
    public void onActionSearchPart(KeyEvent event) {
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
    /** This method sets the associated parts tableview.
     This method copies existing associated part data and allows the user to add/remove associated parts.
     @param productId an existing Product ID
     @param associatedParts an existing list of associated parts
     */
    public void sendAssociatedPartsData(int productId, ObservableList<Part> associatedParts) {
        this.productId = productId;
        this.associatedParts = associatedParts;
        associatedPartTableView.setItems(this.associatedParts);
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

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
