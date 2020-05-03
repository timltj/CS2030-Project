package cs2030.simulator;

/** 
 * Encapsulates a DoneEvent when a human server is done serving a customer.
 */
public class DoneEvent extends Event {
    // attributes
    private final Server server;
    private final RandomGenerator rg;

    /**
     * Constructs a DoneEvent.
     * @param customer customer served
     * @param time time at which customer is done being served
     * @param server server who served the customer
     * @param rg RandomGenerator class object
     */
    public DoneEvent(Customer customer, double time, Server server, RandomGenerator rg) {
        super(customer, time);
        this.server = server;
        this.rg = rg;        
    }

    /* overrides */
    @Override
    public Event getNextEvent() {     
        double restFactor = rg.genRandomRest();
        if (restFactor < HumanServer.probRest) { // human server rests      
            server.setRestFlag();
            return new ServerRest(this.customer, this.time, this.server, rg);
        }     
        return new ServerBack(this.customer, this.time, this.server, rg);     
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public int getStatus() {
        return DONE;
    }
  
    @Override
    public String toString() {
        if (this.customer instanceof GreedyCustomer) {
            return String.format("%.3f", this.time) + " " + this.customer.getID() +
                "(greedy) done serving by server " + this.server.getID();
        }
        return String.format("%.3f", this.time) + " " + this.customer.getID() +
            " done serving by server " + this.server.getID();     
    }
}
