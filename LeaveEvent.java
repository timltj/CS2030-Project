package cs2030.simulator;

/** 
 * Encapsulates a LeaveEvent when a customer leaves.
 */
public class LeaveEvent extends Event {
    /**
     * Constructs a LeaveEvent.
     * @param customer customer who left
     * @param time time at which customer left
     */
    public LeaveEvent(Customer customer, double time) {
        super(customer, time);
        Statistics.addLeft();    
    }

    /* overrides */
    @Override
    public Event getNextEvent() {       
        return null;
    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public int getStatus() {
        return LEAVE;
    }

    @Override
    public String toString() {
        if (this.customer instanceof GreedyCustomer) {
            return String.format("%.3f", this.time) + " " +
                this.customer.getID() + "(greedy) leaves";        
        }       
        return String.format("%.3f", this.time) + " " + this.customer.getID() + " leaves";
    }
}
