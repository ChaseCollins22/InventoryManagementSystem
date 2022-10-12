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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Product;

/** This class controls the addProduct.fxml document and adds a product to the main Inventory. */
public class AddProductController implements Initializable {
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
    private TextField productNameText;

    @FXML
    private TextField productInvText;

    @FXML
    private TextField productPriceText;

    @FXML
    private TextField productMaxText;

    @FXML
    private TextField productMinText;

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
    private TextField partSearchBar;

    @FXML
    private TableView<Part> associatedPartTableView;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

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

    /** This method adds a part to the associated parts table.
     This method is called when the user selects a part to add to the associated parts table for a particular product.
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

    /** This method returns the user to the main page.
     This method is called when the user clicks the 'Cancel' button on the addProduct.fxml view.
     @param event clicking the 'Cancel' button
     */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        switchView("MainPageInventory.fxml", event);
    }

    /** This method removes an associated part.
     This method is called when the user wants to remove an associated part from a product.
     @param event clicking the 'Remove Associated Part' button
     */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent event) {
        Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alertRemove = new Alert(Alert.AlertType.ERROR, "Please select a part to remove");
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

    /** This method saves a product to the main inventory.
     This method is called when a user clicks the 'Save' button which adds a new product to the main page inventory.
     <p><b>
     LOGICAL ERROR
     Originally when adding a new product, the product's associated parts were copied from associatedParts over to another ObservableList
     in the main page controller. This worked fine and the data was sent over, but when multiple products were involved they would all share
     the same list of associated parts. When modifying any product the changes weren't sent back to the main page associated parts either and
     the list would be empty. In order to fix this I made the list static, but then realized that all products still shared the same associated
     parts data. I then made the main page associated parts data into an ObservableList of ObservableLists and created a method to add a new list
     when a product was created that contained the original associated parts data along with the products unique id. I then used this id to access
     the data at each index (which matched the product id - 1 after accounting for the 0 index). This allowed me to pass each products unique
     list of associated parts between the main page and the modification page.
     </b></p>
     @param event clicking the 'Save' button
     */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
        clearExceptionText();
        boolean valid = true;
        try {
            int productId;
            if (Inventory.getAllProducts().size() == 0) {
                productId = 1;
            } else {
                Product lastProduct = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1);
                productId = lastProduct.getId() + 1;
            }
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
                Inventory.addProduct(new Product(productId, productName, productPrice, productInv, productMin, productMax));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageInventory.fxml"));
                Parent root = loader.load();

                MainPageInventoryController mainController = loader.getController();
                mainController.addToAllAssociatedParts(productId, associatedParts);

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

    /** This method searches for a desired part.
     This method is called when the user wants to search for a particular part by name or id number.
     @param event a key pressed in the search bar
     */
    @FXML
    public void onActionSearchPart(KeyEvent event) {
        String search = partSearchBar.getText();
        ObservableList<Part> parts = Inventory.lookupPart(search);
        if (parts.isEmpty()) {
            try {
                int id = Integer.parseInt(search);
                Part part = Inventory.lookupPart(id);
                if (part != null) {
                    partTableView.getSelectionModel().select(id - 1);
                    partTableView.setItems(Inventory.getAllParts());
                }
            } catch (NumberFormatException ignored) {
            }
        } else {
            partTableView.setItems(parts);
        }
    }

    /** This method is called when the controller is first invoked.
     This sets the initial tableviews and column headers.
     @param url URL
     @param resourceBundle a Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.setItems(associatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
