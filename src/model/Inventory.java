package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class is used as a container to hold all Part and Product objects used in the application.
 The Part and Product lists can be manipulated using the various methods in the Inventory class.
 */
public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Getter for the allParts Observable List.
     @return The list of all Parts.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Getter for the allProducts Observable List.
     @return The list of all Products.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Adds newly created Parts to the allParts list.
     @param newPart The new part to be added to the allParts list.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Adds newly created Products to the allProducts list.
     @param newProduct The new part to be added to the allProducts list.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Deletes the selected Part from the allParts list.
     @param selectedPart The selected part to be deleted from the allParts list.
     */
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }

    /** Deletes the selected Product from the allProducts list.
     @param selectedProduct The selected part to be deleted from the allProducts list.
     */
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }

    /** Looks up a Part by id.
     @param partId the Part's id.
     @return The Part that has a matching id.
     */
    public static Part lookupPart(int partId){
        for (Part p : allParts) {
        if (p.getId() == partId) {
            return p;
        }
    }
        return null;
    }
    /** Looks up a Product by id.
     @param productId the Product's id.
     @return The Product that has a matching id.
     */
    public static Product lookupProduct(int productId){
        for (Product p : allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /** Updates the original Part in the allParts list.
     This method is used when modifying Part objects and prevents duplicate Part objects being added to the allParts list.
     @param index The index of the Part in allParts list.
     @param selectedPart The Part selected from the Parts table that is being modified.
     */
    public static void updatePart(int index, Part selectedPart){
    Part p = Inventory.lookupPart(index);
    Inventory.deletePart(p);
    Inventory.addPart(selectedPart);
    }
    /** Updates the original Product in the allProducts list.
     This method is used when modifying Product objects and prevents duplicate Product objects from being added to the allProducts list.
     @param index The index of the Part in allProducts list.
     @param selectedProduct The Part selected from the Products table that is being modified.
     */
    public static void  updateProduct(int index, Product selectedProduct){
        Product p = Inventory.lookupProduct(index);
        Inventory.deleteProduct(p);
        Inventory.addProduct(selectedProduct);
    }
}
