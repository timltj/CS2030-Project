package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

/** 
 * Encapsulates the simulation of events.
 */
public class Simulator {
    /**
     * Runs the simulator when called through the Simulator class in main.
     *
     * @param seed an int value denoting the base seed for the RandomGenerator object
     * @param numSer an int value representing the number of servers
     * @param numSelf int value representing the number of self-checkout counters
     * @param maxQ an int value for the maximum queue length
     * @param numCus an int representing the number of customers 
     *      (or the number of arrival events) to simulate
     * @param arrRate a positive double parameter for the arrival rate, lambda
     * @param serRate a positive double parameter for the service rate, mu
     * @param restRate a positive double parameter for the resting rate, rho
     * @param probRest a double parameter for the probability of resting, Pr
     * @param probGreed a double parameter for the probability of a greedy customer occurring, Pg
     */
    public static void run(int seed, int numSer, int numSelf, int maxQ, int numCus, 
            double arrRate, double serRate, double restRate, double probRest, double probGreed) {
        /* random number generator */
        RandomGenerator rg = new RandomGenerator(seed, arrRate, serRate, restRate);
        
        /* priority queue to store events */
        PriorityQueue<Event> eventQ = new PriorityQueue<Event>();

        /* list to store servers */    
        List<Server> serList = new ArrayList<Server>();
        
        HumanServer.probRest = probRest;

        for (int i = 0; i < numSer + numSelf; i++) { 
            if (i < numSer) {
                serList.add(new HumanServer(maxQ, rg));
                Server.numHumanServer++;
            } else {
                serList.add(new SelfCheckout(maxQ, rg));
            }
        }        
 
        /* list to store arrival times */ 
        List<Double> arrTimeList = new ArrayList<Double>();    

        double arrTime = 0;
        for (int i = 0; i < numCus; i++) {
            double greedFactor = rg.genCustomerType();
            if (i == 0) {                
                arrTimeList.add(0.0);                  
                if (greedFactor < probGreed) {
                    eventQ.add(new ArrivalEvent(new GreedyCustomer(0.0), 0.0, serList, rg));
                    continue;
                }                
                eventQ.add(new ArrivalEvent(new Customer(0.0), 0.0, serList, rg));
            } else {
                arrTime += rg.genInterArrivalTime();
                arrTimeList.add(arrTime);
                if (greedFactor < probGreed) {
                    eventQ.add(new ArrivalEvent(new GreedyCustomer(arrTime), arrTime, serList, rg));
                    continue;
                }
                eventQ.add(new ArrivalEvent(new Customer(arrTime), arrTime, serList, rg));
            }         
        }

        /* main loop */
        while (!eventQ.isEmpty()) {
            if (eventQ.peek().getStatus() == 1 ||
                    eventQ.peek().getStatus() == 3 ||
                    eventQ.peek().getStatus() == 4 ||
                    eventQ.peek().getStatus() == 9) { // ARRIVAL, WAIT, DONE or SELF_DONE
                Event event = eventQ.peek().getNextEvent();
                if (event != null) { // WAIT null check
                    eventQ.add(event);
                }
                System.out.println(eventQ.poll());
            } else if (eventQ.peek().getStatus() == 2 ||
                    eventQ.peek().getStatus() == 8) { // SERVED or SELF_SERVED       
                if (eventQ.peek().getServer().getCurCustomer().getID() !=
                        eventQ.peek().getCustomer().getID()) {
                    Customer customer = eventQ.peek().getCustomer();
                    Server server = eventQ.peek().getServer();
                    eventQ.add(new ServedEvent(customer, server.getNextServiceTime(), server, rg));
                    eventQ.poll();
                } else {               
                    eventQ.add(eventQ.peek().getNextEvent());
                    System.out.println(eventQ.poll());
                }   
            } else if (eventQ.peek().getStatus() == 6) { // SERVER_REST
                eventQ.add(eventQ.peek().getNextEvent());
                eventQ.poll();                        
            } else if (eventQ.peek().getStatus() == 7) { // SERVER_BACK
                eventQ.peek().getNextEvent();
                eventQ.poll();              
            } else { // LEAVE
                System.out.println(eventQ.poll());
            }           
        }

        /* output statistics */
        System.out.println("[" + String.format("%.3f", Statistics.getAverageWait()) +
                " " + Statistics.getServed() +
                " " + Statistics.getLeft() + "]");        
    }
}
