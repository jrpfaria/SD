package clientSide.entities;

/**
 * Interface for the referee cloning.
 */
public interface RefereeCloning {
    /**
     * Referee state getter.
     * @return the referee state
     */
    RefereeStates getRefereeState();

    /**
     * Set the referee state.
     * @param state the new referee state
     */
    void setRefereeState(RefereeStates state);
}
