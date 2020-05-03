package cs2030.simulator;

/** 
 * Encapsulates the fields and methods of a generic Event.
 */
public abstract class Event implements Comparable<Event> {
    // static final constants
    public static final int ARRIVAL = 1;
    public static final int SERVED = 2;
    public static final int WAIT = 3;
    public static final int DONE = 4;
    public static final int LEAVE = 5;
    public static final int SERVER_REST = 6;
    public static final int SERVER_BACK = 7;
    public static final int SELF_SERVED = 8;
    public static final int SELF_DONE = 9;    

    // attributes
    protected final Customer customer;
    protected final double time;

    /**
     * Constructs an abstract generic Event.
     * @param customer customer involved
     * @param time time of event
     */
    protected Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }

    /* abstract methods */
    public abstract Event getNextEvent();    
    
    public abstract Server getServer();    

    public abstract int getStatus();       

    /* inherited methods */
    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }

    /* overrides */
    @Override
    public int compareTo(Event another)  {
        if (this.time < another.time) {
            return -1;
        } else if (this.time > another.time) {
            return 1;
        } else {
            return this.customer.getID() - another.customer.getID();         
        }
    }
}
