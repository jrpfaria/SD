package assignment1.sharedRegions;

import assignment1.main.*;
import assignment1.entities.*;
import genclass.GenericIO;
import genclass.TextFile;

public class GeneralRepos {

    private final String logFileName;

    private int nGame;
    private int nTrial;
    private int ropePosition;
    private RefereeState refereeState;
    private final CoachState[] coachState;
    private final ContestantState[][] contestantState;
    private final short[][] contestantStrength;
    private final short[][] contestantPosition;

    public GeneralRepos(String logFileName, short[][] contestantStrength) {
        if ((logFileName == null) || logFileName.equals(""))
            this.logFileName = "logger";
            else this.logFileName = logFileName;
        nGame = 0;
        nTrial = 0;
        ropePosition = 0;
        refereeState = RefereeState.START_OF_THE_MATCH;
        coachState = new CoachState[2];
        for (int i = 0; i < 2; i++) {
            coachState[i] = CoachState.WAIT_FOR_REFEREE_COMMAND;
        }
        contestantState = new ContestantState[2][5];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                contestantState[i][j] = ContestantState.SEAT_AT_THE_BENCH;
            }
        }
        this.contestantStrength = contestantStrength;
        contestantPosition = new short[][]{{0, 0, 0}, {0, 0, 0}};
        reportInitialStatus();
    }

    public void setRefereeState(RefereeState refereeState) {
        this.refereeState = refereeState;
    }

    public void setCoachState(short team, CoachState coachState) {
        this.coachState[team-1] = coachState;
    }

    public void setContestantState(short team, short number, ContestantState contestantState) {
        this.contestantState[team-1][number-1] = contestantState;
    }

    public void setContestantStrength(short team, short number, short strength) {
        contestantStrength[team-1][number-1] = strength;
    }

    public void setContestantPosition(short team, short number, short position) {
        contestantPosition[team-1][position] = number;
    }

    public void setRopePosition(short position) {
        this.ropePosition = position;
    }

    public void startTrial() {
        nTrial++;
    }

    public void startOfGame() {
        nGame++;
        nTrial = 0;
        reportStartOfGame();
    }

    public void endOfGame(short team, boolean knockout) {
        reportEndOfGame(team, knockout);
    }

    private void reportInitialStatus() {
        TextFile log = new TextFile();

        if (!log.openForWriting(".", logFileName)) {
            GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }

        log.writelnString("                               Game of the Rope - Description of the internal state");
        log.writelnString("\nRef Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial");
        log.writelnString("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
        reportStatus();
    }

    private void reportStartOfGame() {
        TextFile log = new TextFile();

        if (!log.openForWriting(".", logFileName)) {
            GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }
        
        log.writelnString("Game " + nGame);
        log.writelnString("\nRef Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial");
        log.writelnString("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
        reportStatus();
    }

    private void reportEndOfGame(short team, boolean knockout) {
        TextFile log = new TextFile();

        if (!log.openForWriting(".", logFileName)) {
            GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }

        if (knockout) log.writelnString(String.format("Game %d was won by team %d by knockout in %d trials.", nGame, team, nTrial));
        else if (ropePosition!=0) log.writelnString(String.format("Game %d was won by team %d by %d points.", nGame, team, Math.abs(ropePosition)));
        else log.writelnString(String.format("Game %d was a draw", nGame));
    }

    private void reportStatus() {
        TextFile log = new TextFile();
        String lineStatus = "";

        if (!log.openForAppending(".", logFileName)) {
            GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        lineStatus += refereeState;

        lineStatus += "  " + coachState[0];
        for (int i = 0; i < 5; i++) lineStatus += " " + contestantState[0][i] + " " + contestantStrength[0][i];
        lineStatus += "  " + coachState[1];
        for (int i = 0; i < 5; i++) lineStatus += " " + contestantState[1][i] + " " + contestantStrength[1][i];

        if (nTrial==0) {
            lineStatus += " - - - . - - - -- --";
        }
        else {
            for (int i = 0; i < 3; i++) {
                if (contestantPosition[0][i]==0) lineStatus += " -";
                else lineStatus += " " + contestantPosition[0][i];
            }
            lineStatus += " " + ".";
            for (int i = 0; i < 3; i++) {
                if (contestantPosition[1][i]==0) lineStatus += " -";
                else lineStatus += " " + contestantPosition[1][i];
            }
        }

        log.writelnString(lineStatus);
        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
}
