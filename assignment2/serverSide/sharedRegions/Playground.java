package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.*;
import commInfra.*;
import serverSide.entities.*;
import serverSide.main.*;

/**
 * Playground.
 * <p>
 * It is responsible to keep a continuously updated account of the position of the rope and strength difference between players
 * and is implemented as an implicit monitor.
 * All public methods are executed in mutual exclusion
 * (except for do_your_best, which is composed of two separate blocks that are executed in mutual exclusion)
 * There are four internal synchronization points: the referee waits for the contestants to stop playing,
 * the coaches wait for the contestants to be ready and the trail do conclude,
 * and the contestants wait for the trial to start and to end
 */

public class Playground {
    /**
     * Reference to the general repository.
     */
    private final GeneralReposStub reposStub;
    /**
     * Number of contestants of each team that are in position to pull the rope.
     */
    private final int[] inPosition = new int[2];
    /**
     * Flag the indicated the start of a trial.
     */
    private boolean startTrial;
    /**
     * Flag the indicated the end of a trial.
     */
    private boolean endTrial;
    /**
     * Number of contestants that are done pulling the rope.
     */
    private int amDone = 0;
    /**
     * Position of the rope.
     */
    private int ropePosition;
    /**
     * Difference in strength between the two teams.
     */
    private int strengthDifference;

    private int nEntities = 0;

    /**
     * Playground instantiation.
     *
     * @param repos reference to the general repository
     */
    public Playground(GeneralReposStub reposStub) {
        this.reposStub = reposStub;
    }

    //Referee

    /**
     * Operation startTrial
     * Called by the referee to notify contestants and general repository of the beginning of the trial.
     * Update rope position on the general repository and reset position in the playground.
     */
    public synchronized void startTrial() {
        reposStub.setRopePosition(ropePosition);
        strengthDifference = 0;
        endTrial = false;
        startTrial = true;
        notifyAll();
    }

    /**
     * Operation wait_for_trial_conclusion
     * The referee waits while contestants pull the rope.
     */
    public synchronized void wait_for_trial_conclusion() {
        PlaygroundClientProxy t = (PlaygroundClientProxy) Thread.currentThread();
        t.setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        reposStub.setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);

        while (amDone < 2 * SimulPar.NP) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        amDone = 0;
    }

    /**
     * Operation assertTrialDecision
     * The referee notifies the contestants and coaches of the end of the trial.
     * Update rope position based on the strength of the teams.
     *
     * @return rope position
     */
    public synchronized int assertTrialDecision() {
        startTrial = false;
        endTrial = true;
        notifyAll();
        ropePosition += strengthDifference;
        return ropePosition;
    }

    /**
     * Operation declareGameWinner
     * The referee calls the general repository to log the end of the game
     *
     * @return rope position
     */
    public synchronized int declareGameWinner() {
        PlaygroundClientProxy t = (PlaygroundClientProxy) Thread.currentThread();
        boolean knockout = Math.abs(ropePosition) >= 4;
        int ropePosition = this.ropePosition;
        this.ropePosition = 0;
        t.setRefereeState(RefereeStates.END_OF_A_GAME);
        reposStub.endGame(ropePosition, knockout);
        reposStub.setRopePosition(0);
        return ropePosition;
    }

    //Coach

    /**
     * Operation assemble_team
     * The coaches wait for the contestants to get in position to play
     *
     * @param team team of the coach
     */
    public synchronized void assemble_team() {
        PlaygroundClientProxy t = (PlaygroundClientProxy) Thread.currentThread();
        int team = t.getCoachTeam();
        t.setCoachState(CoachStates.ASSEMBLE_TEAM);
        reposStub.setCoachState(team, CoachStates.ASSEMBLE_TEAM);
        while (inPosition[team] < SimulPar.NP) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        t.setCoachState(CoachStates.WATCH_TRIAL);
        reposStub.setCoachState(team, CoachStates.WATCH_TRIAL);
        inPosition[team] = 0;
    }

    /**
     * Operation watch_trial
     * The coaches wait for the trial to end
     *
     * @param team team of the coach
     */
    public synchronized void watch_trial() {
        while (!endTrial) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    //Contestant

    /**
     * Operation followCoachAdvice
     * The contestants notify their coach that they're in position
     *
     * @param team team of the contestant
     */
    public synchronized void followCoachAdvice() {
        PlaygroundClientProxy t = (PlaygroundClientProxy) Thread.currentThread();
        int team = t.getContestantTeam();
        inPosition[team]++;
        notifyAll();
    }

    /**
     * Operation stand_in_position
     * The contestants wait for the trial to start
     *
     * @param team   team of the contestant
     * @param number number of the contestant
     */
    public synchronized void stand_in_position() {
        PlaygroundClientProxy t = (PlaygroundClientProxy) Thread.currentThread();
        int team = t.getContestantTeam();
        int number = t.getContestantNumber();
        reposStub.addContestant(team, number);
        t.setContestantState(ContestantStates.STAND_IN_POSITION);
        reposStub.setContestantState(team, number, ContestantStates.STAND_IN_POSITION);
        while (!startTrial) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Operation getReady
     * The contestants inform the general repository of their participation in the trial
     *
     * @param team   team of the contestant
     * @param number number of the contestant
     */
    public synchronized void getReady() {
        PlaygroundClientProxy t = (PlaygroundClientProxy) Thread.currentThread();
        int team = t.getContestantTeam();
        int number = t.getContestantNumber();
        int strength = t.getContestantStrength();
        t.setContestantState(ContestantStates.DO_YOUR_BEST);
        reposStub.setContestantState(team, number, ContestantStates.DO_YOUR_BEST);
        if (team == 0) strengthDifference -= strength;
        else strengthDifference += strength;
    }

    /**
     * Operation amDone
     * The contestants inform the referee that they're done pulling the rope
     */
    public synchronized void amDone() {
        amDone++;
        notifyAll();
        while (!endTrial) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    //

    public synchronized void shutdown() {
        nEntities += 1;
        if (nEntities >= SimulPar.E) ServerGameOfRopePlayground.waitConnection = false;
        notifyAll();
    }
}
