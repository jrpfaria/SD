package clientSide.entities;

/**
 * Coach cloning.
 * <p>
 * It specifies his own attributes.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public interface CoachCloning {
    /**
     * Coach state getter.
     *
     * @return the coach state
     */
    CoachStates getCoachState();

    /**
     * Coach state setter.
     *
     * @param state the new coach state
     */
    void setCoachState(CoachStates state);

    /**
     * Coach team getter.
     *
     * @return the coach team number
     */
    int getCoachTeam();

    /**
     * Coach team setter.
     *
     * @param team the new coach team
     */
    void setCoachTeam(int team);
}
