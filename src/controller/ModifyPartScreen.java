package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class displays the Modify Part screen.
 */
public class ModifyPartScreen implements Initializable {

    public Button saveModifyButton;
    public Button cancelModifyButton;
    public ToggleGroup partSourceToggle;
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public Label partIDLabel;
    public Label partNameLabel;
    public Label partStockLabel;
    public Label partPriceLabel;
    public Label partMaxLabel;
    public Label partMachineIDLabel;
    public TextField partIDTxt;
    public TextField partNameTxt;
    public TextField partStockTxt;
    public TextField partPriceTxt;
    public TextField partMaxTxt;
    public TextField partMachineIDTxt;
    public TextField partMinTxt;
    public Label partMinLabel;

    /** This method saves and updates the modified Part when the Save button is clicked on the Modify Part screen.
     @param actionEvent When the Save button is clicked in the Modify Part screen.
     @throws IOException NumberFormatException expected if text input that does not match the type of the textfield is entered.
     */
    public void saveModifyPart(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(partIDTxt.getText());
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
                        Please note: Min and/or Stock cannot be greater than Max.
                        """);
                alert.showAndWait();
            }
            else if (stock < 0 || min < 0 || max < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("""
                        Invalid value(s) entered.
                        Please enter valid Min, Max and/or Stock values in the text fields.
                        Values must be 0 or greater and Stock values must be between the Min and Max values.
                        """);
                alert.showAndWait();
            }
            else { //Returns to first screen

                if (inHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(partMachineIDTxt.getText());
                    Inventory.updatePart(Integer.parseInt(partIDTxt.getText()), new InHouse(id, name, stock, price, min, max, machineID));
                } else {
                    String companyName = partMachineIDTxt.getText();
                    Inventory.updatePart(Integer.parseInt(partIDTxt.getText()), new Outsourced(id, name, stock, price, min, max, companyName));
                }
                Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
                Stage stage = (Stage) cancelModifyButton.getScene().getWindow();
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
     @param actionEvent When the Cancel button is clicked on the Modify Part screen.
     @throws IOException
     */
    public void modifyToFirstScreen(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel will clear all input and return to main menu.\n Continue anyways?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
            Stage stage = (Stage) cancelModifyButton.getScene().getWindow();
            stage.setTitle("Inventory Management");
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();
        }
    }

    /** Sends the selected Part object data from the main screen to Modify Part screen's text fields.
     @param part The Part object that is getting its data retrieved and sent to be modified.
     */
    public void sendPartData(Part part){
        partIDTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partStockTxt.setText(String.valueOf(part.getStock()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));

        if(part instanceof InHouse) {
            partMachineIDTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
            partMachineIDLabel.setText("Machine ID");
            inHouseRadioButton.setSelected(true);
        }
        else if(part instanceof Outsourced) {
            partMachineIDTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            partMachineIDLabel.setText("Company Name");
            outsourcedRadioButton.setSelected(true);
        }

    }
    /**This method initializes the Modify Part screen.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known(Source: docs.oracle.com).
     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized(Source: docs.oracle.com).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partSourceToggle = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(partSourceToggle);
        outsourcedRadioButton.setToggleGroup(partSourceToggle);

    }

    /** This method checks which radio button is selected and changes the corresponding label text depending on which radio button is selected.
     @param actionEvent When either the inHouseRadioButton or outsourcedRadioButton are selected.
     */
    public void radioButtonSelected(ActionEvent actionEvent) {
        if (partSourceToggle.getSelectedToggle().equals(inHouseRadioButton)){
            partMachineIDLabel.setText("Machine ID");
        }
        if(partSourceToggle.getSelectedToggle().equals(outsourcedRadioButton)){
            partMachineIDLabel.setText("Company Name");
        }

    }
}
