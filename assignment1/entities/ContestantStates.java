package assignment1.entities;

public enum ContestantStates {
    SEAT_AT_THE_BENCH,
    STAND_IN_POSITION,
    DO_YOUR_BEST;

    @Override
    public String toString() {
        switch (this) {
            case SEAT_AT_THE_BENCH:
                return "SAB";
            case STAND_IN_POSITION:
                return "SIP";
            case DO_YOUR_BEST:
                return "DYB";
            default:
                throw new IllegalArgumentException();
        }
    }
}
