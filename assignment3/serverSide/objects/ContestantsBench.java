package serverSide.objects;

import java.rmi.RemoteException;
import interfaces.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.main.*;

/**
 * ContestantsBench.
 * 
 * It is responsible to keep a continuously updated account of the strength of
 * each contestant
 * and how many contestants are in the bench.
 * 
 * public methods are executed in mutual exclusion
 * (except for seat_at_the_bench which is divided in two mutual
 * exclusion blocks)
 * There are three internal synchronization points: the coaches
 * wait for their contestants to be seated
 * and for the orders of the referee,
 * and the contestants wait for either the match to end or for
 * their coach to give them orders.
 */
public class ContestantsBench implements ContestantsBenchInterface {
    /**
     * Reference to the general repository.
     */
    private final GeneralReposInterface reposStub;
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
    public ContestantsBench(GeneralReposInterface reposStub) {
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

    // Referee

    /**
     * Operation callTrial
     * Called by the referee to notify the coaches that a trial was called.
     */
    @Override
    public synchronized void callTrial() throws RemoteException {
        callTrial = 2;
        notifyAll();
        try {
            reposStub.callTrial();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on callTrial - callTrial: " + e.getMessage());
            System.exit(1);
        }
        try {
            reposStub.setRefereeState(RefereeStates.TEAMS_READY);
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on callTrial - setRefereeState: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Operation declareMatchWinner
     * Called by the referee to notify coaches and contestants that the match is
     * over.
     * Call the general repository to log the end of the match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    @Override
    public synchronized void declareMatchWinner(int score1, int score2) throws RemoteException {
        try {
            reposStub.endMatch(score1, score2);
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on declareMatchWinner - endMatch: " + e.getMessage());
            System.exit(1);
        }
        matchOver = true;
        notifyAll();
    }

    // Coach

    /**
     * Operation reviewNotes
     * Called by the coaches to obtain the strength of each of their players, after
     * they're all in the bench.
     *
     * @return roster of players
     */
    @Override
    public synchronized Pair<Integer, Integer>[] reviewNotes(int team) throws RemoteException {
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
    @Override
    public synchronized int wait_for_referee_command(int team) throws RemoteException {
        reposStub.setCoachState(team, CoachStates.WAIT_FOR_REFEREE_COMMAND);
        while (!matchOver && callTrial == 0) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        if (matchOver)
            return 0;
        callTrial--;
        return 1;
    }

    /**
     * Operation callContestants
     * Called by the coaches to notify their contestants that a trial was called and
     * which players will participate.
     *
     * @param roster numbers of the contestants that will participate
     */
    @Override
    public synchronized void callContestants(int team, int[] roster) throws RemoteException {
        for (int i = 0; i < SimulPar.NC; i++)
            called[team][i] = 1;
        for (int number : roster)
            called[team][number] = 2;
        notifyAll();
    }

    // Contestants

    /**
     * Operation seat_at_the_bench
     * Called by the contestants to update their strength, and wait for either the
     * match to end or a trial to be called.
     *
     * @return 0 if match is over, 1 if player has to stay in the bench, 2 if player
     *         will participate in the trial
     */
    @Override
    public int seat_at_the_bench(int team, int number, int strength) throws RemoteException {
        synchronized (this) {
            reposStub.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
            reposStub.setContestantStrength(team, number, strength);
            contestants[team][number].setValue(strength);
            seated[team]++;
            notifyAll();
        }
        synchronized (this) {
            while (!matchOver && called[team][number] == 0) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            if (matchOver)
                return 0;
            seated[team]--;
            int orders = called[team][number];
            called[team][number] = 0;
            return orders;
        }
    }

    /**
     * Operation seatDown
     * Called by the contestants to update their strength and remove their number
     * from the general repository.
     */
    @Override
    public synchronized void seatDown(int team, int number) throws RemoteException {
        reposStub.removeContestant(team, number);
        reposStub.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
    }

    //

    @Override
    public synchronized void shutdown() throws RemoteException {
        nEntities += 1;
        if (nEntities >= SimulPar.E)
            ServerGameOfRopeContestantsBench.shutdown();
        notifyAll();
    }
}
