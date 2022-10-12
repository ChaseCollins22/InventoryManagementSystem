package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the base storage of all parts and products in the management system. */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method returns all parts in the main Inventory.
     This method is a getter for the allParts ObservableList in the Inventory Model.
     @return Returns all parts in the system
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This method returns all products in the main Inventory.
     This method is a getter for the allProducts ObservableList in the Inventory Model.
     @return Returns all products in the system
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** This method adds a part to a list of existing parts.
     This method adds a new part to the allParts list in the Inventory model.
     @param newPart A new Part object
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This method adds a product to a list of existing products.
     This method adds a new product to the allProducts list in the Inventory model.
     @param newProduct A new Product object
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**This method finds a part by id in allParts.
     This is a helper method that searches allParts for a matching id.
     @param partId A part id number
     @return Returns a Part object
     */
    public static Part lookupPart(int partId) {
        for (int i = 0; i < getAllParts().size(); i++) {
            Part part = getAllParts().get(i);
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**This method finds a product by id in allProducts.
     This is a helper method that searches allProducts for a matching id.
     @param productId A product id number
     @return Returns a Product object
     */
    public static Product lookupProduct(int productId) {
        for (int i = 0; i < getAllProducts().size(); i++) {
            Product product = getAllProducts().get(i);
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**This method finds a part by name in allParts.
     This is a helper method that searches allParts for a matching partial/full name.
     @param partName A full/partial part name
     @return Returns an ObservableList of Parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFiltered = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            if (part.getName().contains(partName)) {
                partsFiltered.add(part);
            }
        }
        return partsFiltered;
    }

    /**This method finds a part by name in allProducts.
     This is a helper method that searches allProducts for a matching partial/full name.
     @param productName A full/partial product name
     @return Returns an ObservableList of Products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFiltered = FXCollections.observableArrayList();
        for (Product product : getAllProducts()) {
            if (product.getName().contains(productName)) {
                productsFiltered.add(product);
            }
        }
        return productsFiltered;
    }

    /** This method updates an existing part.
     This method overwrites previous data about a part with updated data.
     @param index The index where the part is located
     @param selectedPart An updated Part object
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index - 1, selectedPart);
    }

    /** This method updates an existing product.
     This method overwrites previous data about a product with updated data.
     @param index The index where the product is located
     @param newProduct An updated Product object
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index - 1, newProduct);
    }

    /** This method deletes a part.
     This method deletes a part from the allParts list.
     @param selectedPart A selected Part object
     */
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    /** This method deletes a product.
     This method deletes a product from the allProducts list.
     @param selectedProduct A selected Product object
     */
    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

}
