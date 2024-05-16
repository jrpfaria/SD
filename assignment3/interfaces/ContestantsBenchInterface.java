package interfaces;

import java.rmi.*;

public interface ContestantsBenchInterface extends Remote {
    // Referee

    /**
     * Operation callTrial
     * Called by the referee to notify the coaches that a trial was called.
     */
    public void callTrial() throws RemoteException;

    /**
     * Operation declareMatchWinner
     * Called by the referee to notify coaches and contestants that the match is
     * over.
     * Call the general repository to log the end of the match.
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public void declareMatchWinner(int score1, int score2) throws RemoteException;

    // Coach

    /**
     * Operation reviewNotes
     * Called by the coaches to obtain the strength of each of their players, after
     * they're all in the bench.
     *
     * @return roster of players
     */
    public int[] reviewNotes(int team) throws RemoteException;

    /**
     * Operation wait_for_referee_command
     * The coaches wait for either the match to end or for a trial to be called
     *
     * @return 0 if match is over, 1 if trial was called
     */
    public int wait_for_referee_command(int team) throws RemoteException;

    /**
     * Operation callContestants
     * Called by the coaches to notify their contestants that a trial was called and
     * which players will participate.
     *
     * @param roster numbers of the contestants that will participate
     */
    public void callContestants(int team, int[] roster) throws RemoteException;

    // Contestants

    /**
     * Operation seat_at_the_bench
     * Called by the contestants to update their strength, and wait for either the
     * match to end or a trial to be called.
     *
     * @return 0 if match is over, 1 if player has to stay in the bench, 2 if player
     *         will participate in the trial
     */
    public int seat_at_the_bench(int team, int number, int strength) throws RemoteException;

    /**
     * Operation seatDown
     * Called by the contestants to update their strength and remove their number
     * from the general repository.
     */
    public void seatDown(int team, int number) throws RemoteException;

    //

    public void shutdown() throws RemoteException;
}
