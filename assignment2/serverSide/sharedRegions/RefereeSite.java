package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.*;
import serverSide.entities.*;
import serverSide.main.*;

/**
 * Referee site.
 * <p>
 * It is responsible to enable communication between the coaches and the referee at the beginning of a trial, and to allow the referee to announce a new game.
 * All public methods are executed in mutual exclusion.
 * There is one internal synchronization points, where the referee waits for the teams to be ready.
 */
public class RefereeSite {
    /**
     * Reference to the general repository.
     */
    private final GeneralReposStub reposStub;
    /**
     * Number of coaches whose teams are ready.
     */
    private int ready;

    private int nEntities = 0;

    /**
     * Referee site instantiation.
     *
     * @param reposStub reference to the general repository stub
     */
    public RefereeSite(GeneralReposStub reposStub) {
        this.reposStub = reposStub;
    }

    //Referee

    /**
     * Operation announceNewGame.
     * <p>
     * It is called by the referee when he declares the start of a game.
     */
    public synchronized void announceNewGame() {
        ((RefereeSiteClientProxy) Thread.currentThread()).setRefereeState(RefereeStates.START_OF_A_GAME);
        reposStub.setRefereeState(RefereeStates.START_OF_A_GAME);
        reposStub.startGame();
    }

    /**
     * Operation teams_ready.
     * <p>
     * It is called by the referee while he waits for the teams to be ready.
     */
    public synchronized void teams_ready() {
        while (ready < 2) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        ready = 0;
    }

    //Coach

    /**
     * Operation informReferee.
     * <p>
     * It is called by a coach to indicate to the referee that their team is ready.
     */
    public synchronized void informReferee() {
        ready++;
        notifyAll();
    }

    //

    public synchronized void shutdown() {
        nEntities += 1;
        if (nEntities >= 2) ServerGameOfRopeRefereeSite.waitConnection = false;
        notifyAll();
    }
}
