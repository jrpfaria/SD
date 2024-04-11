package assignment2.entities;

import assignment2.main.SimulPar;
import assignment2.sharedRegions.*;
import assignment2.commonInfra.Pair;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
/**
 * The Coach class represents a coach entity in the game simulation.
 * Coaches manage their teams, select players for each game, and participate in various activities.
 */
public class Coach extends Thread {
    /**
     * Stores the coach's state
     */
    private CoachStates state;
    
    /**
     * Stores the index of the corresponding team
     */
    private final int team;
    
    /**
     * Stores the reference for the refereeSite shared area
     */
    private final RefereeSite refereeSite;
    
    /**
     * Stores the reference for the playground shared area
     */
    private final Playground playground;
    
    /**
     * Stores the reference for the contestantsBench shared area
     */
    private final ContestantsBench contestantsBench;

    /**
     * Stores the method which the coach will follow for selecting players
     */
    private final boolean method; // Randomly chosen: sweaty if true, gambler's dream if false
    
    /**
     * Auxiliary variable, added to keep track of the number of games played in the gambler's dream method
     */
    private int gameCounter = 0;

    /**
     * Constructor for Coach class.
     * Initializes the coach with a specific team, referee site, playground, and contestants bench.
     * @param team The team index of the coach.
     * @param refereeSite The referee site shared memory area.
     * @param playground The playground shared memory area.
     * @param contestantsBench The contestants bench shared memory area.
     */
    public Coach(int team, RefereeSite refereeSite, Playground playground, ContestantsBench contestantsBench) {
        super(String.format("Coach-%d", team+1));
        this.state = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        this.team = team;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
        this.method = Math.random() < 0.5; // Randomly choose the coaching method: sweaty or gambler's dream
    }

    /**
     * Retrieves the team index of the coach.
     * @return The team index of the coach.
     */
    public int getTeam() {
        return this.team;
    }

    /**
     * Sets the state of the coach.
     * @param state The state to set for the coach.
     */
    public void setCoachState(CoachStates state) {
        this.state = state;
    }

    /**
     * Selects players for the game based on a given list of contestants.
     * @param contestants The list of contestants available for selection.
     * @return An array containing the indices of the selected players.
     */
    public int[] selectPlayers(Pair<Integer, Integer>[] contestants) {
        Pair<Integer, Integer>[] sorted = contestants.clone();
        Arrays.sort(sorted, Collections.reverseOrder()); // Sort contestants by strength (descending)
        if (method) // Sweaty method
            return selectPlayersSweaty(sorted);
        else // Gambler's Dream method
            return selectPlayersGamblersDream(sorted);
    }

    /**
     * Selects players using the Sweaty method, where the strongest players are selected.
     * @param sorted The sorted list of contestants.
     * @return An array containing the indices of the selected players.
     */
    public int[] selectPlayersSweaty(Pair<Integer, Integer>[] sorted) {
        int[] roster = new int[SimulPar.NP]; // Initialize roster array
        for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[i].getKey(); // Select top players
        return roster;
    }
    
    /**
     * Selects players using the Gambler's Dream method, where the weakest players are selected initially,
     * then the strongest players are selected in subsequent games after a certain number of games.
     * @param sorted The sorted list of contestants.
     * @return An array containing the indices of the selected players.
     */
    public int[] selectPlayersGamblersDream(Pair<Integer, Integer>[] sorted) {
        int[] roster = new int[SimulPar.NP]; // Initialize roster array
        if (gameCounter++ < SimulPar.NG*SimulPar.NT) { // Check if still within initial selection phase
            for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[i].getKey(); // Select all players initially
        } else { // After initial phase, select only the weakest player
            for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[SimulPar.NP-1].getKey();
        }
        return roster;
    }

    /**
     * Overrides the run method of Thread class.
     * This method represents the coach's behavior during the game.
     */
    @Override
    public void run() {
        int orders;
        Pair<Integer, Integer>[] contestants = contestantsBench.reviewNotes(team); // Review notes to help with assembling the team
        int[] roster = selectPlayers(contestants); // Select players for the game
        while (true) {
            orders = contestantsBench.wait_for_referee_command(team); // Wait for referee's command
            if (orders == 0) return; // Terminate if no further orders
            contestantsBench.callContestants(team, roster); // Call selected contestants
            playground.assemble_team(team); // Assemble team on the playground
            refereeSite.informReferee(); // Inform referee that team is ready
            playground.watch_trial(team); // Watch the trial on the playground
            contestants = contestantsBench.reviewNotes(team); // Review notes for next game
            roster = selectPlayers(contestants); // Select players for next game
        }
    }
}
