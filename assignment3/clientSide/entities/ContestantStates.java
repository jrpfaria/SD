package clientSide.entities;

/**
 * The ContestantStates enum represents the possible states of a contestant in
 * the game simulation.
 * Contestants can be in one of the following states: SEAT_AT_THE_BENCH,
 * STAND_IN_POSITION, DO_YOUR_BEST.
 */
public enum ContestantStates {
    /**
     * Represents the state where the contestant is seated at the bench, waiting for
     * instructions.
     */
    SEAT_AT_THE_BENCH,

    /**
     * Represents the state where the contestant is standing in position on the
     * playground.
     */
    STAND_IN_POSITION,

    /**
     * Represents the state where the contestant is performing their best in the
     * trial.
     */
    DO_YOUR_BEST;

    /**
     * Overrides the toString method to provide custom string representations for
     * enum values.
     *
     * @return A string representation of the enum value.
     */
    @Override
    public String toString() {
        switch (this) {
            case SEAT_AT_THE_BENCH:
                return "SAB"; // Short form for SEAT_AT_THE_BENCH
            case STAND_IN_POSITION:
                return "SIP"; // Short form for STAND_IN_POSITION
            case DO_YOUR_BEST:
                return "DYB"; // Short form for DO_YOUR_BEST
            default:
                throw new IllegalArgumentException(); // Throw exception for unknown state
        }
    }
}
