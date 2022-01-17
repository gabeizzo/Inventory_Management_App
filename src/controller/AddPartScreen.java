package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class displays the Add Part Screen.
 */
public class AddPartScreen implements Initializable {
    public Button cancelAddPartButton;
    public Button saveAddPartButton;
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public ToggleGroup partSourceToggle;
    public Label partIDLabel;
    public Label partNameLabel;
    public Label partStockLabel;
    public Label partPriceLabel;
    public Label partMaxLabel;
    public Label partMachineIDLabel;
    public Label partMinLabel;
    public TextField partIDTxt;
    public TextField partNameTxt;
    public TextField partStockTxt;
    public TextField partPriceTxt;
    public TextField partMaxTxt;
    public TextField partMachineIDTxt;
    public TextField partMinTxt;

    /** This method saves the newly added Part when the Save button is clicked on the Add Part screen.
     @param actionEvent When the Save button is clicked in the Add Part screen.
     */    public void saveAddToPartsList(ActionEvent actionEvent) throws IOException {
        try {
            int id = Inventory.allParts.size()+1;
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partStockTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());

            // Checks that min/max/stock fields have correct input
            if(max < min || stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("""
                        Invalid value(s) entered.
                        Please enter valid Min, Max and/or Stock values in the text fields.
                        Please note: Min and/or Stock cannot be greater than Max.""");
                alert.showAndWait();
            }
            else if (stock < 0 || min < 0 || max < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("""
                        Invalid value(s) entered.
                        Please enter valid Min, Max and/or Stock values in the text fields.
                        Values must be 0 or greater and Stock values must be between the Min and Max values.""");
                alert.showAndWait();
            }
            else {
                if (inHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(partMachineIDTxt.getText());
                    Inventory.addPart(new InHouse(id, name, stock, price, min, max, machineID));
                } else {
                    String companyName = partMachineIDTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, stock, price, min, max, companyName));
                }
                //Returns to first screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
                Stage stage = (Stage) cancelAddPartButton.getScene().getWindow();
                stage.setTitle("Inventory Management");
                stage.setScene(new Scene(root, 1200, 600));
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Alert");
            alert.setContentText("Invalid value(s) entered.\nPlease enter valid values in text fields.\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    /** This method loads the main Inventory Management screen when the Cancel button is clicked.
     @param actionEvent When the Cancel button is clicked on the Add Part screen.
     */
    public void toFirstScreen(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel will clear all input and return to main menu.\n Continue anyways?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
            Stage stage = (Stage) cancelAddPartButton.getScene().getWindow();
            stage.setTitle("Inventory Management");
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();
        }
    }

    /**This method initializes the Add Part screen.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known(Source: docs.oracle.com).
     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized(Source: docs.oracle.com).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partSourceToggle = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(partSourceToggle);
        outsourcedRadioButton.setToggleGroup(partSourceToggle);
        inHouseRadioButton.setSelected(true);
    }
    /** This method checks which radio button is selected and changes the corresponding label text depending on which radio button is selected.
     @param actionEvent When either the inHouseRadioButton or outsourcedRadioButton are selected.
     */
    public void radioButtonSelected(ActionEvent actionEvent) throws IOException {
        if (partSourceToggle.getSelectedToggle().equals(inHouseRadioButton)) {
            partMachineIDLabel.setText("Machine ID");
        }
        if (partSourceToggle.getSelectedToggle().equals(outsourcedRadioButton)) {
            partMachineIDLabel.setText("Company Name");
        }

    }
}
