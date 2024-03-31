package assignment1.sharedRegions;

import assignment1.main.SimulPar;
import assignment1.entities.*;
import assignment1.commonInfra.Pair;

/**
 *    ContestantsBench.
 *
 *    It is responsible to keep a continuously updated account of the strength of each contestant
 *    and how many contestants are in the bench.
 *    All public methods are executed in mutual exclusion
 *    (except for seat_at_the_bench which is divided in two mutual exclusion blocks)
 *    There are three internal synchronization points: the coaches wait for their contestants to be seated
 *    and for the orders of the referee,
 *    and the contestants wait for either the match to end or for their coach to give them orders.
 */
public class ContestantsBench {
    /**
     *   Reference to the general repository.
     */
    private GeneralRepos repos;
    /**
     *   Flag that indicates that match is over.
     */
    private boolean matchOver;
    /**
     *   Flag that indicates that a trial was called.
     */
    private int callTrial;
    /**
     *   Arrays of orders, one value for each contestant.
     */
    private int[][] called;
    /**
     *   Number of players in each team that are in the bench.
     */
    private int seated[];
    /**
     *   Array of Pairs that map a contestant's team and number to their strength.
     */
    private Pair<Integer, Integer>[][] contestants;

    /**
     *  ContestantsBench instantiation.
     *
     *    @param repos reference to the general repository
     */
    @SuppressWarnings("unchecked")
    public ContestantsBench(GeneralRepos repos) {
        this.repos = repos;
        called = new int[2][SimulPar.NC];
        seated = new int[2];
        contestants = (Pair<Integer, Integer>[][]) new Pair[2][];
        contestants[0] = (Pair<Integer, Integer>[]) new Pair[SimulPar.NC];
        contestants[1] = (Pair<Integer, Integer>[]) new Pair[SimulPar.NC];
        for (int i = 0; i < SimulPar.NC; i++) {
            contestants[0][i] = new Pair<Integer, Integer>(i, 0);
            contestants[1][i] = new Pair<Integer, Integer>(i, 0);
        }
    }

    //Referee

    /** 
     *  Operation callTrial
     *  Called by the referee to notify the coaches that a trial was called.
     */
    public synchronized void callTrial() {
        callTrial = 2;
        notifyAll();
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.TEAMS_READY);
        repos.setRefereeState(RefereeStates.TEAMS_READY);
    }

    /** 
     *  Operation declareMatchWinner
     *  Called by the referee to notify coaches and contestants that the match is over.
     *  Call the general repository to log the end of the match.
     * 
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public synchronized void declareMatchWinner(int score1, int score2) {
        matchOver = true;
        notifyAll();
        repos.endMatch(score1, score2);
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.END_OF_THE_MATCH);
        repos.setRefereeState(RefereeStates.END_OF_THE_MATCH);
    }

    //Coach

    /** 
     *  Operation reviewNotes
     *  Called by the coaches to obtain the strength of each of their players, after they're all in the bench.
     * 
     *  @param team team of coach
     */
    public synchronized Pair<Integer, Integer>[] reviewNotes(int team) {
        while (seated[team]<SimulPar.NC) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        return contestants[team];
    }

    /** 
     *  Operation wait_for_referee_command
     *  The coaches wait for either the match to end or for a trial to be called
     * 
     *  @param team team of coach
     *  @returns 0 if match is over, 1 if trial was called
     */
    public synchronized int wait_for_referee_command(int team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.WAIT_FOR_REFEREE_COMMAND);
        repos.setCoachState(team, CoachStates.WAIT_FOR_REFEREE_COMMAND);
        while (!matchOver && callTrial==0) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        if (matchOver) return 0;
        callTrial--;
        return 1;
    }

    /** 
     *  Operation callContestants
     *  Called by the coaches to notify their contestants that a trial was called and which players will participate.
     *  
     * @param team team of coach
     * @param roster numbers of the contestants that will participate
     */
    public synchronized void callContestants(int team, int[] roster) {
        for (int i = 0; i < SimulPar.NC; i++) called[team][i] = 1;
        for (int number: roster) called[team][number] = 2;
        notifyAll();
    }

    //Contestants

    /** 
     *  Operation seat_at_the_bench
     *  Called by the contestants to update their strength, and wait for either the match to end or a trial to be called.
     *  
     * @param team team of contestant
     * @param number of contestant
     * @returns 0 if match is over, 1 if player has to stay in the bench, 2 if player will participate in the trial
     */
    public int seat_at_the_bench(int team, int number) {
        Contestant c;
        synchronized (this) {
            c = (Contestant)Thread.currentThread();
            c.setContestantState(ContestantStates.SEAT_AT_THE_BENCH);
            repos.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
            contestants[team][number].setValue(c.getStrength());
            seated[team]++;
            notifyAll();
        }
        synchronized (this) {
            while (!matchOver && called[team][number]==0) {
                try {wait();}
                catch (InterruptedException e) {}
            }
            if (matchOver) return 0;
            seated[team]--;
            int orders = called[team][number];
            if (orders==1) {
                c.increaseStrength();
                repos.setContestantStrength(team, number, c.getStrength());
            }
            called[team][number] = 0;
            return orders;
        }
    }

    /** 
     *  Operation seatDown
     *  Called by the contestants to update their strength and remove their number from the general repository.
     *  
     * @param team team of contestant
     * @param number of contestant
     */
    public synchronized void seatDown(int team, int number) {
        Contestant c = (Contestant)Thread.currentThread();
        c.reduceStrength();
        repos.removeContestant(team, number);
        repos.setContestantStrength(team, number, c.getStrength());
    }
}
