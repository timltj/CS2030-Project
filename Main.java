import java.util.Scanner;

import cs2030.simulator.ArrivalEvent;
import cs2030.simulator.Customer;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.Event;
import cs2030.simulator.GreedyCustomer;
import cs2030.simulator.HumanServer;
import cs2030.simulator.LeaveEvent;
import cs2030.simulator.RandomGenerator;
import cs2030.simulator.SelfCheckout;
import cs2030.simulator.SelfDoneEvent;
import cs2030.simulator.SelfServedEvent;
import cs2030.simulator.ServedEvent;
import cs2030.simulator.Server;
import cs2030.simulator.ServerBack;
import cs2030.simulator.ServerRest;
import cs2030.simulator.Simulator;
import cs2030.simulator.Statistics;
import cs2030.simulator.WaitEvent;

/** 
 * Provides a main client to start simulation.
 */
public class Main {
    /**
     * Invokes the running of the Simulator class to simulate the execution of events.
     * Extracts the information needed for the Discrete Event Simulator from the input arguments.
     * Precondition: 'args' should be 10 tokens required for the Discrete Event Simulator.
     *
     * @param  args            the command-line arguments.     
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        /* input */
        int seed = sc.nextInt();
        int numSer = sc.nextInt();
        int numSelf = sc.nextInt();
        int maxQ = sc.nextInt();
        int numCus = sc.nextInt();
        double arrRate = sc.nextDouble();
        double serRate = sc.nextDouble(); 
        double restRate = sc.nextDouble();
        double probRest = sc.nextDouble();
        double probGreed = sc.nextDouble();

        sc.close();

        /* run simulation */
        Simulator.run(seed, numSer, numSelf, maxQ, numCus, 
                arrRate, serRate, restRate, probRest, probGreed);    
    }    
}
