package cs2030.simulator;

/** 
 * Encapsulates a ServerBack event when a human server returns from rest.
 */
public class ServerBack extends Event {
    // attributes
    private final Server server;
    private final RandomGenerator rg;

    /**
     * Constructs a ServerBack event.
     * @param customer previous customer served
     * @param time time at which previous customer was done being served
     * @param server this human server
     * @param rg RandomGenerator class object
     */
    public ServerBack(Customer customer, double time, Server server, RandomGenerator rg) {
        super(customer, time);
        this.server = server;
        this.rg = rg;        
    }

    /* overrides */
    @Override
    public Event getNextEvent() {           
        if (!server.getQueue().isEmpty()) {
            server.setCurrent(server.removeFromQueue());        
        } else {
            server.setCurrent(null);
        }                  
        return null;        
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public int getStatus() {
        return SERVER_BACK;
    }
  
    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + server.getID() + " back";
    }
}
