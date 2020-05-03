package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;

/** 
 * Encapsulates the fields and methods of a generic Server.
 */
public abstract class Server { 
    // static fields
    private static int counter = 1;    
    protected static int numHumanServer;

    // attributes
    protected final int id;
    protected final RandomGenerator rg;
    protected final int maxQ;        

    protected double serviceTime;
    protected Customer curCustomer;

    /**
     * Constructs an abstract generic Server.
     * @param maxQ maximum queue length  
     * @param rg RandomGenerator class object
     */
    public Server(int maxQ, RandomGenerator rg) {     
        this.id = counter;
        this.rg = rg;
        this.maxQ = maxQ;           

        this.serviceTime = 0;             
        this.curCustomer = null;     
        counter++;
    }

    /* inherited methods */
    public int getID() {
        return this.id;
    }

    public double getNextServiceTime() {
        return this.serviceTime;
    }
        
    public Customer getCurCustomer() {
        return this.curCustomer;
    }
    
    public void setNextServiceTime(double time) {
        this.serviceTime = time;
    }

    public void setCurrent(Customer customer) {
        this.curCustomer = customer;
    }

    /* HumanServer operations */    
    public double getProbRest() { 
        return -1; // invalid for self-check
    }
    
    public boolean getRestFlag() { 
        return false; // default
    }

    public void setRestFlag() { }    

    /* abstract methods */    
    public abstract Queue<Customer> getQueue();

    public abstract void addToQueue(Customer customer);   

    public abstract Customer removeFromQueue();
    
    public abstract boolean queueFull();   
}
