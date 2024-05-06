package clientSide.entities;

/**
 * Interface for the coach cloning.
 */
public interface CoachCloning {
    /**
     * Get the coach state.
     */
    CoachStates getCoachState();

    /**
     * Set the coach state.
     */
    void setCoachState(CoachStates state);

    /**
     * Get the coach team.
     */
    int getCoachTeam();

    /**
     * Set the coach team.
     */
    void setCoachTeam(int team);
}
