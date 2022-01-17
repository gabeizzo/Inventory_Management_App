package model;

/** This class is used to create InHouse Part objects.
 */
public class InHouse extends Part{
    private int machineID;

    public InHouse(int id, String name, int stock, double price, int min, int max, int machineID) {
        super(id, name, stock, price, min, max);
        this.machineID = machineID;
    }
    /** Gets the InHouse Part's machine id.
     @return The InHouse Part's machine id.
     */
    public int getMachineID() {
        return machineID;
    }

}
