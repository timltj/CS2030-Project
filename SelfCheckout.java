package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;

/** 
 * Encapsulates a self-checkout server.
 */
public class SelfCheckout extends Server {    
    // static fields
    protected static Queue<Customer> cusQ = new LinkedList<Customer>();    
    protected static int reservation = 0;

    /**
     * Constructs a SelfCheckout.
     * @param maxQ maximum queue length  
     * @param rg RandomGenerator class object
     */
    public SelfCheckout(int maxQ, RandomGenerator rg) {
        super(maxQ, rg);        
    }

    /* overrides */
    @Override
    public Queue<Customer> getQueue() {
        return SelfCheckout.cusQ;
    }

    @Override
    public void addToQueue(Customer customer) {
        SelfCheckout.cusQ.add(customer);
    }

    @Override
    public Customer removeFromQueue() {             
        return SelfCheckout.cusQ.poll();
    }

    @Override
    public boolean queueFull() {
        if (SelfCheckout.cusQ.size() < maxQ) {
            return false;
        } else {
            return true;
        }
    }    
}
