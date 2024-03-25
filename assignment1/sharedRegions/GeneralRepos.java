package assignment1.sharedRegions;

import assignment1.main.SimulPar;
import assignment1.entities.*;
import genclass.GenericIO;
import genclass.TextFile;

public class GeneralRepos {

    private final String logFileName;

    private String legend;

    private int nGame;
    private int nTrial;
    private int ropePosition;
    private RefereeStates refereeState;
    private final CoachStates[] coachState;
    private final ContestantStates[][] contestantState;
    private final short[][] contestantStrength;
    private final short[][] contestantPosition;

    public GeneralRepos(String logFileName, short[][] contestantStrength) {
        //create file
        if ((logFileName == null) || logFileName.equals(""))
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
        contestantPosition = new short[2][SimulPar.NC];

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
    }

    public void setRefereeState(RefereeStates refereeState) {
        if (this.refereeState!=refereeState) {
            this.refereeState = refereeState;
            reportStatus();
        }
    }

    public void setCoachState(short team, CoachStates coachState) {
        if (this.coachState[team]!=coachState) {
            this.coachState[team] = coachState;
            reportStatus();
        }
    }

    public void setContestantState(short team, short number, ContestantStates contestantState) {
        if (this.contestantState[team][number]!=contestantState) {
            this.contestantState[team][number] = contestantState;
            reportStatus();
        }
    }

    public void setContestantStrength(short team, short number, short strength) {
        contestantStrength[team][number] = strength;
    }

    public void setContestantPosition(short team, short number, short position) {
        contestantPosition[team][position] = number;
    }

    public void addContestant(short team, short number) {
        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[team][i]==0) {
                contestantPosition[team][i] = (short)(number+1);
                break;
            }
        }
    }

    public void removeContestant(short team, short number) {
        for (int i = 0; i < SimulPar.NP; i++) {
            if (contestantPosition[team][i]==number+1) {
                contestantPosition[team][i] = 0;
                break;
            }
        }
    }

    public void setRopePosition(short position) {
        this.ropePosition = position;
    }

    public void startTrial() {
        nTrial++;
    }

    public void startGame() {
        nGame++;
        nTrial = 0;
        reportStartOfGame();
    }

    public void endGame(short team, boolean knockout) {
        reportEndOfGame(team, knockout);
    }

    public void endMatch(short score1, short score2) {
        reportEndOfMatch(score1, score2);
    }

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

        //report
        reportStatus();
    }

    private void reportEndOfGame(short ropePosition, boolean knockout) {
        //open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        //print end of game
        if (ropePosition==0) log.writelnString(String.format("Game %d was a draw", nGame));
        else {
            short winner = 1;
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

    private void reportEndOfMatch(short score1, short score2) {
        //open file
        TextFile log = new TextFile();
        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        //print end of match
        if (score1==score2) log.writelnString("Match was a draw.");
        else {
            short winner;
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
            if (nTrial==0 || contestantPosition[0][i]==0) lineStatus += " -";
            else lineStatus += " " + contestantPosition[0][i];
        }
        lineStatus += " " + ".";
        for (int i = 0; i < SimulPar.NP; i++) {
            if (nTrial==0 || contestantPosition[1][i]==0) lineStatus += " -";
            else lineStatus += " " + contestantPosition[1][i];
        }

        lineStatus += String.format(" %2d %2d", nTrial, ropePosition);

        //close file
        log.writelnString(lineStatus);
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
}
