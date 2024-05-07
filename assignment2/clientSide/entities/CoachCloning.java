package clientSide.entities;

/**
 * Interface for the coach cloning.
 */
public interface CoachCloning {
    /**
     * Coach state getter.
     * @return the coach state
     */
    CoachStates getCoachState();

    /**
     * Coach state setter.
     * @param state the new coach state
     */
    void setCoachState(CoachStates state);

    /**
     * Coach team getter.
     * @return the coach team number
     */
    int getCoachTeam();

    /**
     * Coach team setter.
     * @param team the new coach team
     */
    void setCoachTeam(int team);
}
