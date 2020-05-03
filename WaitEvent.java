package cs2030.simulator;

/** 
 * Encapsulates a WaitEvent when a customer waits in queue.
 */
public class WaitEvent extends Event {
    // attributes
    private final Server server;
    private final RandomGenerator rg;

    /**
     * Constructs a WaitEvent.
     * @param customer customer waiting to be served
     * @param time time at which customer waits
     * @param server server designated serve the customer
     * @param rg RandomGenerator class object
     */
    public WaitEvent(Customer customer, double time, Server server, RandomGenerator rg) {
        super(customer, time);
        this.server = server;
        this.rg = rg;        
    }

    /* overrides */
    @Override
    public Event getNextEvent() {
        if (this.server instanceof HumanServer) {
            return new ServedEvent(this.customer, server.getNextServiceTime(), this.server, rg);
        }
        return null;
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public int getStatus() {
        return WAIT;
    }
   
    @Override
    public String toString() {
        if (this.customer instanceof GreedyCustomer) {
            if (this.server instanceof HumanServer) {
                return String.format("%.3f", this.time) + " " + this.customer.getID() +
                    "(greedy) waits to be served by server " + this.server.getID();           
            }
            return String.format("%.3f", this.time) + " " + this.customer.getID() +
                "(greedy) waits to be served by self-check " + (Server.numHumanServer + 1);  
        }
        if (this.server instanceof HumanServer) {
            return String.format("%.3f", this.time) + " " + this.customer.getID() +
                " waits to be served by server " + this.server.getID();           
        }
        return String.format("%.3f", this.time) + " " + this.customer.getID() +
            " waits to be served by self-check " + (Server.numHumanServer + 1);          
    }
}
