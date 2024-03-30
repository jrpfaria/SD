/**
 * The Contestant class represents a contestant entity in the game simulation.
 * Contestants participate in various activities during the game and follow instructions from coaches.
 */
package assignment1.entities;

import assignment1.sharedRegions.*;

public class Contestant extends Thread {
    /**
     * Stores the contestant's state
     */
    private ContestantStates state;

    /**
     * Stores the index for the team to which the player belongs
     */
    private int team;
    
    /**
     * Stores the index for the player within the team
     */
    private int number;
    
    /**
     * Stores the strength of the player
     */
    private int strength;
    
    /**
     * Stores the reference to the playground shared area
     */
    private Playground playground;
    
    /**
     * Stores the reference to the contestantsBench shared area
     */
    private ContestantsBench contestantsBench;

    /**
     * Constructor for Contestant class.
     * Initializes a contestant with a specific team, number, strength, playground, and contestants bench.
     * @param team The team index of the contestant.
     * @param number The contestant number within the team.
     * @param strength The strength level of the contestant.
     * @param playground The playground shared memory area.
     * @param contestantsBench The contestants bench shared memory area.
     */
    public Contestant(int team, int number, int strength, Playground playground, ContestantsBench contestantsBench) {
        super(String.format("Contestant-%d-%d", team+1, number+1));
        this.state = ContestantStates.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
    }

    /**
     * Retrieves the state of the contestant.
     * @return The current state of the contestant.
     */
    public ContestantStates getContestantState() {
        return state;
    }

    /**
     * Retrieves the team index of the contestant.
     * @return The team index of the contestant.
     */
    public int getTeam() {
        return team;
    }

    /**
     * Retrieves the number of the contestant within the team.
     * @return The number of the contestant within the team.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retrieves the strength level of the contestant.
     * @return The strength level of the contestant.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Sets the state of the contestant.
     * @param state The state to set for the contestant.
     */
    public void setContestantState(ContestantStates state) {
        this.state = state;
    }

    /**
     * Increases the strength level of the contestant.
     */
    public void increaseStrength() {
        this.strength++;
    }

    /**
     * Reduces the strength level of the contestant.
     */
    public void reduceStrength() {
        this.strength--;
    }

    /**
     * Overrides the run method of Thread class.
     * This method represents the behavior of the contestant during the game.
     */
    @Override
    public void run() {
        int orders;
        while (true) {
            orders = contestantsBench.seat_at_the_bench(team, number); // Wait for instructions while seated at the bench
            switch (orders) {
                case 0: return; // Match is over; terminate thread
                case 1: continue; // Player was not called; rest and start again
                case 2: break; // Player was called; continue execution
            }
            playground.followCoachAdvice(team); // Follow coach's advice
            playground.stand_in_position(team, number); // Stand in position on the playground
            playground.getReady(team, number); // Get ready for the trial
            playground.do_your_best(team, number, strength); // Perform the trial with current strength
            contestantsBench.seatDown(team, number); // Seat down after the trial
        }
    }
}
