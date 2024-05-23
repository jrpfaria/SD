package interfaces;

import java.rmi.*;

public interface ContestantsBenchInterface extends Remote {
    // Referee

    /**
     * Operation callTrial
     * Called by the referee to notify the coaches that a trial was called.
     * @throws RemoteException if either the invocation of the remote method,
     *                       or the communication with the registry service fails
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
     * @throws RemoteException if either the invocation of the remote method,
     *                       or the communication with the registry service fails
     */
    public void declareMatchWinner(int score1, int score2) throws RemoteException;

    // Coach

    /**
     * Operation reviewNotes
     * Called by the coaches to obtain the strength of each of their players, after
     * they're all in the bench.
     * @param team coach's team
     * @return roster of players
     * @throws RemoteException if either the invocation of the remote method,
     *                      or the communication with the registry service fails
     */
    public int[] reviewNotes(int team) throws RemoteException;

    /**
     * Operation wait_for_referee_command
     * The coaches wait for either the match to end or for a trial to be called
     * @param team coach's team
     * @return 0 if match is over, 1 if trial was called
     * @throws RemoteException if either the invocation of the remote method,
     *                     or the communication with the registry service fails
     */
    public int wait_for_referee_command(int team) throws RemoteException;

    /**
     * Operation callContestants
     * Called by the coaches to notify their contestants that a trial was called and
     * which players will participate.
     *
     * @param team   coach's team
     * @param roster numbers of the contestants that will participate
     * @throws RemoteException if either the invocation of the remote method,
     *                      or the communication with the registry service fails
     */
    public void callContestants(int team, int[] roster) throws RemoteException;

    // Contestants

    /**
     * Operation seat_at_the_bench
     * Called by the contestants to update their strength, and wait for either the
     * match to end or a trial to be called.
     * @param team     contestant team
     * @param number   contestant number
     * @param strength contestant strength
     * @return 0 if match is over, 1 if player has to stay in the bench, 2 if player
     *         will participate in the trial
     * @throws RemoteException if either the invocation of the remote method,
     *                      or the communication with the registry service fails
     */
    public int seat_at_the_bench(int team, int number, int strength) throws RemoteException;

    /**
     * Operation seatDown
     * Called by the contestants to update their strength and remove their number
     * from the general repository.
     * @param team   contestant team
     * @param number contestant number
     * @throws RemoteException if either the invocation of the remote method,
     *                     or the communication with the registry service fails
     */
    public void seatDown(int team, int number) throws RemoteException;

    //
    /**
     * Operation shutdown.
     * @throws RemoteException if either the invocation of the remote method,
     *                      or the communication with the registry service fails
     */
    public void shutdown() throws RemoteException;
}
