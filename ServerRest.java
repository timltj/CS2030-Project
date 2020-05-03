package cs2030.simulator;

/** 
 * Encapsulates a ServerRest event when a human server rests.
 */
public class ServerRest extends Event {
    // attributes
    private final Server server;
    private final RandomGenerator rg;

    /**
     * Constructs a ServerRest event.
     * @param customer previous customer served
     * @param time time at which previous customer was done being served
     * @param server this human server
     * @param rg RandomGenerator class object
     */
    public ServerRest(Customer customer, double time, Server server, RandomGenerator rg) {
        super(customer, time);
        this.server = server;
        this.rg = rg;        
    }

    /* overrides */
    @Override
    public Event getNextEvent() {   
        double newTime = this.time + rg.genRestPeriod();
        server.setNextServiceTime(newTime);
        server.setRestFlag();
        return new ServerBack(this.customer, newTime, this.server, rg);
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public int getStatus() {
        return SERVER_REST;
    }
  
    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + server.getID() + " resting";
    }
}
