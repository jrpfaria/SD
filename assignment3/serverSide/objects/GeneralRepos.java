package serverSide.objects;

import java.rmi.RemoteException;
import interfaces.*;
import clientSide.entities.*;
import genclass.GenericIO;
import genclass.TextFile;
import serverSide.main.*;
import serverSide.main.SimulPar;

/**
 * General Repository.
 * 
 * It is responsible to keep the visible internal state of the problem and to
 * provide means for it
 * to be printed in the logging file.
 * It is implemented as an implicit monitor.
 * All public methods are executed in mutual exclusion.
 * There are no internal synchronization points.
 */

public class GeneralRepos implements GeneralReposInterface {

    /**
     * Legend to identify columns in the file.
     */
    private final String legend;
    /**
     * State of the coaches.
     */
    private final CoachStates[] coachState;
    /**
     * State of the contestants.
     */
    private final ContestantStates[][] contestantState;
    /**
     * Number and position of the contestants currently in the playground.
     */
    private final int[][] contestantPosition;
    private boolean init;
    /**
     * Name of the logging file.
     */
    private String logFileName;
    /**
     * Number of current game.
     */
    private int nGame;
    /**
     * Number of current trial.
     */
    private int nTrial;
    /**
     * Position of the rope.
     */
    private int ropePosition;
    /**
     * State of the referee.
     */
    private RefereeStates refereeState;
    /**
     * Strength of the contestants.
     */
    private int[][] contestantStrength;
    private int nEntities = 0;

