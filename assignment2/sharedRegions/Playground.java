package assignment2.sharedRegions;

import assignment2.main.SimulPar;
import assignment2.entities.*;

/**
 *    Playground.
 *
 *    It is responsible to keep a continuously updated account of the position of the rope and strength difference between players
 *    and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion 
 *    (except for do_your_best, which is composed of two separate blocks that are executed in mutual exclusion)
 *    There are four internal synchronization points: the referee waits for the contestants to stop playing,
 *    the coaches wait for the contestants to be ready and the trail do conclude,
 *    and the contestants wait for the trial to start and to end
 */

public class Playground {
    /**
     *   Reference to the general repository.
     */
    private final GeneralRepos repos;
    /**
     *   Flag the indicated the start of a trial.
     */
    private boolean startTrial;
    /**
     *   Flag the indicated the end of a trial.
     */
    private boolean endTrial;
    /**
     *  Number of contestants that are done pulling the rope.
     */
    private int amDone = 0;
    /**
     *   Number of contestants of each team that are in position to pull the rope.
     */
    private final int[] inPosition;
    /**
     * Position of the rope.
     */
    private int ropePosition;
    /**
     *   Difference in strength between the two teams.
     */
    private int strengthDifference;
    /**
     *  Playground instantiation.
     *
     *    @param repos reference to the general repository
     */
    public Playground(GeneralRepos repos) {
        this.repos = repos;
        this.inPosition = new int[2];
    }

    //Referee

    /** 
     *  Operation startTrial
     *  Called by the referee to notify contestants and general repository of the beginning of the trial.
     *  Update rope position on the general repository and reset position in the playground.
     */
    public synchronized void startTrial() {
        repos.setRopePosition(ropePosition);
        repos.startTrial();
        strengthDifference = 0;
        endTrial = false;
        startTrial = true;
        notifyAll();
    }
    /**
     *  Operation wait_for_trial_conclusion
     *  The referee waits while contestants pull the rope.
     */
    public synchronized void wait_for_trial_conclusion() {
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        repos.setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        while (amDone<2*SimulPar.NP) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        amDone = 0;
    }
    /** 
     *  Operation assertTrialDecision
     *  The referee notifies the contestants and coaches of the end of the trial.
     *  Update rope position based on the strength of the teams.
     * 
     *  @return rope position
     */
    public synchronized int assertTrialDecision() {
        startTrial = false;
        endTrial = true;
        notifyAll();
        ropePosition += strengthDifference;
        return ropePosition;
    }
    /**
     *  Operation declareGameWinner
     *  The referee calls the general repository to log the end of the game
     * 
     *  @return rope position
     */
    public synchronized int declareGameWinner() {
        boolean knockout = Math.abs(strengthDifference) >= 4;
        int ropePosition = this.ropePosition;
        this.ropePosition = 0;
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.END_OF_A_GAME);
        repos.setRefereeState(RefereeStates.END_OF_A_GAME);
        repos.endGame(ropePosition, knockout);
        repos.setRopePosition(0);
        return ropePosition;
    }

    //Coach

    /**
     *  Operation assemble_team
     *  The coaches wait for the contestants to get in position to play
     * 
     * @param team team of the coach
     */
    public synchronized void assemble_team(int team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.ASSEMBLE_TEAM);
        repos.setCoachState(team, CoachStates.ASSEMBLE_TEAM);
        while (inPosition[team]<SimulPar.NP) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        inPosition[team] = 0;
    }
    /**
     *  Operation watch_trial
     *  The coaches wait for the trial to end
     * 
     * @param team team of the coach
     */
    public synchronized void watch_trial(int team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.WATCH_TRIAL);
        repos.setCoachState(team, CoachStates.WATCH_TRIAL);
        while (!endTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    //Contestant

    /**
     *  Operation followCoachAdvice
     *  The contestants notify their coach that they're in position
     * 
     * @param team team of the contestant
     */
    public synchronized void followCoachAdvice(int team) {
        inPosition[team]++;
        notifyAll();
    }
    /**
     *  Operation stand_in_position
     *  The contestants wait for the trial to start
     * 
     * @param team team of the contestant
     * @param number number of the contestant
     */
    public synchronized void stand_in_position(int team, int number) {
        ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.STAND_IN_POSITION);
        repos.setContestantState(team, number, ContestantStates.STAND_IN_POSITION);
        repos.addContestant(team, number);
        while (!startTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    /**
     *  Operation getReady
     *  The contestants inform the general repository of their participation in the trial
     * 
     *  @param team team of the contestant
     *  @param number number of the contestant
     */
    public synchronized void getReady(int team, int number) {

    }
    /**
     *  Operation do_your_best
     *  The contestants pull the rope, sleep for a random amount of time, inform the referee that they're done playing, and wait for the trial to end
     *
     *  @param team team of the contestant
     *  @param number number of the contestant
     *  @param strength strength of the contestant
     * 
     */
    public void do_your_best(int team, int number, int strength) {
        synchronized (this) {
            ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.DO_YOUR_BEST);
            repos.setContestantState(team, number, ContestantStates.DO_YOUR_BEST);
            pullTheRope(team, strength);
        }
        try {Thread.sleep((long)((SimulPar.MAXT-SimulPar.MINT+1)*Math.random()+SimulPar.MINT));}
        catch (InterruptedException e) {}
        synchronized (this) {
            amDone();
            while (!endTrial) {
                try {wait();}
                catch (InterruptedException e) {}
            }
        }
    }
    /**
     *  Operation pullTheRope
     *  The contestants add or subtract their strength to the strengthDifference variable
     * 
     *  @param team team of the contestant
     *  @param strength strength of the contestant
     */
    public synchronized void pullTheRope(int team, int strength) {
        if (team==0) strengthDifference -= strength;
        else strengthDifference += strength;
    }
    /**
     *  Operation amDone
     *  The contestants inform the referee that they're done pulling the rope
     * 
     */
    public synchronized void amDone() {
        amDone++;
        notifyAll();
    }
}
