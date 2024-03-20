package assignment1.entities;

import java.lang.Math;

public class Contestant extends Thread implements Comparable<Contestant> {
    private ContestantState state;
    private short team;
    private short number;
    private short strength;

    public Contestant(short team, short number, short strength) {
        super(String.format("Contestant-%d-%d", team, number));
        this.state = ContestantState.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
    }

    public ContestantState getContestantState() {
        return state;
    }

    public short getTeam() {
        return team;
    }

    public short getNumber() {
        return number;
    }

    public short getStrength() {
        return strength;
    }

    public void setContestantState(ContestantState state) {
        this.state = state;
    }

    public void rest() {
        strength += 1;
    }

    public void play() {
        strength -= 1;
    }

    @Override
    public int compareTo(Contestant b) {
        return Short.compare(strength, b.getStrength());
    }

    @Override
    public void run() {
        // while (!endOfMatches)
        // {
        //     followCoachAdvice();
        //     getReady();
        //     pullTheRope();
        //     amDone();
        //     sitDown();
        // }
    }

    public void pullTheRope() {
        try
        { sleep ((long) (1 + 100 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }
}