    /**
     * Instantiation of a general repository object.
     */
    public GeneralRepos() {
        // initialize variables
        nGame = 0;
        nTrial = 0;
        ropePosition = 0;
        refereeState = RefereeStates.START_OF_THE_MATCH;
        coachState = new CoachStates[2];
        for (int i = 0; i < 2; i++) {
            coachState[i] = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        }
        contestantState = new ContestantStates[2][SimulPar.NC];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestantState[i][j] = ContestantStates.SEAT_AT_THE_BENCH;
            }
        }

        contestantPosition = new int[2][SimulPar.NC];

        // create legend string
        StringBuilder legend1 = new StringBuilder();
        StringBuilder legend2 = new StringBuilder();
        legend1.append("\nRef");
        legend2.append("\nSta");
        legend1.append(" Coa 1");
        legend2.append("  Stat");
        for (int i = 1; i <= SimulPar.NC; i++) {
            legend1.append(" Cont ").append(i);
            legend2.append(" Sta SG");
        }
        legend1.append(" Coa 2");
        legend2.append("  Stat");
        for (int i = 1; i <= SimulPar.NC; i++) {
            legend1.append(" Cont ").append(i);
            legend2.append(" Sta SG");
        }
        for (int i = SimulPar.NP; i > 0; i--) {
            legend1.append("  ");
            legend2.append(" ").append(i);
        }
        legend1.append(" Trial");
        legend2.append(" .");
        for (int i = 1; i <= SimulPar.NP; i++)
            legend2.append(" ").append(i);
        legend2.append(" NB PS");
        legend = legend1 + legend2.toString();
    }

    @Override
    public synchronized void initSimul(String logFileName, int[][] contestantStrength) throws RemoteException {
        this.logFileName = logFileName;
        this.contestantStrength = contestantStrength;

        // report
        reportInitialStatus();

        init = true;
        notifyAll();
    }

    /**
     * Set referee state.
     *
     * @param refereeState referee state
     */
    @Override
    public synchronized void setRefereeState(int state) throws RemoteException {
        check_init();
        RefereeStates refereeState = RefereeStates.values()[state];
        if (this.refereeState != refereeState) {
            this.refereeState = refereeState;
            reportStatus();
        }
    }

    /**
     * Set coach state.
     *
     * @param team       coach team
     * @param coachState coach state
     */
    @Override
    public synchronized void setCoachState(int team, int state) throws RemoteException {
        check_init();
        CoachStates coachState = CoachStates.values()[state];
        if (this.coachState[team] != coachState) {
            this.coachState[team] = coachState;
            reportStatus();
        }
    }

    /**
     * Set contestant state.
     *
     * @param team            contestant team
     * @param number          contestant number
     * @param contestantState contestant state
     */
    @Override
    public synchronized void setContestantState(int team, int number, int state)
            throws RemoteException {
        check_init();
        ContestantStates contestantState = ContestantStates.values()[state];
        if (this.contestantState[team][number] != contestantState) {
            this.contestantState[team][number] = contestantState;
            reportStatus();
        }
    }

    /**
     * Set contestant strength.
     *
     * @param team     contestant team
     * @param number   contestant number
     * @param strength contestant strength
     */
    @Override
    public synchronized void setContestantStrength(int team, int number, int strength) throws RemoteException {
        check_init();

        contestantStrength[team][number] = strength;
    }

    /**
     * Add contestant to the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     */
    @Override
    public synchronized void addContestant(int team, int number) throws RemoteException {
        check_init();

        if (team == 0) {
            for (int i = 0; i < SimulPar.NP; i++) {
                if (contestantPosition[team][i] == 0) {
                    contestantPosition[team][i] = number + 1;
                    break;
                }
            }
        } else {
            for (int i = SimulPar.NP - 1; i >= 0; i--) {
                if (contestantPosition[team][i] == 0) {
                    contestantPosition[team][i] = number + 1;
                    break;
                }
            }
        }
    }

    /**
     * Remove contestant from the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     */
    @Override
    public synchronized void removeContestant(int team, int number) throws RemoteException {
        check_init();

        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[team][i] == number + 1) {
                contestantPosition[team][i] = 0;
                break;
            }
        }
    }

    /**
     * Set position of rope.
     *
     * @param position rope position
     */
    @Override
    public synchronized void setRopePosition(int position) throws RemoteException {
        check_init();

        this.ropePosition = position;
    }

    /**
     * Call trial.
     */
    @Override
    public synchronized void callTrial() throws RemoteException {
        check_init();

        nTrial++;
    }

    /**
     * Start game.
     */
    @Override
    public synchronized void startGame() throws RemoteException {
        check_init();

        nGame++;
        nTrial = 0;
        reportStartOfGame();
        if (this.refereeState != RefereeStates.START_OF_A_GAME) {
            this.refereeState = RefereeStates.START_OF_A_GAME;
            reportStatus();
        }
    }

    /**
     * End game.
     *
     * @param team     winning team
     * @param knockout true if game was won by knockout, false if game was won by
     *                 points
     */
    @Override
    public synchronized void endGame(int team, boolean knockout) throws RemoteException {
        check_init();

        setRefereeState(RefereeStates.END_OF_A_GAME.ordinal());
        reportEndOfGame(team, knockout);
    }

    /**
     * End match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    @Override
    public synchronized void endMatch(int score1, int score2) throws RemoteException {
        check_init();

        reportEndOfMatch(score1, score2);
        setRefereeState(RefereeStates.END_OF_THE_MATCH.ordinal());
    }

    private synchronized void check_init() {
        while (!init) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Write the header to the logging file.
     */
    private void reportInitialStatus() {
        // open file
        TextFile log = new TextFile();
        if (!log.openForWriting(".", logFileName)) {
            GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }

        // print header
        log.writelnString("                               Game of the Rope - Description of the internal state");
        log.writelnString(legend);

        // close file
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }

        // report
        reportStatus();
    }

    /**
     * Write a line in the logging file indicating the start of a game.
     */
    private void reportStartOfGame() {
        // open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        // print header
        log.writeString("Game " + nGame);
        log.writelnString(legend);

        // close file
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }

    /**
     * Write a line in the logging file indicating the outcome of the game
     *
     * @param ropePosition position of the rope
     * @param knockout     true if game was won by knockout, false if game was won
     *                     by points
     */
    private void reportEndOfGame(int ropePosition, boolean knockout) {
        // open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        // print end of game
        if (ropePosition == 0)
            log.writelnString(String.format("Game %d was a draw", nGame));
        else {
            int winner = 1;
            if (ropePosition > 0)
                winner = 2;
            if (knockout)
                log.writelnString(
                        String.format("Game %d was won by team %d by knockout in %d trials.", nGame, winner, nTrial));
            else
                log.writelnString(String.format("Game %d was won by team %d by %d points.", nGame, winner,
                        Math.abs(ropePosition)));
        }

        // close file
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }

    /**
     * Write a line in the logging file indicating the outcome of the match
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    private void reportEndOfMatch(int score1, int score2) {
        // open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        // print end of match
        if (score1 == score2)
            log.writelnString("Match was a draw.");
        else {
            int winner;
            if (score1 >= score2)
                winner = 1;
            else
                winner = 2;
            log.writelnString(String.format("Match was won by team %d (%d-%d).", winner, score1, score2));
        }

        // close file
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }

    /**
     * Write a state line at the end of the logging file.
     * 
     * The current state of the entities is organized in a line to be printed.
     * Internal operation.
     */
    private void reportStatus() {
        // open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        // print status
        StringBuilder lineStatus = new StringBuilder();

        lineStatus.append(refereeState);

        lineStatus.append("  ").append(coachState[0]);
        for (int i = 0; i < SimulPar.NC; i++)
            lineStatus.append(String.format(" %s %2d", contestantState[0][i], contestantStrength[0][i]));
        lineStatus.append("  ").append(coachState[1]);
        for (int i = 0; i < SimulPar.NC; i++)
            lineStatus.append(String.format(" %s %2d", contestantState[1][i], contestantStrength[1][i]));

        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[0][i] == 0)
                lineStatus.append(" -");
            else
                lineStatus.append(" ").append(contestantPosition[0][i]);
        }
        lineStatus.append(" " + ".");
        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[1][i] == 0)
                lineStatus.append(" -");
            else
                lineStatus.append(" ").append(contestantPosition[1][i]);
        }

        if (nTrial == 0)
            lineStatus.append("  -");
        else
            lineStatus.append(String.format(" %2d", nTrial));
        lineStatus.append(String.format(" %2d", ropePosition));

        // close file
        log.writelnString(lineStatus.toString());
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }

    //

    @Override
    public synchronized void shutdown() throws RemoteException {
        nEntities += 1;
        if (nEntities >= SimulPar.E)
            ServerGameOfRopeGeneralRepos.shutdown();
        notifyAll();
    }
}
