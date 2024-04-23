package assignment2.sharedRegions;

import assignment2.main.SimulPar;
import assignment2.entities.*;
import genclass.GenericIO;
import genclass.TextFile;

/**
 *  General Repository.
 *
 *    It is responsible to keep the visible internal state of the problem and to provide means for it
 *    to be printed in the logging file.
 *    It is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are no internal synchronization points.
 */

public class GeneralRepos {

    /**
     * Name of the logging file.
    */
    private final String logFileName;
    /**
     * Legend to identify columns in the file.
    */
    private final String legend;
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
     * State of the coaches.
    */
    private final CoachStates[] coachState;
    /**
     * State of the contestants.
    */
    private final ContestantStates[][] contestantState;
    /**
     * Strength of the contestants.
    */
    private final int[][] contestantStrength;
    /**
     * Number and position of the contestants currently in the playground.
    */
    private final int[][] contestantPosition;

    /**
     *   Instantiation of a general repository object.
     *
     *     @param logFileName name of the logging file
     *     @param contestantStrength strength of each contestant of each team
    */
    public GeneralRepos() {
        //create file
        if ((logFileName == null) || logFileName.isEmpty())
            this.logFileName = "logger";
            else this.logFileName = logFileName;
        
        //initialize variables
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
        this.contestantStrength = contestantStrength;
        contestantPosition = new int[2][SimulPar.NC];

        //create legend string
        String legend1 = "";
        String legend2 = "";
        legend1 += "\nRef";
        legend2 += "\nSta";
        legend1 += " Coa 1";
        legend2 += "  Stat";
        for (int i = 1; i <= SimulPar.NC; i++) {
            legend1 += " Cont " + i;
            legend2 += " Sta SG";
        }
        legend1 += " Coa 2";
        legend2 += "  Stat";
        for (int i = 1; i <= SimulPar.NC; i++) {
            legend1 += " Cont " + i;
            legend2 += " Sta SG";
        }
        for (int i = SimulPar.NP; i > 0; i--) {
            legend1 += "  ";
            legend2 += " " + i;
        }
        legend1 += " Trial";
        legend2 += " .";
        for (int i = 1; i <= SimulPar.NP; i++) legend2 += " " + i;
        legend2 += " NB PS";
        legend = legend1 + legend2;

        //report
        reportInitialStatus();
        startGame();
    }
    /**
     *   Set referee state.
     *
     *   @param refereeState referee state
     */
    public synchronized void setRefereeState(RefereeStates refereeState) {
        if (this.refereeState!=refereeState) {
            this.refereeState = refereeState;
            reportStatus();
        }
    }
    /**
     *   Set coach state.
     *
     *   @param team coach team
     *   @param coachState coach state
     */
    public synchronized void setCoachState(int team, CoachStates coachState) {
        if (this.coachState[team]!=coachState) {
            this.coachState[team] = coachState;
            reportStatus();
        }
    }
    /**
     *   Set contestant state.
     *
     *   @param team contestant team
     *   @param number contestant number
     *   @param contestantState contestant state
     */
    public synchronized void setContestantState(int team, int number, ContestantStates contestantState) {
        if (this.contestantState[team][number]!=contestantState) {
            this.contestantState[team][number] = contestantState;
            reportStatus();
        }
    }
    /**
     *   Set contestant strength.
     *
     *   @param team contestant team
     *   @param number contestant number
     *   @param strength contestant strength
     */
    public synchronized void setContestantStrength(int team, int number, int strength) {
        contestantStrength[team][number] = strength;
    }
    /**
     *   Add contestant to the contestantPosition array.
     *
     *   @param team contestant team
     *   @param number contestant number
     */
    public synchronized void addContestant(int team, int number) {
        if (team==0) {
            for (int i = 0; i < SimulPar.NP; i++) {
                if (contestantPosition[team][i]==0) {
                    contestantPosition[team][i] = number+1;
                    break;
                }
            }
        }
        else {
            for (int i = SimulPar.NP-1; i >= 0; i--) {
                if (contestantPosition[team][i]==0) {
                    contestantPosition[team][i] = number+1;
                    break;
                }
            }
        }
    }
    /**
     *   Remove contestant from the contestantPosition array.
     *
     *   @param team contestant team
     *   @param number contestant number
     */
    public synchronized void removeContestant(int team, int number) {
        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[team][i]==number+1) {
                contestantPosition[team][i] = 0;
                break;
            }
        }
    }
    /**
     *   Set position of rope.
     *
     *   @param position rope position
     */
    public synchronized void setRopePosition(int position) {
        this.ropePosition = position;
    }
    /**
     *   Call trial.
     */
    public synchronized void callTrial() {
        nTrial++;
    }
    /**
     *   Start game.
     */
    public synchronized void startGame() {
        nGame++;
        nTrial = 0;
        reportStartOfGame();
        setRefereeState(RefereeStates.START_OF_A_GAME);
    }
    /** 
     * End game.
     *
     * @param team winning team
     * @param knockout true if game was won by knockout, false if game was won by points
     */
    public synchronized void endGame(int team, boolean knockout) {
        setRefereeState(RefereeStates.END_OF_A_GAME);
        reportEndOfGame(team, knockout);
        if (nGame+1<=SimulPar.NG) startGame();
    }
    /**
     * End match.
     * 
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public synchronized void endMatch(int score1, int score2) {
        reportEndOfMatch(score1, score2);
        setRefereeState(RefereeStates.END_OF_THE_MATCH);
    }
    /**
     *  Write the header to the logging file.
     */
    private void reportInitialStatus() {
        //open file
        TextFile log = new TextFile();
        if (!log.openForWriting(".", logFileName)) {
            GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }

        //print header
        log.writelnString("                               Game of the Rope - Description of the internal state");
        log.writelnString(legend);

        //close file
        if (!log.close()) {
            GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }

        //report
        reportStatus();
    }
    /**
     *  Write a line in the logging file indicating the start of a game.
     */
    private void reportStartOfGame() {
        //open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }
        
        //print header
        log.writeString("Game " + nGame);
        log.writelnString(legend);

        //close file
        if (!log.close()) {
            GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
    /**
     *  Write a line in the logging file indicating the outcome of the game
     * 
     * @param ropePosition position of the rope
     * @param knockout true if game was won by knockout, false if game was won by points
     */
    private void reportEndOfGame(int ropePosition, boolean knockout) {
        //open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        //print end of game
        if (ropePosition==0) log.writelnString(String.format("Game %d was a draw", nGame));
        else {
            int winner = 1;
            if (ropePosition>0) winner = 2;
            if (knockout) log.writelnString(String.format("Game %d was won by team %d by knockout in %d trials.", nGame, winner, nTrial));
            else if (ropePosition!=0) log.writelnString(String.format("Game %d was won by team %d by %d points.", nGame, winner, Math.abs(ropePosition)));
        }

        //close file
        if (!log.close()) {
            GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
    /**
     *  Write a line in the logging file indicating the outcome of the match
     * 
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    private void reportEndOfMatch(int score1, int score2) {
        //open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        //print end of match
        if (score1==score2) log.writelnString("Match was a draw.");
        else {
            int winner;
            if (score1>=score2) winner = 1;
            else winner = 2;
            log.writelnString(String.format("Match was won by team %d (%d-%d).", winner, score1, score2));
        }

        //close file
        if (!log.close()) {
            GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
    /**
     *  Write a state line at the end of the logging file.
     *
     *  The current state of the entities is organized in a line to be printed.
     *  Internal operation.
    */
    private void reportStatus() {
        //open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }
        
        //print status
        String lineStatus = "";

        lineStatus += refereeState;

        lineStatus += "  " + coachState[0];
        for (int i = 0; i < SimulPar.NC; i++) lineStatus += String.format(" %s %2d", contestantState[0][i], contestantStrength[0][i]);
        lineStatus += "  " + coachState[1];
        for (int i = 0; i < SimulPar.NC; i++) lineStatus += String.format(" %s %2d", contestantState[1][i], contestantStrength[1][i]);

        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[0][i]==0) lineStatus += " -";
            else lineStatus += " " + contestantPosition[0][i];
        }
        lineStatus += " " + ".";
        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[1][i]==0) lineStatus += " -";
            else lineStatus += " " + contestantPosition[1][i];
        }

        if (nTrial==0) lineStatus += "  -";
        else lineStatus += String.format(" %2d", nTrial);
        lineStatus += String.format(" %2d", ropePosition);

        //close file
        log.writelnString(lineStatus);
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
}
