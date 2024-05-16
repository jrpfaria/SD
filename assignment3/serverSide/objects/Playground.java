package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.*;
import genclass.GenericIO;
import interfaces.*;
import serverSide.main.*;

/**
 * Playground.
 * 
 * It is responsible to keep a continuously updated account of the position of
 * the rope and strength difference between players
 * and is implemented as an implicit monitor.
 * All public methods are executed in mutual exclusion
 * (except for do_your_best, which is composed of two separate blocks that are
 * executed in mutual exclusion)
 * There are four internal synchronization points: the referee waits for the
 * contestants to stop playing,
 * the coaches wait for the contestants to be ready and the trail do conclude,
 * and the contestants wait for the trial to start and to end
 */

public class Playground implements PlaygroundInterface {
    /**
     * Reference to the general repository.
     */
    private final GeneralReposInterface reposStub;
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
     * @param reposStub reference to the general repository stub
     */
    public Playground(GeneralReposInterface reposStub) {
        this.reposStub = reposStub;
    }

    // Referee

    /**
     * Operation startTrial
     * Called by the referee to notify contestants and general repository of the
     * beginning of the trial.
     * Update rope position on the general repository and reset position in the
     * playground.
     */
    @Override
    public synchronized void startTrial() throws RemoteException {
        try {
            reposStub.setRopePosition(ropePosition);
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on startTrial - setRopePosition: " + e.getMessage());
            System.exit(1);
        }

        strengthDifference = 0;
        endTrial = false;
        startTrial = true;
        notifyAll();
    }

    /**
     * Operation wait_for_trial_conclusion
     * The referee waits while contestants pull the rope.
     */
    @Override
    public synchronized void wait_for_trial_conclusion() throws RemoteException {
        try {
            reposStub.setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION.ordinal());
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Referee remote exception on wait_for_trial_conclusion - setRefereeState: " + e.getMessage());
            System.exit(1);
        }

        while (amDone < 2 * SimulPar.NP) {
            try {
                wait();
            } catch (InterruptedException ignored) {
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
    @Override
    public synchronized int assertTrialDecision() throws RemoteException {
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
    @Override
    public synchronized int declareGameWinner() throws RemoteException {
        boolean knockout = Math.abs(ropePosition) >= 4;
        int ropePosition = this.ropePosition;
        this.ropePosition = 0;
        try {
            reposStub.endGame(ropePosition, knockout);
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on declareGameWinner - endGame: " + e.getMessage());
            System.exit(1);
        }
        try {
            reposStub.setRopePosition(0);
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Referee remote exception on declareGameWinner - setRopePosition: " + e.getMessage());
            System.exit(1);
        }

        return ropePosition;
    }

    // Coach

    /**
     * Operation assemble_team
     * The coaches wait for the contestants to get in position to play
     */
    @Override
    public synchronized void assemble_team(int team) throws RemoteException {
        try {
            reposStub.setCoachState(team, CoachStates.ASSEMBLE_TEAM.ordinal());
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Coach " + (team + 1) + " remote exception on assemble_team - setCoachState: 1 " + e.getMessage());
            System.exit(1);
        }
        while (inPosition[team] < SimulPar.NP) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        try {
            reposStub.setCoachState(team, CoachStates.WATCH_TRIAL.ordinal());
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Coach " + (team + 1) + " remote exception on assemble_team - setCoachState 2: " + e.getMessage());
            System.exit(1);
        }

        inPosition[team] = 0;
    }

    /**
     * Operation watch_trial
     * The coaches wait for the trial to end
     */
    @Override
    public synchronized void watch_trial() throws RemoteException {
        while (!endTrial) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    // Contestant

    /**
     * Operation followCoachAdvice
     * The contestants notify their coach that they're in position
     */
    @Override
    public synchronized void followCoachAdvice() throws RemoteException {
    }

    /**
     * Operation stand_in_position
     * The contestants wait for the trial to start
     */
    @Override
    public synchronized void stand_in_position(int team, int number) throws RemoteException {
        try {
            reposStub.addContestant(team, number);
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1)
                    + " remote exception on stand_in_position - addContestant: " + e.getMessage());
            System.exit(1);
        }
        try {
            reposStub.setContestantState(team, number, ContestantStates.STAND_IN_POSITION.ordinal());
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1)
                    + " remote exception on stand_in_position - setContestantState: " + e.getMessage());
            System.exit(1);
        }
        inPosition[team]++;
        notifyAll();
        while (!startTrial) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Operation getReady
     * The contestants inform the general repository of their participation in the
     * trial
     */
    @Override
    public synchronized void getReady(int team, int number, int strength) throws RemoteException {
        try {
            reposStub.setContestantState(team, number, ContestantStates.DO_YOUR_BEST.ordinal());
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1)
                    + " remote exception on getReady - setContestantState: " + e.getMessage());
            System.exit(1);
        }

        if (team == 0)
            strengthDifference -= strength;
        else
            strengthDifference += strength;
    }

    /**
     * Operation amDone
     * The contestants inform the referee that they're done pulling the rope
     */
    @Override
    public synchronized void amDone() throws RemoteException {
        amDone++;
        notifyAll();
        while (!endTrial) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    //

    @Override
    public synchronized void shutdown() throws RemoteException {
        nEntities += 1;
        if (nEntities >= SimulPar.E)
            ServerGameOfRopePlayground.shutdown();
        notifyAll();
    }
}
