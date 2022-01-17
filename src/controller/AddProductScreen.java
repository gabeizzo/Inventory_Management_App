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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class displays the Add Product screen.
 */
public class AddProductScreen implements Initializable {
    public Button saveAddProductButton;
    public Button cancelAddProductButton;
    public TextField productIDTxt;
    public TextField productNameTxt;
    public TextField productStockTxt;
    public TextField productPriceTxt;
    public TextField productMaxTxt;
    public TextField productMinTxt;
    public TextField partsToSearch;
    public TableView partsTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partPrice;
    public TableView associatedPartsTable;
    public TableColumn associatedPartIDColumn;
    public TableColumn associatedPartNameColumn;
    public TableColumn associatedPartInventoryColumn;
    public TableColumn associatedPartPrice;
    public Button addAssociatedPartButton;
    public Button removeAssociatedPartButton;

    private Product product = new Product(1, "Scuba Gear", 1,1000.00,1,10);

    /** This method loads the main Inventory Management screen when the Cancel button is clicked.
     @param actionEvent When the Cancel button is clicked on the Add Product screen.
     @throws IOException
     */
    public void addProductToFirstScreen(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel will clear all input and return to main menu.\n Continue anyways?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
            Stage stage = (Stage) cancelAddProductButton.getScene().getWindow();
            stage.setTitle("Inventory Management");
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();
        }
    }

    /**This method initializes the Add Product screen.
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
    /** This method saves the new Product and adds it to the Products list/table.
     @param actionEvent When the Save button is clicked in the Add Product screen.
     @throws IOException
     */
    public void saveAddProduct(ActionEvent actionEvent) throws IOException {
        try {
            int id = Inventory.allProducts.size()+1;
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(productStockTxt.getText());
            double price = Double.parseDouble(productPriceTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());

            // Checks that min/max/stock fields have correct input
            if(max < min || stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("""
                        Invalid value(s) entered.
                        Please enter valid Min, Max and/or Stock values in the text fields.
                        Note: Min and Stock cannot be greater than Max. Stock value must between the Min and Max values.""");
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
                //Adds Product once all conditions are met
                Inventory.addProduct(new Product(id, name, stock, price, min, max));

                //Returns to first screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
                Stage stage = (Stage) cancelAddProductButton.getScene().getWindow();
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
    /** Searches for Parts by name.
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
    /** This method adds a selected Part to the associated parts table on the Add Product screen.
     @param actionEvent When the Add button is clicked on the Add Product screen.
     @throws IOException
     */
    public void addAssociatedPart(ActionEvent actionEvent) throws IOException {
        if(partsTable.getSelectionModel().getSelectedItem() != null){
            product.addAssociatedPart((Part) partsTable.getSelectionModel().getSelectedItem());
            associatedPartsTable.setItems(product.getAssociatedParts());
        }
    }
    /** This method deletes/removes a selected Part from the associated parts table on Add Product screen.
     @param actionEvent When the Remove Associated Part button is clicked on the Add Product screen.
     @throws IOException
     */
    public void removeAssociatedPart(ActionEvent actionEvent) throws IOException {
        if(associatedPartsTable.getSelectionModel().getSelectedItem() != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete will remove this part from Associated Parts.\n Are you sure you want to delete this item?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    product.deleteAssociatedPart((Part) associatedPartsTable.getSelectionModel().getSelectedItem());
                }
            }

        }
}
