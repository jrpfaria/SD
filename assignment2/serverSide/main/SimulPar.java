package serverSide.main;

/**
 *    Definition of the simulation parameters.
 */

public class SimulPar {
    /**
     * number of games, default = 3
    */
    public static final int NG = 3;
    /**
     * number of trials, default = 6
    */
    public static final int NT = 6; // 
    /**
     * knockout treshold, default = 4
    */
    public static final int KT = 4; 
    /**
     * number of contestants per team, default = 5
    */
    public static final int NC = 5;
    /**
     * number of active player per team, default = 3
    */
    public static final int NP = 3;
    /**
     * min strength of contestants, default = 6
    */
    public static final int MINS = 6;
    /**
     * max strength of contestants, default = 10
    */
    public static final int MAXS = 10;
    /**
     * min sleep time for contestant in ms, default = 1000
    */
    public static final int MINT = 100;
    /**
     * max sleep time for contestant in ms, default = 5000
    */
    public static final int MAXT = 500;
}
