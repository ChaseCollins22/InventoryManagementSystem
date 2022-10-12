package Model;

/** This is the base class for all parts in the management system. */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This is the class constructor.
     This constructor creates the base attributes of a Part.
     @param price The price of the part
     @param name The name of the part
     @param min The minimum number in stock
     @param max The maximum number in stock
     @param id A unique Part ID
     @param stock The current number in stock
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method returns a Part id.
     This method is a getter for the id attribute.
     @return Part id number
     */
    public int getId() {
        return id;
    }

    /** This method sets a Part id.
     This method is a setter for the id attribute.
     @param id Part id number
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method returns a Part name.
     This method is a getter for the name attribute.
     @return Part name
     */
    public String getName() {
        return name;
    }

    /** This method sets a Part name.
     This method is a setter for the name attribute.
     @param name Part name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method returns a Part price.
     This method is a getter for the price attribute.
     @return Part price
     */
    public double getPrice() {
        return price;
    }

    /** This method sets a Part price.
     This method is a setter for the price attribute.
     @param price Part price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method returns a Part stock level.
     This method is a getter for the stock attribute.
     @return Part current stock
     */
    public int getStock() {
        return stock;
    }

    /** This method sets a Part current stock level.
     This method is a setter for the stock attribute.
     @param stock Part new stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method returns a Part minimum stock level.
     This method is a getter for the min attribute.
     @return Part minimum stock level
     */
    public int getMin() {
        return min;
    }

    /** This method sets a Part minimum stock level.
     This method is a setter for the min attribute.
     @param min Part new minimum stock level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method returns a Part maximum stock level.
     This method is a getter for the max attribute.
     @return Part maximum stock level
     */
    public int getMax() {
        return max;
    }

    /** This method sets a Part maximum stock level.
     This method is a setter for the max attribute.
     @param max Part maximum stock level
     */
    public void setMax(int max) {
        this.max = max;
    }
}
