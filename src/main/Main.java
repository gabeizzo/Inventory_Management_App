/**
 * @author Gabriel Izzo<gizzo@wgu.edu>
 * @version 1.0
 *<b>Javadoc located in the project folder in a separate folder titled javadoc. Example file path: \C482_Project\javadoc<b/>
 */

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/** Main class displays an application to display and manage inventory items.
 Users can view, add, modify and delete Parts and Products.
 A future enhancement idea would be to connect the application to a database/sales system so that inventory item data could be stored and changed as inventory items are received or sold/shipped.
 */
public class Main extends Application {

    /** This method displays the First Screen when the application is started.
     FirstScreen.fxml gets loaded and shows the Inventory Management main menu.
     @param stage The stage to display.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        stage.setTitle("Inventory Management");
        stage.setScene(new Scene(root,1200 ,600));
        stage.show();
    }

    /**This is the application's main method.
      This is the first method to be called when running the application.
      @param args The arguments to be used when application is launched.
     */
    public static void main(String[] args){

        //Parts to be listed in Parts Table
        InHouse part1 = new InHouse(1,"Hose",5,20.99,2,10, 808117);
        InHouse part2 = new InHouse(2,"133HP Steel Tank",5,499.99,1,6,156432);
        InHouse part3 = new InHouse(3,"Pressure Gauge",5,59.99, 1,6, 234220);
        InHouse part4 = new InHouse(4, "Tank Valve", 8, 45.99, 1, 10, 890011);
        InHouse part5 = new InHouse(5, "3/4 O-Ring", 100, 0.25, 20, 1000,765432);

        Outsourced part6 = new Outsourced(6, "ScubaPro Mask", 10, 125.99, 2, 20, "ScubaPro");
        Outsourced part7 = new Outsourced(7, "Atomic Aquatics Snorkel", 10, 79.99, 2, 20, "Atomic Aquatics");
        Outsourced part8 = new Outsourced(8, "XS-Scuba Turtle Fins", 10, 189.99, 2, 20, "XS-Scuba");
        Outsourced part9 = new Outsourced(9, "ScubaPro BCD", 5, 999.99, 1, 10, "ScubaPro");
        Outsourced part10 = new Outsourced(10, "Mares X-Mission Drysuit", 2, 3899.99, 1, 10, "Mares");
        Outsourced part11 = new Outsourced(11, "Shearwater Perdix AI Dive Computer", 6, 1179.99, 1, 10, "Shearwater");

        //Adds the parts to FirstScreen Parts Table
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);
        Inventory.addPart(part11);

        //Products Table data
        Product product1 = new Product(1,"Tank System", 5, 1399.99, 1, 10);
        Product product2 = new Product(2,"Mask + Snorkel Set", 10, 199.99, 1, 10);
        Product product3 = new Product(3,"Complete Scuba System", 4, 7999.99, 1, 10);

        //Adds the Products to the Product Table
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);


        launch(args);
    }
}
