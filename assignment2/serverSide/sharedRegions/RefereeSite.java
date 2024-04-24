package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *    Referee site.
 *
 *    It is responsible to enable communication between the coaches and the referee at the beginning of a trial, and to allow the referee to announce a new game.
 *    All public methods are executed in mutual exclusion.
 *    There is one internal synchronization points, where the referee waits for the teams to be ready.
 */
public class RefereeSite {
    /**
     *   Reference to the general repository.
     */
    private final GeneralReposStub reposStub;
    /**
     *   Number of coaches whose teams are ready.
     */
    private int ready;
    
    /**
     *  Referee site instantiation.
     *
     *    @param repos reference to the general repository
    */
    public RefereeSite(GeneralReposStub reposStub) {
        this.reposStub = reposStub;
    }

    //Referee

    /**
     *  Operation announceNewGame.
     *
     *  It is called by the referee when he declares the start of a game.
     */
    public synchronized void announceNewGame() {
        ((RefereeSiteClientProxy)Thread.currentThread()).setRefereeState(RefereeStates.START_OF_A_GAME);
    }

    /**
     *  Operation teams_ready.
     *
     *  It is called by the referee while he waits for the teams to be ready.
     */
    public synchronized void teams_ready() {
        while (ready<2) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        ready = 0;
    }

    //Coach

    /**
     *  Operation informReferee.
     *
     *  It is called by a coach to indicate to the referee that their team is ready.
     */
    public synchronized void informReferee() {
        ready++;
        notifyAll();
    }
}
