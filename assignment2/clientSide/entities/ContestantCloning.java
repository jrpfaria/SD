package clientSide.entities;

/**
 * Interface for the contestant cloning.
 */
public interface ContestantCloning {
    /**
     * Get the contestant state.
     */
    ContestantStates getContestantState();

    /**
     * Set the contestant state.
     */
    void setContestantState(ContestantStates state);

    /**
     * Get the contestant team.
     */
    int getContestantTeam();

    /**
     * Set the contestant team.
     */
    void setContestantTeam(int team);

    /**
     * Get the contestant number.
     */
    int getContestantNumber();

    /**
     * Set the contestant number.
     */
    void setContestantNumber(int number);

    /**
     * Get the contestant strength.
     */
    int getContestantStrength();

    /**
     * Set the contestant strength.
     */
    void setContestantStrength(int strength);
}
