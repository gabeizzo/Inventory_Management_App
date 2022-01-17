package controller;

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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.allProducts;

/** This class displays the first screen that a user sees when running the application.
 The first screen shows all Parts and Product objects in inventory in two separate tables.
 Users can search for parts, add, modify, delete them as well as exit the application from this screen.
 */

public class FirstScreen implements Initializable {

    @FXML
    public Button addPartButton;

    @FXML
    public Button modifyPartButton;

    @FXML
    public Button deletePartButton;

    @FXML
    public Button addProductButton;

    @FXML
    public Button modifyProductButton;

    @FXML
    public Button deleteProductButton;

    @FXML
    public TextField partsToSearch;

    @FXML
    public TextField productsToSearch;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part,Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part,Integer> partInventoryLevelColumn;

    @FXML
    private TableColumn<Part,Double> partPrice;

    @FXML
    private TableColumn<Product,Integer> productIDColumn;

    @FXML
    private TableColumn<Product,String> productNameColumn;

    @FXML
    private TableColumn<Product,Integer> productInventoryLevelColumn;

    @FXML
    private TableColumn<Product,Double> productPrice;


    /**This method initializes the first screen.
     During initialization, the tables are populated with their corresponding data.
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

        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsToSearch.setPromptText("Search by Part ID or Name");
        productsToSearch.setPromptText(("Search Product ID or Name"));

    }

    /**This method displays the Add Parts screen.
     @param actionEvent When the Add button on the main screen, below the Parts table, is clicked.
     @throws IOException
     */
    public void toAddPartScreen(ActionEvent actionEvent) throws IOException {

        //load hierarchy of AddPartScreen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));

        //get the AddPartScreen stage from Add button click event
        Stage stage = (Stage) (addPartButton.getScene().getWindow());

        //Create the Add Part Screen and display
        Scene scene = new Scene(root,800,600);
        stage.setTitle("Add Part Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**Displays the Modify Part screen.
     @param actionEvent When the Modify button on the main screen, below the Parts table is clicked. This must be after selecting a Part object in the Parts table to modify.
     @throws IOException
     */
    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
        if(partsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartScreen.fxml"));
            loader.load();

            ModifyPartScreen MPSController = loader.getController();
            MPSController.sendPartData(partsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) (modifyPartButton.getScene().getWindow());
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Part Screen");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**Displays the Add Product screen.
     @param actionEvent When the Add button on the main screen, below the Products table, is clicked.
     @throws IOException
     */
    public void toAddProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        Stage stage = (Stage) (addProductButton.getScene().getWindow());
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Product Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**Displays the Modify Product screen.
     @param actionEvent When the Modify button on the main screen, below the Products table, is clicked. This must be after selecting a Product object in the Products table to modify.
     @throws IOException
     */
    public void toModifyProductScreen(ActionEvent actionEvent) throws IOException {
        if(productTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductScreen.fxml"));
            loader.load();

            ModifyProductScreen MPSController = loader.getController();
            MPSController.sendProductData(productTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) (modifyProductButton.getScene().getWindow());
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Product Screen");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**This method deletes Part objects from inventory.
     @param actionEvent When Delete button below the Parts table is clicked, deletes selected Part from Inventory.
     */

    public void deletePart(ActionEvent actionEvent) {
        if(partsTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete will remove this part from inventory.\n Are you sure you want to delete this item?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.allParts.remove(partsTable.getSelectionModel().getSelectedItem());
            }
        }
    }
    /**This method deletes Product objects from inventory.
     @param actionEvent When Delete button below the Products table is clicked, deletes selected Product and Associated Parts from Inventory.
     @throws IOException
     */
    public void deleteProduct(ActionEvent actionEvent) throws IOException{
        Product product = productTable.getSelectionModel().getSelectedItem();
        if(product != null){
                if(product.getAssociatedParts().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete will remove this product from inventory.\n\n\n Are you sure you want to delete this item?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK) {
                       Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    }
                }
                else{
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setContentText("There are associated parts related to this Product.\n\n\nRemove all associated parts in the Modify Part screen and try again.");
                    alert2.showAndWait();
            }
        }
    }

    /** This method searches the Parts table for a match by id or name.
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
     @return p The Part that matches the searched id.
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

    /**This method searches the Products table for a match by id or name.
     @param actionEvent When user presses Enter after entering input into the search bar above the Products table.
     */
    public void searchProducts(ActionEvent actionEvent) {

        String searchInput = productsToSearch.getText();

        ObservableList<Product> products = searchByProductName(searchInput);
        try {
            int id = Integer.parseInt(searchInput);
            Product p = searchByProductID(id);
            if(p != null){
                products.add(p);
            }
        } catch (NumberFormatException e) {
            //ignore
        }
        productTable.setItems(products);

        if(products.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Alert");
            alert.setContentText("No parts found.");
            alert.showAndWait();
        }

    }
    /** This method searches Products by name.
     @param partialProductName The text input entered into the search field above the Products table.
     @return resultsSearch The search results to be displayed in the Products table.
     */
    private ObservableList<Product> searchByProductName(String partialProductName){
        ObservableList<Product> resultsSearch = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product p : allProducts){
            if(p.getName().contains(partialProductName)){
                resultsSearch.add(p);
            }
        }
        return resultsSearch;
    }
    /**Searches Products by id.
     @param id The Product id that is searched.
     @return p The Product that matches the searched id.
     */
    private Product searchByProductID(int id){
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product p : allProducts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /** Exits the application.
     @param actionEvent When the Exit button on the main screen is clicked.
     @throws IOException
     */
    public void onActionExit(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }

}
