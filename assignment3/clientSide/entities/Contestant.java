package clientSide.entities;

import java.rmi.RemoteException;
import genclass.GenericIO;
import interfaces.*;
import serverSide.main.*;

/**
 * The Contestant class represents a contestant entity in the game simulation.
 * Contestants participate in various activities during the game and follow
 * instructions from coaches.
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
    private final PlaygroundInterface playgroundStub;
    /**
     * Stores the reference to the contestantsBench shared area
     */
    private final ContestantsBenchInterface contestantsBenchStub;
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
     * Initializes a contestant with a specific team, number, strength, playground,
     * and contestants bench.
     *
     * @param team                 The team index of the contestant.
     * @param number               The contestant number within the team.
     * @param strength             The strength level of the contestant.
     * @param playgroundStub       The playground shared stub.
     * @param contestantsBenchStub The contestants bench stub.
     */
    public Contestant(int team, int number, int strength, PlaygroundInterface playgroundStub,
            ContestantsBenchInterface contestantsBenchStub) {
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
            orders = seat_at_the_bench(); // Wait for instructions while seated at the bench
            switch (orders) {
                case 0: // Match is over; terminate thread
                    return;
                case 1: // Player was not called; rest and start again
                    increaseStrength();
                    continue;
                case 2: // Player was called; continue execution
                    break;
            }
            followCoachAdvice(); // Follow coach's advice
            stand_in_position(); // Stand in position on the playground
            getReady(); // Get ready for the trial
            pullTheRope();
            amDone();
            seatDown(); // Seat down after the trial
        }
    }

    private int seat_at_the_bench() {
        ReturnInt r = null;
        try {
            r = contestantsBenchStub.seat_at_the_bench(team, number, strength);
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1)
                    + " remote exception on seat_at_the_bench: " + e.getMessage());
            System.exit(1);
        }
        this.state = ContestantStates.values()[r.getIntStateVal()];
        return r.getIntVal();
    }

    private void followCoachAdvice() {
        try {
            playgroundStub.followCoachAdvice();
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1)
                    + " remote exception on followCoachAdvice: " + e.getMessage());
            System.exit(1);
        }
    }

    private void stand_in_position() {
        ReturnInt r = null;
        try {
            r = playgroundStub.stand_in_position(team, number);
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1)
                    + " remote exception on stand_in_position: " + e.getMessage());
            System.exit(1);
        }
        this.state = ContestantStates.values()[r.getIntStateVal()];
    }

    private void getReady() {
        ReturnInt r = null;
        try {
            r = playgroundStub.getReady(team, number, strength);
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1) + " remote exception on getReady: "
                    + e.getMessage());
            System.exit(1);
        }
        this.state = ContestantStates.values()[r.getIntStateVal()];
    }

    private void pullTheRope() {
        try {
            Thread.sleep((long) ((SimulPar.MAXT - SimulPar.MINT + 1) * Math.random() + SimulPar.MINT));
        } catch (InterruptedException ignored) {
        }
        reduceStrength();
    }

    private void amDone() {
        try {
            playgroundStub.amDone();
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Contestant " + (team + 1) + "-" + (number + 1) + " remote exception on amDone: " + e.getMessage());
            System.exit(1);
        }
    }

    private void seatDown() {
        ReturnInt r = null;
        try {
            r = contestantsBenchStub.seatDown(team, number);
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant " + (team + 1) + "-" + (number + 1) + " remote exception on seatDown: "
                    + e.getMessage());
            System.exit(1);
        }
        this.state = ContestantStates.values()[r.getIntStateVal()];
    }
}
