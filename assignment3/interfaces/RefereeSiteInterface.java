package interfaces;

import java.rmi.*;

public interface RefereeSiteInterface extends Remote {

    // Referee

    /**
     * Operation announceNewGame.
     * 
     * It is called by the referee when he declares the start of a game.
     * @throws RemoteException
     */
    public void announceNewGame() throws RemoteException;

    /**
     * Operation teams_ready.
     * 
     * It is called by the referee while he waits for the teams to be ready.
     * @throws RemoteException
     */
    public void teams_ready() throws RemoteException;

    // Coach

    /**
     * Operation informReferee.
     * 
     * It is called by a coach to indicate to the referee that their team is ready.
     * @throws RemoteException
     */
    public void informReferee() throws RemoteException;

    //

    /**
     * Operation shutdown.
     * @throws RemoteException
     */
    public void shutdown() throws RemoteException;

}
