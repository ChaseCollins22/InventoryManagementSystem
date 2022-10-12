package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the base class for all products in the management system. */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This is the class constructor.
     This constructor creates new Products.
     @param price The price of the product
     @param name The name of the product
     @param min The minimum number in stock
     @param max The maximum number in stock
     @param id A unique Product ID
     @param stock Product current stock level
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method returns a list of associated product parts.
     This method is a getter for the associatedParts list.
     @return Product associated parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /** This method returns a Product id.
     This method is a getter for the id attribute.
     @return Product id number
     */
    public int getId() {
        return id;
    }

    /** This method sets a Product id.
     This method is a setter for the id attribute.
     @param id Product id number
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method returns a Product name.
     This method is a getter for the name attribute.
     @return Product name
     */
    public String getName() {
        return name;
    }

    /** This method sets a Product name.
     This method is a setter for the name attribute.
     @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method returns a Product price.
     This method is a getter for the price attribute.
     @return Product price
     */
    public double getPrice() {
        return price;
    }

    /** This method sets a Product price.
     This method is a setter for the price attribute.
     @param price Product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method returns a Product current stock level.
     This method is a getter for the stock attribute.
     @return Product current stock level
     */
    public int getStock() { return stock; }

    /** This method sets a Product stock level.
     This method is a setter for the stock attribute.
     @param stock Product current stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method returns a Product minimum current stock level.
     This method is a getter for the min attribute.
     @return Product current stock level
     */
    public int getMin() {
        return min;
    }

    /** This method sets a Product minimum stock level.
     This method is a setter for the min attribute.
     @param min Product minimum stock level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method returns a Product maximum stock level.
     This method is a getter for the max attribute.
     @return Product maximum stock level
     */
    public int getMax() {
        return max;
    }

    /** This method sets a Product maximum stock level.
     This method is a setter for the max attribute.
     @param max Product maximum stock level
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds an associated part.
     This method adds an associated part to the associated parts list
     @param part new Part object
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** THis method deltes an associated part.
     This method deletes an associated part from the associatedParts list.
     @param selectedAssociatedPart selected associated Part object
     @return true/false value
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        try {
            associatedParts.remove(selectedAssociatedPart);
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

}
