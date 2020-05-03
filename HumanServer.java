package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;

/** 
 * Encapsulates a human server.
 */
public class HumanServer extends Server {   
    // static fields
    protected static double probRest;   

    // attributes
    private boolean restFlag;
    private Queue<Customer> cusQ;

    /**
     * Constructs a HumanServer.
     * @param maxQ maximum queue length  
     * @param rg RandomGenerator class object
     */
    public HumanServer(int maxQ, RandomGenerator rg) {
        super(maxQ, rg);
        this.restFlag = false;
        this.cusQ = new LinkedList<Customer>();
    }

    /* HumanServer operations */
    @Override
    public double getProbRest() {
        return this.probRest;
    }

    @Override
    public boolean getRestFlag() {
        return this.restFlag;
    }  
    
    @Override
    public void setRestFlag() {
        this.restFlag = !this.restFlag;
    }

    /* overrides */    
    @Override
    public Queue<Customer> getQueue() {
        return this.cusQ;
    }

    @Override
    public void addToQueue(Customer customer) {
        this.cusQ.add(customer);
    }

    @Override
    public Customer removeFromQueue() {
        return this.cusQ.poll();
    }
   
    @Override
    public boolean queueFull() {
        if (cusQ.size() < maxQ) {
            return false;
        } else {
            return true;
        }
    }    
}
