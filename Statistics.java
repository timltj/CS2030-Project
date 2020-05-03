package cs2030.simulator;

/** 
 * Encapsulates the computation of statistics during the simulation.
 */
public class Statistics {
    // attributes    
    private static double totalWait = 0;
    private static int served = 0;
    private static int left = 0;

    /* client operations */
    public static void addLeft() {
        Statistics.left++;
    }

    public static void addTotalWait(double time) {
        Statistics.served++; 
        Statistics.totalWait += time;    
    }

    /* getters */    
    public static double getTotalWait() {
        return Statistics.totalWait;
    }

    public static int getServed() {
        return Statistics.served;
    }

    public static int getLeft() {
        return Statistics.left;
    }

    /**
     * Computes the average waiting time.
     * @return returns the average waiting time
     */
    public static double getAverageWait() {        
        if (Statistics.served == 0) {
            return 0.000;
        }        
        return Statistics.totalWait / Statistics.served;
    }
}
