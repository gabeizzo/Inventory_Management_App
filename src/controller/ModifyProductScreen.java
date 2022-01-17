package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class displays the Modify Product screen.
 */
public class ModifyProductScreen implements Initializable {
    public Button cancelModifyProduct;
    public Button saveModifyProductButton;
    public TextField productIDTxt;
    public TextField productNameTxt;
    public TextField productStockTxt;
    public TextField productPriceTxt;
    public TextField productMaxTxt;
    public TextField productMinTxt;
    public TextField partsToSearch;
    public Button addAssociatedPartButton;
    public Button removeAssociatedPartButton;
    public TableView associatedPartsTable;
    public TableView partsTable;
    public TableColumn associatedPartIDColumn;
    public TableColumn associatedPartNameColumn;
    public TableColumn partPrice;
    public TableColumn associatedPartInventoryColumn;
    public TableColumn associatedPartPrice;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    private Product product;

    /** This method loads the main Inventory Management screen when the Cancel button is clicked.
     @param actionEvent When the Cancel button is clicked on the Modify Product screen.
     @throws IOException
     */
    public void modifyProductToFirstScreen(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel will not update any new text input and returns to the main menu.\n Continue anyways?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
            Stage stage = (Stage) cancelModifyProduct.getScene().getWindow();
            stage.setTitle("Inventory Management");
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();
        }
    }
    /**This method initializes the Modify Product screen.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known(Source: docs.oracle.com).
     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized(Source: docs.oracle.com).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /** Sends the selected Product object data from the main screen to Modify Product screen's text fields.
     @param product1 The Product object that is getting its data retrieved and sent to be modified.
     */
    public void sendProductData(Product product1) {
        product = product1;
        productIDTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        productStockTxt.setText(String.valueOf(product.getStock()));
        productMaxTxt.setText(String.valueOf(product.getMax()));
        productMinTxt.setText(String.valueOf(product.getMin()));
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        associatedPartsTable.setItems(product.getAssociatedParts());

    }

    /** This method saves and updates the modified Product when the Save button is clicked in the Modify Product screen.
     * A logical error that was encountered while developing this method was that, originally, the method would complete without performing all logical error checks.
     * When the method would be called, instead of it checking that the inventory levels are between the Max and Min values for the item and that they are 0 greater, it would only check the exception thrown in the catch block.
     * After trying to use if statements, only one or the other logical error check would be performed and again would continue without checking all logical errors, and would not display the correct error messages.
     * To fix this, instead of using multiple if statements, the logical error checks were separated into if/else-if/else statements inside the try block that would check the multiple logical errors and only go to the main screen once all of them had been checked.
     @param actionEvent When the Save button is clicked in the Modify Parts screen.
     @throws IOException NumberFormatException checks that the text entered is of the correct type for that text field i.e. string, int, etc.
     */
    public void saveModifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            product.setId(Integer.parseInt(productIDTxt.getText()));
            product.setName(productNameTxt.getText());
            product.setStock(Integer.parseInt(productStockTxt.getText()));
            product.setPrice(Double.parseDouble(productPriceTxt.getText()));
            product.setMin(Integer.parseInt(productMinTxt.getText()));
            product.setMax(Integer.parseInt(productMaxTxt.getText()));

            // Checks that min/max/stock fields have correct input
            if(product.getMax() < product.getMin() || product.getStock() < product.getMin() || product.getStock() > product.getMax()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("""
                        Invalid value(s) entered.
                        Please enter valid Min, Max and/or Stock values in the text fields.
                        Please note: Min and Stock cannot be greater than Max.""");
                alert.showAndWait();
            }
            else if (product.getStock() < 0 || product.getMin() < 0 || product.getMax() < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("""
                        Invalid value(s) entered.
                        Please enter valid Min, Max and/or Stock values in the text fields.
                        Values must be 0 or greater and Stock values must be between the Min and Max values.""");
                alert.showAndWait();
            }
            else {
                Inventory.updateProduct(Integer.parseInt(productIDTxt.getText()), product);
                //Returns to first screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
                Stage stage = (Stage) cancelModifyProduct.getScene().getWindow();
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
    /** This method searches the Parts list for a match by id or name.
     @param actionEvent When user presses Enter after entering input into the search bar above the Parts table.
     */
    public void searchParts(ActionEvent actionEvent) {
            String searchInput = partsToSearch.getText();

            ObservableList<Part> parts = searchByPartName(searchInput);
            try {
                int id = Integer.parseInt(searchInput);
                Part p = searchByPartID(id);
                if(p != null){
                    parts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
            partsTable.setItems(parts);

            if(parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Alert");
            alert.setContentText("No parts found.");
            alert.showAndWait();
        }

    }
    /**Searches for Parts by name.
     @param partialPartName The text input entered into the search field above the Parts table.
     @return resultsSearch The search results to be displayed in the Parts table.
     */
        private ObservableList<Part> searchByPartName(String partialPartName){
            ObservableList<Part> resultsSearch = FXCollections.observableArrayList();
            ObservableList<Part> allParts = Inventory.getAllParts();

            for(Part p : allParts){
                if(p.getName().contains(partialPartName)){
                    resultsSearch.add(p);
                }
            }
            return resultsSearch;
        }
    /**Searches Parts by id.
     @param id The Part id that is searched.
     @return The Part that matches the searched id.
     */
        private Part searchByPartID(int id){
            ObservableList<Part> allParts = Inventory.getAllParts();
            for (Part p : allParts) {
                if (p.getId() == id) {
                    return p;
                }
            }
            return null;
        }

    /** This method adds a selected Part to the associated parts table on Modify Product screen.
     @param actionEvent When the Add button is clicked on the Modify Product screen.
     */
    public void addAssociatedParts(ActionEvent actionEvent) {
        if(partsTable.getSelectionModel().getSelectedItem() != null){
            product.addAssociatedPart((Part) partsTable.getSelectionModel().getSelectedItem());
            associatedPartsTable.setItems(product.getAssociatedParts());
        }

    }
    /** This method deletes/removes a selected Part from the associated parts table on Modify Product screen.
     @param actionEvent When the Remove Associated Part button is clicked on the Modify Product screen.
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        if(associatedPartsTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete will remove this part from Associated Parts.\n Are you sure you want to delete this item?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                product.deleteAssociatedPart((Part) associatedPartsTable.getSelectionModel().getSelectedItem());
            }
        }

    }
}
