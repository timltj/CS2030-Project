package cs2030.simulator;

import java.util.List;

/** 
 * Encapsulates an ArrivalEvent when a customer arrives.
 */
public class ArrivalEvent extends Event {
    // attributes
    private final List<Server> serList;
    private final RandomGenerator rg;

    /**
     * Constructs an ArrivalEvent.
     * @param customer an arriving customer
     * @param time time of customer arrival
     * @param serList list of all servers
     * @param rg RandomGenerator class object
     */
    public ArrivalEvent(Customer customer, double time, List<Server> serList, RandomGenerator rg) {
        super(customer, time);
        this.serList = serList;
        this.rg = rg;
    }
   
    /* overrides */
    @Override
    public Event getNextEvent() {     
        // check for SERVE or SELF_SERVE
        for (int i = 0; i < serList.size(); i++) { 
            Server server = serList.get(i);
            if (this.time >= server.getNextServiceTime() &&
                    !server.getRestFlag() &&
                    server instanceof HumanServer) { // human server
                server.setCurrent(this.customer);
                return new ServedEvent(this.customer, this.time, server, rg);                
            } 
            if (this.time >= server.getNextServiceTime() &&
                    SelfCheckout.reservation == 0 &&
                    server instanceof SelfCheckout) { // self-checkout
                server.setCurrent(this.customer);
                return new SelfServedEvent(this.customer, this.time, server, rg);
            }
        }  
        
        // check for WAIT
        if (this.customer instanceof GreedyCustomer) { // greedy customer
            int minQ = Integer.MAX_VALUE;
            Server minServer = null;
            for (int i = 0; i < serList.size(); i++) { // find shortest queue to wait
                Server server = serList.get(i);
                if (server.getQueue().size() < minQ &&
                        server.queueFull() == false &&
                        !server.getRestFlag()) {
                    minQ = server.getQueue().size();
                    minServer = server;
                }
            }
            if (minServer != null) {
                minServer.addToQueue(this.customer);       
                if (minServer instanceof SelfCheckout) { // SelfCheckout reservation
                    SelfCheckout.reservation++;                 
                }
                return new WaitEvent(this.customer, this.time, minServer, rg);
            }
        } else { // typical customer
            for (int i = 0; i < serList.size(); i++) { // find first queue to wait
                Server server = serList.get(i);
                if (server.queueFull() == false && !server.getRestFlag()) {
                    server.addToQueue(this.customer);       
                    if (server instanceof SelfCheckout) { // SelfCheckout reservation
                        SelfCheckout.reservation++;                 
                    }
                    return new WaitEvent(this.customer, this.time, server, rg);
                }
            }        
        }
        
        // LEAVE
        return new LeaveEvent(this.customer, this.time);
    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public int getStatus() {
        return ARRIVAL;
    }

    @Override
    public String toString() {
        if (this.customer instanceof GreedyCustomer) {
            return String.format("%.3f", this.time) + " " +
                this.customer.getID() + "(greedy) arrives";
        }
        return String.format("%.3f", this.time) + " " + this.customer.getID() + " arrives";
    }
}
