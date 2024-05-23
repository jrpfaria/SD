package clientSide.entities;

import interfaces.*;
import serverSide.main.SimulPar;
import java.rmi.RemoteException;
import genclass.GenericIO;

/**
 * The Referee class represents the referee entity in the game simulation.
 * The referee oversees the match, announces new games, manages trials, and
 * declares winners.
 */
public class Referee extends Thread {
    /**
     * Stores the reference for the refereeSite shared area
     */
    private final RefereeSiteInterface refereeSiteStub;
    /**
     * Stores the reference for the playground shared area
     */
    private final PlaygroundInterface playgroundStub;
    /**
     * Stores the reference for the contestantsBench shared area
     */
    private final ContestantsBenchInterface contestantsBenchStub;
    /**
     * Stores the referee's state
     */
    private RefereeStates state;
    /**
     * Stores team 1's score
     */
    private int score1;

    /**
     * Stores team 2's score
     */
    private int score2;

    /**
     * Constructor for Referee class.
     * Initializes the referee with a referee site, playground, and contestants
     * bench.
     *
     * @param refereeSiteStub      The referee site stub.
     * @param playgroundStub       The playground stub.
     * @param contestantsBenchStub The contestants bench stub.
     */
    public Referee(RefereeSiteInterface refereeSiteStub, PlaygroundInterface playgroundStub,
            ContestantsBenchInterface contestantsBenchStub) {
        super("Referee");
        this.state = RefereeStates.START_OF_THE_MATCH;
        this.refereeSiteStub = refereeSiteStub;
        this.playgroundStub = playgroundStub;
        this.contestantsBenchStub = contestantsBenchStub;
    }

    /**
     * Sets the state of the referee.
     *
     * @param state The state to set for the referee.
     */
    public void setRefereeState(RefereeStates state) {
        this.state = state;
    }

    public RefereeStates getRefereeState(RefereeStates state) {
        return this.state;
    }

    /**
     * Overrides the run method of Thread class.
     * This method represents the behavior of the referee during the match.
     */
    @Override
    public void run() {
        int currentGame;
        int currentTrial;
        int ropePosition;

        // Iterate through each game
        for (currentGame = 1; currentGame <= SimulPar.NG; currentGame++) {
            announceNewGame(); // Announce the start of a new game
            // Iterate through each trial of the current game
            for (currentTrial = 1; currentTrial <= SimulPar.NT; currentTrial++) {
                callTrial(); // Call for a trial to start
                teams_ready(); // Signal that teams are ready
                startTrial(); // Start the trial on the playground
                wait_for_trial_conclusion(); // Wait for the trial to conclude
                ropePosition = assertTrialDecision(); // Determine the trial outcome
                // Break out of the trial loop if the strength difference exceeds the threshold,
                // making it a knockout
                if (Math.abs(ropePosition) >= SimulPar.KT)
                    break;
            }
            ropePosition = declareGameWinner(); // Declare the winner of the current game
            // Update scores based on the rope position
            if (ropePosition < 0)
                score1++; // Team 1 wins
            if (ropePosition > 0)
                score2++; // Team 2 wins
        }
        declareMatchWinner(score1, score2); // Declare the winner of the match
    }

    private void announceNewGame() {
        ReturnInt r = null;
        try {
            r = refereeSiteStub.announceNewGame();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on announceNewGame: " + e.getMessage());
            System.exit(1);
        }
        this.state = RefereeStates.values()[r.getIntStateVal()];
    }

    private void callTrial() {
        ReturnInt r = null;
        try {
            r = contestantsBenchStub.callTrial();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on callTrial: " + e.getMessage());
            System.exit(1);
        }
        this.state = RefereeStates.values()[r.getIntStateVal()];
    }

    private void teams_ready() {
        try {
            refereeSiteStub.teams_ready();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on teams_ready: " + e.getMessage());
            System.exit(1);
        }
    }

    private void startTrial() {
        try {
            playgroundStub.startTrial();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on startTrial: " + e.getMessage());
            System.exit(1);
        }
    }

    private void wait_for_trial_conclusion() {
        ReturnInt r = null;
        try {
            r = playgroundStub.wait_for_trial_conclusion();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on wait_for_trial_conclusion: " + e.getMessage());
            System.exit(1);
        }
        this.state = RefereeStates.values()[r.getIntStateVal()];
    }

    private int assertTrialDecision() {
        int r = 0;
        try {
            r = playgroundStub.assertTrialDecision();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on assertTrialDecision: " + e.getMessage());
            System.exit(1);
        }
        return r;
    }

    private int declareGameWinner() {
        int r = 0;
        try {
            r = playgroundStub.declareGameWinner();
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on declareGameWinner: " + e.getMessage());
            System.exit(1);
        }
        return r;
    }

    private void declareMatchWinner(int score1, int score2) {
        try {
            contestantsBenchStub.declareMatchWinner(score1, score2);
        } catch (RemoteException e) {
            GenericIO.writelnString("Referee remote exception on declareMatchWinner: " + e.getMessage());
            System.exit(1);
        }
    }

}
