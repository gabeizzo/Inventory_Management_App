package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class is used to create Product objects.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product (int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**This method is used to get the id for Product objects.
     @return id The Product object's name.
     */
    public int getId() {
        return id;
    }

    /**This method is used to set the id for Product objects.
     @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**This method is used to get the name for Product objects.
     @return name The Product object's name.
     */
    public String getName() {
        return name;
    }

    /**This method is used to set the name for Product objects.
     @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**This method is used to get the price for Product objects.
     @return the price
     */
    public double getPrice() {
        return price;
    }

    /**This method is used to set the price for Product objects.
     @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**This method is used to get the stock for Product objects.
     @return stock The stock value for a Product object.
     */
    public int getStock() {
        return stock;
    }

    /**This method is used to set the stock for Product objects.
     @param stock The stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**This method is used to get the min for Product objects.
     @return min The min value for a Product object.
     */
    public int getMin() {
        return min;
    }

    /**This method is used to set the min for Product objects.
     @param min The min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method is used to get the max for Product objects.
     @return max The max value for a Product object.
     */
    public int getMax() {
        return max;
    }

    /** This method is used to set the max for Product objects.
     @param max The max value to set for a Product object.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Deletes the selected Associated Part from the Associated Parts table on the Add Product and Modify Product screens.
     @param selectedAssociatedPart The highlighted or selected part in the Associated Parts table.
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }

    /** Adds the selected part in the Parts table to the Associated parts table on the Add Product and Modify Product screens.
     @param selectedPart The highlighted or selected part in the Parts table to be added to the Associated Parts table.
     */
    public void addAssociatedPart(Part selectedPart){
        associatedParts.add(selectedPart);
    }

    /** Gets the list of Parts that are associated with a Product. 
     @return associatedParts The list of associated Parts.
     */
    public ObservableList<Part> getAssociatedParts(){
        return associatedParts;
    }

}
