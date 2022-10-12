package Model;

/** This class creates a type of part called an outsourced part*/
public class Outsourced extends Part {

    /** This is the class constructor.
     This constructor creates new Outsourced parts.
     @param price The price of the part
     @param name The name of the part
     @param min The minimum number in stock
     @param max The maximum number in stock
     @param id A unique Part ID
     @param stock The current number in stock
     @param companyName The manufacturer's company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    private String companyName;

    /** This method gets the company name.
     @return Returns the current company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This method sets the company name.
     @param companyName A new company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
