package interfaces;

import java.rmi.*;

public interface GeneralReposInterface extends Remote {

    /**
     * Initialize the simulation.
     * 
     * @param logFileName       name of the log file
     * @param contestantStrengh strength of each contestant
     * @throws RemoteException if either the invocation of the remote method,
     *                        or the communication with the registry service fails
     */
    public void initSimul(String logFileName, int[][] contestantStrength) throws RemoteException;

    /**
     * Set referee state.
     *
     * @param refereeState referee state
     * @throws RemoteException if either the invocation of the remote method,
     *                       or the communication with the registry service fails
     */
    public void setRefereeState(int state) throws RemoteException;

    /**
     * Set coach state.
     *
     * @param team       coach team
     * @param coachState coach state
     * @throws RemoteException if either the invocation of the remote method,
     *                      or the communication with the registry service fails
     */
    public void setCoachState(int team, int state) throws RemoteException;

    /**
     * Set contestant state.
     *
     * @param team            contestant team
     * @param number          contestant number
     * @param contestantState contestant state
     * @throws RemoteException if either the invocation of the remote method,
     *                      or the communication with the registry service fails
     */
    public void setContestantState(int team, int number, int state) throws RemoteException;

    /**
     * Set contestant strength.
     *
     * @param team     contestant team
     * @param number   contestant number
     * @param strength contestant strength
     * @throws RemoteException if either the invocation of the remote method,
     *                     or the communication with the registry service fails
     */
    public void setContestantStrength(int team, int number, int strength) throws RemoteException;

    /**
     * Add contestant to the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     * @throws RemoteException if either the invocation of the remote method,
     *                     or the communication with the registry service fails
     */
    public void addContestant(int team, int number) throws RemoteException;

    /**
     * Remove contestant from the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     * @throws RemoteException if either the invocation of the remote method,
     *                    or the communication with the registry service fails
     */
    public void removeContestant(int team, int number) throws RemoteException;

    /**
     * Set position of rope.
     *
     * @param position rope position
     * @throws RemoteException if either the invocation of the remote method,
     *                    or the communication with the registry service fails
     */
    public void setRopePosition(int position) throws RemoteException;

    /**
     * Call trial.
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
     */
    public void callTrial() throws RemoteException;

    /**
     * Start game.
     * @throws RemoteException if either the invocation of the remote method,
     *                  or the communication with the registry service fails
     */
    public void startGame() throws RemoteException;

    /**
     * End game.
     *
     * @param team     winning team
     * @param knockout true if game was won by knockout, false if game was won by
     *                 points
     * @throws RemoteException if either the invocation of the remote method,
     *                    or the communication with the registry service fails
     */
    public void endGame(int team, boolean knockout) throws RemoteException;

    /**
     * End match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
     */
    public void endMatch(int score1, int score2) throws RemoteException;

    //

    /**
     * Operation shutdown.
<<<<<<< HEAD
     * @throws RemoteException if either the invocation of the remote method,
     *                   or the communication with the registry service fails
=======
     * 
     * @throws RemoteException
>>>>>>> 877bf4f20955498c99fe61a6ead57cd9b879df37
     */
    public void shutdown() throws RemoteException;
}
