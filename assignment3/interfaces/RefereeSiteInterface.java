package interfaces;

import java.rmi.*;

public interface RefereeSiteInterface extends Remote {

    // Referee

    /**
     * Operation announceNewGame.
     * 
     * It is called by the referee when he declares the start of a game.
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
     */
    public ReturnInt announceNewGame() throws RemoteException;

    /**
     * Operation teams_ready.
     * 
     * It is called by the referee while he waits for the teams to be ready.
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
     */
    public void teams_ready() throws RemoteException;

    // Coach

    /**
     * Operation informReferee.
     * 
     * It is called by a coach to indicate to the referee that their team is ready.
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
     */
    public void informReferee() throws RemoteException;

    //

    /**
     * Operation shutdown.
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
     */
    public void shutdown() throws RemoteException;

}
