package collins.inventorysystem;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the addPart.fxml document and adds a part to the main Inventory. */
public class AddPartController implements Initializable {
    @FXML
    private ToggleGroup Radio;

    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private RadioButton outsourcedBtn;

    @FXML
    private TextField partIdText;

    @FXML
    private TextField partInvText;

    @FXML
    private TextField partMachineIdText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField partPriceText;

    @FXML
    private Label machineIdLbl;

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
    private Label exceptionTextMachineId;

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

    @FXML
    private Label machineIdException;

    /** This method switches the user's view.
     This method is called when the user needs to be redirected to a different view.
     @param event An ActionEvent
     @param viewName An FXML file
     */
    public void switchView(String viewName, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(viewName));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method returns the user to the main page.
     This method is called when the user clicks the 'Cancel' button on the addPart.fxml view.
     @param event clicking the 'Cancel' button
     */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        switchView("MainPageInventory.fxml", event);
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
        exceptionTextMachineId.setText("");
        machineIdException.setText("");
    }

    /** This method saves a part to the main inventory.
     This method is called when a user clicks the 'Save' button which adds a new part to the main page inventory.
     @param event clicking the 'Save' button
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {
        clearExceptionText();
        boolean valid = true;
        try {
            int partId;
            if (Inventory.getAllParts().size() == 0) {
                partId = 1;
            } else {
                Part lastPart = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1);
                partId = lastPart.getId() + 1;
            }

            String partName = String.valueOf(partNameText.getText());
            int partInv = Integer.parseInt(partInvText.getText());
            double partPrice = Double.parseDouble(partPriceText.getText());
            int partMin = Integer.parseInt(partMinText.getText());
            int partMax = Integer.parseInt(partMaxText.getText());

            if (String.valueOf(partNameText.getText()).isEmpty()) {
                throw new IOException();
            }
            if (partMax <= partMin || (partInv < partMin || partInv > partMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid Max, Min, and Inv values.");
                alert.showAndWait();
                valid = false;
            }
            if (inHouseBtn.isSelected() && valid) {
                int partMachineId = Integer.parseInt(partMachineIdText.getText());
                Inventory.addPart(new InHouse(partId, partName, partPrice, partInv, partMin, partMax, partMachineId));
                switchView("MainPageInventory.fxml", event);
            } else if (outsourcedBtn.isSelected() && valid) {
                String partCompanyName = String.valueOf(partMachineIdText.getText());
                Inventory.addPart(new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, partCompanyName));
                switchView("MainPageInventory.fxml", event);
            }
        } catch (NumberFormatException | IOException e) {
            if (String.valueOf(partNameText.getText()).isEmpty()) {
                exceptionTextName.setText("Exception: ");
                nameException.setText("No data in Name field");
            }
            if (!(isInteger(partInvText))) {
                exceptionTextInv.setText("Exception: ");
                invException.setText("Inv is not an integer value");
            }
            if (!(isDouble(partPriceText) || isInteger(partPriceText))) {
                exceptionTextPrice.setText("Exception: ");
                priceException.setText("Price is not a double value");
            }
            if (!(isInteger(partMaxText))) {
                exceptionTextMax.setText("Exception: ");
                maxException.setText("Max is not an integer value");
            }
            if (!(isInteger(partMinText))) {
                exceptionTextMin.setText("Exception: ");
                minException.setText("Min is not an integer value");
            }
            if (!(isInteger(partMachineIdText))) {
                exceptionTextMachineId.setText("Exception: ");
                machineIdException.setText("Machine ID is not an integer value");
            }
        }
    }

    /** This method changes the label text.
     This method changes the 'Company Name' label to 'Machine ID' when 'In-House' is selected.
     @param event Clicking the In-House button
     */
    public void onActionInHouse(ActionEvent event) {
        machineIdLbl.setText("Machine ID");
    }

    /** This method changes the label text.
     This method changes the 'Machine ID' label to 'Company Name' when 'Outsourced' is selected.
     @param event Clicking the Outsourced button
     */
    public void onActionOutsourced(ActionEvent event) {
        machineIdLbl.setText("Company Name");
    }


    /** This method is called when the controller is first invoked.
     @param url a URL
     @param resourceBundle a Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
