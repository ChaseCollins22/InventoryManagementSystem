package Model;

/** This class creates a type of part called an In-House part.*/
public class InHouse extends Part {

    /** This is the class constructor.
     This constructor creates new In-House parts.
     @param id a unique id number
     @param max the maximum number in stock
     @param min the minimum number in stock
     @param machineId the machine id that manufactures the part
     @param name the name of the part
     @param price the price of the part
     @param stock the current number in stock
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    private int machineId;

    /** This method gets the machine id number.
     @return machineId integer
     */
    public int getMachineId() {
        return machineId;
    }

    /** This method sets the machine id number. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
