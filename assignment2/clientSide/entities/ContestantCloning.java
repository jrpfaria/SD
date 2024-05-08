package clientSide.entities;

/**
 * Contestant cloning.
 * <p>
 * It specifies his own attributes.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public interface ContestantCloning {
    /**
     * Contestant state getter.
     *
     * @return the contestant state
     */
    ContestantStates getContestantState();

    /**
     * Contestant state setter.
     *
     * @param state the new contestant state
     */
    void setContestantState(ContestantStates state);

    /**
     * Contestant team getter.
     *
     * @return the contestant team number
     */
    int getContestantTeam();

    /**
     * Contestant team setter.
     *
     * @param team the new contestant team
     */
    void setContestantTeam(int team);

    /**
     * Contestant number getter.
     *
     * @return the contestant number
     */
    int getContestantNumber();

    /**
     * Contestant number setter.
     *
     * @param number the new contestant number
     */
    void setContestantNumber(int number);

    /**
     * Contestant strength getter.
     *
     * @return the contestant strength
     */
    int getContestantStrength();

    /**
     * Contestant strength setter.
     *
     * @param strength the new contestant strength
     */
    void setContestantStrength(int strength);
}
