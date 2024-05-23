package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.*;
import genclass.GenericIO;
import interfaces.*;
import serverSide.main.*;

/**
 * Referee site.
 * 
 * It is responsible to enable communication between the coaches and the referee
 * at the beginning of a trial, and to allow the referee to announce a new game.
 * All public methods are executed in mutual exclusion.
 * There is one internal synchronization points, where the referee waits for the
 * teams to be ready.
 */
public class RefereeSite implements RefereeSiteInterface {
    /**
     * Reference to the general repository.
     */
    private final GeneralReposInterface reposStub;
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
    public RefereeSite(GeneralReposInterface reposStub) {
        this.reposStub = reposStub;
    }

    // Referee

    /**
     * Operation announceNewGame.
     * 
     * It is called by the referee when he declares the start of a game.
     */
    @Override
    public synchronized ReturnInt announceNewGame() throws RemoteException {
        try {
            reposStub.setRefereeState(RefereeStates.START_OF_A_GAME.ordinal());
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on callTrial - setRefereeState: " + e.getMessage());
            System.exit(1);
        }
        try {
            reposStub.startGame();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on callTrial - setRefereeState: " + e.getMessage());
            System.exit(1);
        }
        return new ReturnInt(0, RefereeStates.START_OF_A_GAME.ordinal());
    }

    /**
     * Operation teams_ready.
     * 
     * It is called by the referee while he waits for the teams to be ready.
     */
    @Override
    public synchronized void teams_ready() throws RemoteException {
        while (ready < 2) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        ready = 0;
    }

    // Coach

    /**
     * Operation informReferee.
     * 
     * It is called by a coach to indicate to the referee that their team is ready.
     */
    @Override
    public synchronized void informReferee() throws RemoteException {
        ready++;
        notifyAll();
    }

    //

    @Override
    public synchronized void shutdown() throws RemoteException {
        nEntities += 1;
        if (nEntities >= 2)
            ServerGameOfRopeRefereeSite.shutdown();
        notifyAll();
    }
}
