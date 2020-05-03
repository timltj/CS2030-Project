package cs2030.simulator;

/** 
 * Encapsulates a ServedEvent when a human server has served a customer.
 */
public class ServedEvent extends Event {
    // attributes
    private final Server server;
    private final RandomGenerator rg;

    /**
     * Constructs a ServedEvent.
     * @param customer customer served
     * @param time time at which customer is being served
     * @param server server who is serving the customer
     * @param rg RandomGenerator class object
     */
    public ServedEvent(Customer customer, double time, Server server, RandomGenerator rg) {
        super(customer, time);
        this.server = server;
        this.rg = rg;
    }

    /* overrides */
    @Override
    public Event getNextEvent() {
        double newTime = this.time + rg.genServiceTime();
        server.setNextServiceTime(newTime);
        
        Statistics.addTotalWait(this.time - this.customer.getTime());        

        return new DoneEvent(this.customer, newTime, this.server, rg);
    }

    @Override
    public Server getServer() {
        return this.server;
    }
        
    @Override
    public int getStatus() {
        return SERVED;
    }

    @Override
    public String toString() {
        if (this.customer instanceof GreedyCustomer) {
            return String.format("%.3f", this.time) + " " + this.customer.getID() +
                "(greedy) served by server " + this.server.getID();                             
        }
        return String.format("%.3f", this.time) + " " + this.customer.getID() +
            " served by server " + this.server.getID();               
    }
}
