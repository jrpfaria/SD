package clientSide.entities;

import clientSide.stubs.*;
/**
 * The Contestant class represents a contestant entity in the game simulation.
 * Contestants participate in various activities during the game and follow instructions from coaches.
 */
public class Contestant extends Thread {
    /**
     * Stores the contestant's state
     */
    private ContestantStates state;

    /**
     * Stores the index for the team to which the player belongs
     */
    private final int team;
    
    /**
     * Stores the index for the player within the team
     */
    private final int number;
    
    /**
     * Stores the strength of the player
     */
    private int strength;
    
    /**
     * Stores the reference to the playground shared area
     */
    private final PlaygroundStub playgroundStub;
    
    /**
     * Stores the reference to the contestantsBench shared area
     */
    private final ContestantsBenchStub contestantsBenchStub;

    /**
     * Constructor for Contestant class.
     * Initializes a contestant with a specific team, number, strength, playground, and contestants bench.
     * @param team The team index of the contestant.
     * @param number The contestant number within the team.
     * @param strength The strength level of the contestant.
     * @param playground The playground shared memory area.
     * @param contestantsBench The contestants bench shared memory area.
     */
    public Contestant(int team, int number, int strength, PlaygroundStub playgroundStub, ContestantsBenchStub contestantsBenchStub) {
        super(String.format("Contestant-%d-%d", team+1, number+1));
        this.state = ContestantStates.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
        this.playgroundStub = playgroundStub;
        this.contestantsBenchStub = contestantsBenchStub;
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
            orders = contestantsBenchStub.seat_at_the_bench(team, number); // Wait for instructions while seated at the bench
            switch (orders) {
                case 0: return; // Match is over; terminate thread
                case 1: increaseStrength(); continue; // Player was not called; rest and start again
                case 2: break; // Player was called; continue execution
            }
            playgroundStub.followCoachAdvice(team, number); // Follow coach's advice
            playgroundStub.stand_in_position(team, number); // Stand in position on the playground
            playgroundStub.getReady(team, number); // Get ready for the trial
            playgroundStub.do_your_best(team, number, strength); // Perform the trial with current strength
            try {Thread.sleep((long)((SimulPar.MAXT-SimulPar.MINT+1)*Math.random()+SimulPar.MINT));}
            catch (InterruptedException e) {}
            playgroundStub.amDone();
            reduceStrength();
            contestantsBenchStub.seatDown(team, number, strength); // Seat down after the trial
        }
    }
}