/**
 * The Referee class represents the referee entity in the game simulation.
 * The referee oversees the match, announces new games, manages trials, and declares winners.
 */
package assignment1.entities;

import assignment1.main.SimulPar;
import assignment1.sharedRegions.*;

public class Referee extends Thread {

    private RefereeStates state;
    private RefereeSite refereeSite;
    private Playground playground;
    private ContestantsBench contestantsBench;
    private int score1;
    private int score2;

    /**
     * Constructor for Referee class.
     * Initializes the referee with a referee site, playground, and contestants bench.
     * @param refereeSite The referee site shared memory area.
     * @param playground The playground shared memory area.
     * @param contestantsBench The contestants bench shared memory area.
     */
    public Referee(RefereeSite refereeSite, Playground playground, ContestantsBench contestantsBench) {
        super("Referee");
        this.state = RefereeStates.START_OF_THE_MATCH;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
    }

    /**
     * Sets the state of the referee.
     * @param state The state to set for the referee.
     */
    public void setRefereeState(RefereeStates state){
        this.state = state;
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
        int strengthDifference;

        // Iterate through each game
        for (currentGame = 1; currentGame <= SimulPar.NG; currentGame++) {
            refereeSite.announceNewGame(); // Announce the start of a new game
            // Iterate through each trial of the current game
            for (currentTrial = 1; currentTrial <= SimulPar.NT; currentTrial++) {
                contestantsBench.callTrial(); // Call for a trial to start
                refereeSite.teams_ready(); // Signal that teams are ready
                playground.startTrial(); // Start the trial on the playground
                playground.wait_for_trial_conclusion(); // Wait for the trial to conclude
                strengthDifference = playground.assertTrialDecision(); // Determine the trial outcome
                // Break out of the trial loop if the strength difference exceeds the threshold, making it a knock out
                if (Math.abs(strengthDifference) >= SimulPar.KT) break;
            }
            ropePosition = playground.declareGameWinner(); // Declare the winner of the current game
            // Update scores based on the rope position
            if (ropePosition < 0) score1++; // Team 1 wins
            if (ropePosition > 0) score2++; // Team 2 wins
        }
        contestantsBench.declareMatchWinner(score1, score2); // Declare the winner of the match
    }
}
