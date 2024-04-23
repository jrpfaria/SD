package clientSide.entities;

import clientSide.stubs.*;
import serverSide.main.SimulPar;
/**
 * The Referee class represents the referee entity in the game simulation.
 * The referee oversees the match, announces new games, manages trials, and declares winners.
 */
public class Referee extends Thread {
    /**
     * Stores the referee's state
     */
    private RefereeStates state;

    /**
     * Stores the reference for the refereeSite shared area
     */
    private final RefereeSiteStub refereeSiteStub;
    
    /**
     * Stores the reference for the playground shared area
     */
    private final PlaygroundStub playgroundStub;
    
    /**
     * Stores the reference for the contestantsBench shared area
     */
    private final ContestantsBenchStub contestantsBenchStub;
    
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
     * Initializes the referee with a referee site, playground, and contestants bench.
     * @param refereeSite The referee site shared memory area.
     * @param playground The playground shared memory area.
     * @param contestantsBench The contestants bench shared memory area.
     */
    public Referee(RefereeSiteStub refereeSiteStub, PlaygroundStub playgroundStub, ContestantsBenchStub contestantsBenchStub) {
        super("Referee");
        this.state = RefereeStates.START_OF_THE_MATCH;
        this.refereeSiteStub = refereeSiteStub;
        this.playgroundStub = playgroundStub;
        this.contestantsBenchStub = contestantsBenchStub;
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
            refereeSiteStub.announceNewGame(); // Announce the start of a new game
            // Iterate through each trial of the current game
            for (currentTrial = 1; currentTrial <= SimulPar.NT; currentTrial++) {
                contestantsBenchStub.callTrial(); // Call for a trial to start
                refereeSiteStub.teams_ready(); // Signal that teams are ready
                playgroundStub.startTrial(); // Start the trial on the playground
                playgroundStub.wait_for_trial_conclusion(); // Wait for the trial to conclude
                ropePosition = playgroundStub.assertTrialDecision(); // Determine the trial outcome
                // Break out of the trial loop if the strength difference exceeds the threshold, making it a knock out
                if (Math.abs(ropePosition) >= SimulPar.KT) break;
            }
            ropePosition = playgroundStub.declareGameWinner(); // Declare the winner of the current game
            // Update scores based on the rope position
            if (ropePosition < 0) score1++; // Team 1 wins
            if (ropePosition > 0) score2++; // Team 2 wins
        }
        contestantsBenchStub.declareMatchWinner(score1, score2); // Declare the winner of the match
    }
}
