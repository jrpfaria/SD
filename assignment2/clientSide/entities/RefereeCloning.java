package clientSide.entities;

/**
 * Referee cloning.
 * <p>
 * It specifies his own attributes.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public interface RefereeCloning {
    /**
     * Referee state getter.
     *
     * @return the referee state
     */
    RefereeStates getRefereeState();

    /**
     * Set the referee state.
     *
     * @param state the new referee state
     */
    void setRefereeState(RefereeStates state);
}
