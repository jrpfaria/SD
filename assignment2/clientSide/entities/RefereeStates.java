package clientSide.entities;

/**
 * The RefereeStates enum represents the possible states of a referee in the game simulation.
 * Referees can be in one of the following states: START_OF_THE_MATCH, START_OF_A_GAME, TEAMS_READY,
 * WAIT_FOR_TRIAL_CONCLUSION, END_OF_A_GAME, END_OF_THE_MATCH.
 */
public enum RefereeStates {
    /**
     * Represents the state at the start of a match.
     */
    START_OF_THE_MATCH,

    /**
     * Represents the state at the start of a game within a match.
     */
    START_OF_A_GAME,

    /**
     * Represents the state where teams are ready for a trial to begin.
     */
    TEAMS_READY,

    /**
     * Represents the state where the referee is waiting for the conclusion of a trial.
     */
    WAIT_FOR_TRIAL_CONCLUSION,

    /**
     * Represents the state at the end of a game within a match.
     */
    END_OF_A_GAME,

    /**
     * Represents the state at the end of a match.
     */
    END_OF_THE_MATCH;

    /**
     * Overrides the toString method to provide custom string representations for enum values.
     * @return A string representation of the enum value.
     */
    @Override
    public String toString() {
        switch (this) {
            case START_OF_THE_MATCH:
                return "SOM"; // Short form for START_OF_THE_MATCH
            case START_OF_A_GAME:
                return "SOG"; // Short form for START_OF_A_GAME
            case TEAMS_READY:
                return "TRY"; // Short form for TEAMS_READY
            case WAIT_FOR_TRIAL_CONCLUSION:
                return "WTC"; // Short form for WAIT_FOR_TRIAL_CONCLUSION
            case END_OF_A_GAME:
                return "EOG"; // Short form for END_OF_A_GAME
            case END_OF_THE_MATCH:
                return "EOM"; // Short form for END_OF_THE_MATCH
            default:
                throw new IllegalArgumentException(); // Throw exception for unknown state
        }
    }
}
