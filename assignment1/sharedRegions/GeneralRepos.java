package assignment1.sharedRegions;

import assignment1.main.*;
import assignment1.entities.*;
import genclass.GenericIO;
import genclass.TextFile;

public class GeneralRepos {
    private final String logFileName;

    private final int nGame;
    private final int nTrial;
    private final int ropePosition;
    private final RefereeState refereeState;
    private final CoachState[] coachState;
    private final ContestantState[][] contestantState;
    private final short[][] contestantStrength;
    private final short[] contestantPosition;

    public GeneralRepos(String logFileName) {
        if ((logFileName == null) || logFileName.equals(""))
            this.logFileName = "logger";
            else this.logFileName = logFileName;
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
        reportInitialStatus();
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

    private void reportEnd
}
