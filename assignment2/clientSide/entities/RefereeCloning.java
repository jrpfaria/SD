package clientSide.entities;

/**
 * Interface for the referee cloning.
 */
public interface RefereeCloning {
    /**
     * Get the referee state.
     */
    RefereeStates getRefereeState();

    /**
     * Set the referee state.
     */
    void setRefereeState(RefereeStates state);
}
