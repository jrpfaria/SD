package assignment1.entities;
/**
 * The CoachStates enum represents the possible states of a coach in the game simulation.
 * Coaches can be in one of the following states: WAIT_FOR_REFEREE_COMMAND, ASSEMBLE_TEAM, WATCH_TRIAL.
 */
public enum CoachStates {
    /**
     * Represents the state where the coach is waiting for a command from the referee while on the bench.
     */
    WAIT_FOR_REFEREE_COMMAND,

    /**
     * Represents the state where the coach is assembling their team.
     */
    ASSEMBLE_TEAM,

    /**
     * Represents the state where the coach is watching the trial on the playground.
     */
    WATCH_TRIAL;

    /**
     * Overrides the toString method to provide custom string representations for enum values.
     * @return A string representation of the enum value.
     */
    @Override
    public String toString() {
        switch(this) {
            case WAIT_FOR_REFEREE_COMMAND:
                return "WFRC"; // Short form for WAIT_FOR_REFEREE_COMMAND
            case ASSEMBLE_TEAM:
                return "ASTM"; // Short form for ASSEMBLE_TEAM
            case WATCH_TRIAL:
                return "WATL"; // Short form for WATCH_TRIAL
            default:
                throw new IllegalArgumentException(); // Throw exception for unknown state
        }
    }
}
