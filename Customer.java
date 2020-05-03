package cs2030.simulator;

/** 
 * Encapsulates a typical customer.
 */
public class Customer {
    // static fields
    private static int counter = 1;

    // attributes
    protected final int id;
    protected final double arrTime;

    /**
     * Constructs a Customer. 
     * @param arrTime typical customer arrival time    
     */
    public Customer(double arrTime) {
        this.id = counter;
        this.arrTime = arrTime;
        counter++;
    }

    /* getters */
    public int getID() {
        return this.id;
    }

    public double getTime() {
        return this.arrTime;
    }
}
