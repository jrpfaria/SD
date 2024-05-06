package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.main.*;

/**
 * ContestantsBench.
 * <p>
 * It is responsible to keep a continuously updated account of the strength of each contestant
 * and how many contestants are in the bench.
 * All public methods are executed in mutual exclusion
 * (except for seat_at_the_bench which is divided in two mutual exclusion blocks)
 * There are three internal synchronization points: the coaches wait for their contestants to be seated
 * and for the orders of the referee,
 * and the contestants wait for either the match to end or for their coach to give them orders.
 */
public class ContestantsBench {
    /**
     * Reference to the general repository.
     */
    private final GeneralReposStub reposStub;
    /**
     * Arrays of orders, one value for each contestant.
     */
    private final int[][] called;
    /**
     * Number of players in each team that are in the bench.
     */
    private final int[] seated;
    /**
     * Array of Pairs that map a contestant's team and number to their strength.
     */
    private final Pair<Integer, Integer>[][] contestants;
    /**
     * Flag that indicates that match is over.
     */
    private boolean matchOver;
    /**
     * Flag that indicates that a trial was called.
     */
    private int callTrial;
    private int nEntities = 0;

    /**
     * ContestantsBench instantiation.
     *
     * @param reposStub reference to the general repository stub
     */
    public ContestantsBench(GeneralReposStub reposStub) {
        this.reposStub = reposStub;
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
     * Operation callTrial
     * Called by the referee to notify the coaches that a trial was called.
     */
    public synchronized void callTrial() {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        callTrial = 2;
        notifyAll();
        reposStub.callTrial();
        t.setRefereeState(RefereeStates.TEAMS_READY);
        reposStub.setRefereeState(RefereeStates.TEAMS_READY);
    }

    /**
     * Operation declareMatchWinner
     * Called by the referee to notify coaches and contestants that the match is over.
     * Call the general repository to log the end of the match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public synchronized void declareMatchWinner(int score1, int score2) {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        t.setRefereeState(RefereeStates.END_OF_THE_MATCH);
        reposStub.endMatch(score1, score2);
        matchOver = true;
        notifyAll();
    }

    //Coach

    /**
     * Operation reviewNotes
     * Called by the coaches to obtain the strength of each of their players, after they're all in the bench.
     *
     * @return roster of players
     */
    public synchronized Pair<Integer, Integer>[] reviewNotes() {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        int team = t.getCoachTeam();
        while (seated[team] < SimulPar.NC) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        return contestants[team];
    }

    /**
     * Operation wait_for_referee_command
     * The coaches wait for either the match to end or for a trial to be called
     *
     * @return 0 if match is over, 1 if trial was called
     */
    public synchronized int wait_for_referee_command() {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        int team = t.getCoachTeam();
        t.setCoachState(CoachStates.WAIT_FOR_REFEREE_COMMAND);
        reposStub.setCoachState(team, CoachStates.WAIT_FOR_REFEREE_COMMAND);
        while (!matchOver && callTrial == 0) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        if (matchOver) return 0;
        callTrial--;
        return 1;
    }

    /**
     * Operation callContestants
     * Called by the coaches to notify their contestants that a trial was called and which players will participate.
     *
     * @param roster numbers of the contestants that will participate
     */
    public synchronized void callContestants(int[] roster) {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        int team = t.getCoachTeam();
        for (int i = 0; i < SimulPar.NC; i++) called[team][i] = 1;
        for (int number : roster) called[team][number] = 2;
        notifyAll();
    }

    //Contestants

    /**
     * Operation seat_at_the_bench
     * Called by the contestants to update their strength, and wait for either the match to end or a trial to be called.
     *
     * @return 0 if match is over, 1 if player has to stay in the bench, 2 if player will participate in the trial
     */
    public int seat_at_the_bench() {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        int team = t.getContestantTeam();
        int number = t.getContestantNumber();
        int strength = t.getContestantStrength();
        synchronized (this) {
            GenericIO.writelnString("seating " + team + "-" + number);
            t.setContestantState(ContestantStates.SEAT_AT_THE_BENCH);
            reposStub.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
            contestants[team][number].setValue(strength);
            seated[team]++;
            GenericIO.writelnString("seated" + team + " = " + seated[team]);
            notifyAll();
        }
        synchronized (this) {
            while (!matchOver && called[team][number] == 0) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            if (matchOver) return 0;
            seated[team]--;
            int orders = called[team][number];
            called[team][number] = 0;
            return orders;
        }
    }

    /**
     * Operation seatDown
     * Called by the contestants to update their strength and remove their number from the general repository.
     */
    public synchronized void seatDown() {
        ContestantsBenchClientProxy t = (ContestantsBenchClientProxy) Thread.currentThread();
        int team = t.getContestantTeam();
        int number = t.getContestantNumber();
        int strength = t.getContestantStrength();
        reposStub.removeContestant(team, number);
        reposStub.setContestantStrength(team, number, strength);
    }

    //

    public synchronized void shutdown() {
        nEntities += 1;
        if (nEntities >= SimulPar.E) ServerGameOfRopeContestantsBench.waitConnection = false;
        notifyAll();
    }
}
