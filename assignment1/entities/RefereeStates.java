package assignment1.entities;

public enum RefereeStates {
    START_OF_THE_MATCH,
    START_OF_A_GAME,
    TEAMS_READY,
    WAIT_FOR_TRIAL_CONCLUSION,
    END_OF_A_GAME,
    END_OF_THE_MATCH;

    @Override
    public String toString() {
        switch (this) {
            case START_OF_THE_MATCH:
                return "SOM";
            case START_OF_A_GAME:
                return "SOG";
            case TEAMS_READY:
                return "TRY";
            case WAIT_FOR_TRIAL_CONCLUSION:
                return "WTC";
            case END_OF_A_GAME:
                return "EOG";
            case END_OF_THE_MATCH:
                return "EOM";
            default:
                throw new IllegalArgumentException();
        }
    }
}
