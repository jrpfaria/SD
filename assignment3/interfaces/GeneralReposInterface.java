package interfaces;

import java.rmi.*;

public interface GeneralReposInterface extends Remote {

    /**
     * Initialize the simulation.
     * 
     * @param logFileName       name of the log file
     * @param contestantStrengh strength of each contestant
     * @throws RemoteException
     */
    public void initSimul(String logFileName, int[][] contestantStrength) throws RemoteException;

    /**
     * Set referee state.
     *
     * @param refereeState referee state
     * @throws RemoteException
     */
    public void setRefereeState(int state) throws RemoteException;

    /**
     * Set coach state.
     *
     * @param team       coach team
     * @param coachState coach state
     * @throws RemoteException
     */
    public void setCoachState(int team, int state) throws RemoteException;

    /**
     * Set contestant state.
     *
     * @param team            contestant team
     * @param number          contestant number
     * @param contestantState contestant state
     * @throws RemoteException
     */
    public void setContestantState(int team, int number, int state) throws RemoteException;

    /**
     * Set contestant strength.
     *
     * @param team     contestant team
     * @param number   contestant number
     * @param strength contestant strength
     * @throws RemoteException
     */
    public void setContestantStrength(int team, int number, int strength) throws RemoteException;

    /**
     * Add contestant to the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     * @throws RemoteException
     */
    public void addContestant(int team, int number) throws RemoteException;

    /**
     * Remove contestant from the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     * @throws RemoteException
     */
    public void removeContestant(int team, int number) throws RemoteException;

    /**
     * Set position of rope.
     *
     * @param position rope position
     * @throws RemoteException
     */
    public void setRopePosition(int position) throws RemoteException;

    /**
     * Call trial.
     * 
     * @throws RemoteException
     */
    public void callTrial() throws RemoteException;

    /**
     * Start game.
     * 
     * @throws RemoteException
     */
    public void startGame() throws RemoteException;

    /**
     * End game.
     *
     * @param team     winning team
     * @param knockout true if game was won by knockout, false if game was won by
     *                 points
     * @throws RemoteException
     */
    public void endGame(int team, boolean knockout) throws RemoteException;

    /**
     * End match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     * @throws RemoteException
     */
    public void endMatch(int score1, int score2) throws RemoteException;

    //

    /**
     * Operation shutdown.
     * 
     * @throws RemoteException
     */
    public void shutdown() throws RemoteException;
}
