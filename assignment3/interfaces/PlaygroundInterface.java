package interfaces;

import java.rmi.*;

public interface PlaygroundInterface extends Remote {

    // Referee

    /**
     * Operation startTrial
     * Called by the referee to notify contestants and general repository of the
     * beginning of the trial.
     * Update rope position on the general repository and reset position in the
     * playground.
     * 
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public void startTrial() throws RemoteException;

    /**
     * Operation wait_for_trial_conclusion
     * The referee waits while contestants pull the rope.
     * 
     * @return ReturnInt int and state
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public ReturnInt wait_for_trial_conclusion() throws RemoteException;

    /**
     * Operation assertTrialDecision
     * The referee notifies the contestants and coaches of the end of the trial.
     * Update rope position based on the strength of the teams.
     *
     * @return rope position
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public int assertTrialDecision() throws RemoteException;

    /**
     * Operation declareGameWinner
     * The referee calls the general repository to log the end of the game
     *
     * @return rope position
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public int declareGameWinner() throws RemoteException;

    // Coach

    /**
     * Operation assemble_team
     * The coaches wait for the contestants to get in position to play
     * 
     * @param team coach's team
     * 
     * @return int and state
     * 
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public ReturnInt assemble_team(int team) throws RemoteException;

    /**
     * Operation watch_trial
     * The coaches wait for the trial to end
     * 
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public void watch_trial() throws RemoteException;

    // Contestant

    /**
     * Operation followCoachAdvice
     * The contestants notify their coach that they're in position
     * 
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public void followCoachAdvice() throws RemoteException;

    /**
     * Operation stand_in_position
     * The contestants wait for the trial to start
     * 
     * @param team   contestant's team
     * @param number contestant's number
     * @return ReturnInt int and state
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public ReturnInt stand_in_position(int team, int number) throws RemoteException;

    /**
     * Operation getReady
     * The contestants inform the general repository of their participation in the
     * trial
     * 
     * @param team     contestant's team
     * @param number   contestant's number
     * @param strength contestant's strength
     * @return int and state
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public ReturnInt getReady(int team, int number, int strength) throws RemoteException;

    /**
     * Operation amDone
     * The contestants inform the referee that they're done pulling the rope
     * 
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public void amDone() throws RemoteException;

    //
    /**
     * Operation shutdown
     * 
     * @throws RemoteException if either the invocation of the remote method,
     *                         or the communication with the registry service fails
     */
    public void shutdown() throws RemoteException;
}
