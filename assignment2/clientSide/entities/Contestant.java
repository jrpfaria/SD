package clientSide.entities;

import clientSide.stubs.*;
import serverSide.main.*;

/**
 * The Contestant class represents a contestant entity in the game simulation.
 * Contestants participate in various activities during the game and follow instructions from coaches.
 */
public class Contestant extends Thread {
    /**
     * Stores the index for the team to which the player belongs
     */
    private final int team;
    /**
     * Stores the index for the player within the team
     */
    private final int number;
    /**
     * Stores the reference to the playground shared area
     */
    private final PlaygroundStub playgroundStub;
    /**
     * Stores the reference to the contestantsBench shared area
     */
    private final ContestantsBenchStub contestantsBenchStub;
    /**
     * Stores the contestant's state
     */
    private ContestantStates state;
    /**
     * Stores the strength of the player
     */
    private int strength;

    /**
     * Constructor for Contestant class.
     * Initializes a contestant with a specific team, number, strength, playground, and contestants bench.
     *
     * @param team                 The team index of the contestant.
     * @param number               The contestant number within the team.
     * @param strength             The strength level of the contestant.
     * @param playgroundStub       The playground shared stub.
     * @param contestantsBenchStub The contestants bench stub.
     */
    public Contestant(int team, int number, int strength, PlaygroundStub playgroundStub, ContestantsBenchStub contestantsBenchStub) {
        super(String.format("Contestant-%d-%d", team + 1, number + 1));
        this.state = ContestantStates.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
        this.playgroundStub = playgroundStub;
        this.contestantsBenchStub = contestantsBenchStub;
    }

    /**
     * Retrieves the state of the contestant.
     *
     * @return The current state of the contestant.
     */
    public ContestantStates getContestantState() {
        return state;
    }

    /**
     * Sets the state of the contestant.
     *
     * @param state The state to set for the contestant.
     */
    public void setContestantState(ContestantStates state) {
        this.state = state;
    }

    /**
     * Retrieves the team index of the contestant.
     *
     * @return The team index of the contestant.
     */
    public int getTeam() {
        return team;
    }

    /**
     * Retrieves the number of the contestant within the team.
     *
     * @return The number of the contestant within the team.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retrieves the strength level of the contestant.
     *
     * @return The strength level of the contestant.
     */
    public int getStrength() {
        return strength;
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
        if (this.strength > 0) {
            this.strength--;
        }
    }

    /**
     * Overrides the run method of Thread class.
     * This method represents the behavior of the contestant during the game.
     */
    @Override
    public void run() {
        int orders;
        while (true) {
            orders = contestantsBenchStub.seat_at_the_bench(); // Wait for instructions while seated at the bench
            switch (orders) {
                case 0: // Match is over; terminate thread
                    return;
                case 1: // Player was not called; rest and start again
                    increaseStrength();
                    continue;
                case 2: // Player was called; continue execution
                    break;
            }
            playgroundStub.followCoachAdvice(); // Follow coach's advice
            playgroundStub.stand_in_position(); // Stand in position on the playground
            playgroundStub.getReady(); // Get ready for the trial
            pullTheRope();
            playgroundStub.amDone();
            contestantsBenchStub.seatDown(); // Seat down after the trial
        }
    }

    private void pullTheRope() {
        try {
            Thread.sleep((long) ((SimulPar.MAXT - SimulPar.MINT + 1) * Math.random() + SimulPar.MINT));
        } catch (InterruptedException ignored) {
        }
        reduceStrength();
    }
}
