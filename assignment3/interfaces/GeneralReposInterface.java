package interfaces;

import java.rmi.*;

public interface GeneralReposInterface extends Remote {

    public void initSimul(String logFileName, int[][] contestantStrength) throws RemoteException;

    /**
     * Set referee state.
     *
     * @param refereeState referee state
     */
    public void setRefereeState(int state) throws RemoteException;

    /**
     * Set coach state.
     *
     * @param team       coach team
     * @param coachState coach state
     */
    public void setCoachState(int team, int state) throws RemoteException;

    /**
     * Set contestant state.
     *
     * @param team            contestant team
     * @param number          contestant number
     * @param contestantState contestant state
     */
    public void setContestantState(int team, int number, int state) throws RemoteException;

    /**
     * Set contestant strength.
     *
     * @param team     contestant team
     * @param number   contestant number
     * @param strength contestant strength
     */
    public void setContestantStrength(int team, int number, int strength) throws RemoteException;

    /**
     * Add contestant to the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     */
    public void addContestant(int team, int number) throws RemoteException;

    /**
     * Remove contestant from the contestantPosition array.
     *
     * @param team   contestant team
     * @param number contestant number
     */
    public void removeContestant(int team, int number) throws RemoteException;

    /**
     * Set position of rope.
     *
     * @param position rope position
     */
    public void setRopePosition(int position) throws RemoteException;

    /**
     * Call trial.
     */
    public void callTrial() throws RemoteException;

    /**
     * Start game.
     */
    public void startGame() throws RemoteException;

    /**
     * End game.
     *
     * @param team     winning team
     * @param knockout true if game was won by knockout, false if game was won by
     *                 points
     */
    public void endGame(int team, boolean knockout) throws RemoteException;

    /**
     * End match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public void endMatch(int score1, int score2) throws RemoteException;

    //

    public void shutdown() throws RemoteException;
}
