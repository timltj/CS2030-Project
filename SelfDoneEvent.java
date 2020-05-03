package cs2030.simulator;

/** 
 * Encapsulates a SelfDoneEvent when a self-checkout is done serving a customer.
 */
public class SelfDoneEvent extends Event {
    // attributes
    private final Server server;
    private final RandomGenerator rg;
    
    private boolean reservationFlag;
    private Customer nextCustomer;

    /**
     * Constructs a SelfDoneEvent.
     * @param customer customer served
     * @param time time at which customer is done being served
     * @param server self-checkout which served the customer
     * @param rg RandomGenerator class object
     */
    public SelfDoneEvent(Customer customer, double time, Server server, RandomGenerator rg) {
        super(customer, time);
        this.server = server;
        this.rg = rg;        
    }

    /* overrides */
    @Override
    public Event getNextEvent() {   
        if (SelfCheckout.reservation > 0) { // SelfCheckout has customer reservations
            SelfCheckout.reservation--;
            this.reservationFlag = true;

            this.nextCustomer = SelfCheckout.cusQ.peek();
            server.setCurrent(nextCustomer);    

            double newTime = this.time + rg.genServiceTime();
            server.setNextServiceTime(newTime);
        
            Statistics.addTotalWait(this.time - nextCustomer.getTime());             

            return new SelfDoneEvent(server.removeFromQueue(), newTime, server, rg);   
        }     
        return null;     
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public int getStatus() {
        return SELF_DONE;
    }
  
    @Override
    public String toString() {
        if (reservationFlag) { 
            if (this.customer instanceof GreedyCustomer) {
                if (nextCustomer instanceof GreedyCustomer) {
                    return String.format("%.3f", this.time) + " " + this.customer.getID() +
                        "(greedy) done serving by self-check " + this.server.getID() + "\n" +
                        String.format("%.3f", this.time) + " " + this.nextCustomer.getID() +
                        "(greedy) served by self-check " + this.server.getID();         
                }
                return String.format("%.3f", this.time) + " " + this.customer.getID() +
                    "(greedy) done serving by self-check " + this.server.getID() + "\n" +
                    String.format("%.3f", this.time) + " " + this.nextCustomer.getID() +
                    " served by self-check " + this.server.getID();
            } 
            if (nextCustomer instanceof GreedyCustomer) {
                return String.format("%.3f", this.time) + " " + this.customer.getID() +
                    " done serving by self-check " + this.server.getID() + "\n" +
                    String.format("%.3f", this.time) + " " + this.nextCustomer.getID() +
                    "(greedy) served by self-check " + this.server.getID();  
            }
            return String.format("%.3f", this.time) + " " + this.customer.getID() +
                " done serving by self-check " + this.server.getID() + "\n" +
                String.format("%.3f", this.time) + " " + this.nextCustomer.getID() +
                " served by self-check " + this.server.getID();               
        } else {       
            if (this.customer instanceof GreedyCustomer) {
                return String.format("%.3f", this.time) + " " + this.customer.getID() +
                    "(greedy) done serving by self-check " + this.server.getID(); 
            }
            return String.format("%.3f", this.time) + " " + this.customer.getID() +
                " done serving by self-check " + this.server.getID();        
        }
    }
}
