package assignment1.entities;

import assignment1.sharedRegions.*;

import java.lang.Math;

public class Contestant extends Thread implements Comparable<Contestant> {
    private ContestantStates state;
    private short team;
    private short number;
    private short strength;
    private RefereeSite refereeSite;
    private Playground playground;
    private ContestantsBench contestantsBench;

    public Contestant(short team, short number, short strength, RefereeSite refereeSite, Playground playground, ContestantsBench contestantBench) {
        super(String.format("Contestant-%d-%d", team, number));
        this.state = ContestantStates.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
    }

    public ContestantStates getContestantState() {
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

    public void setContestantState(ContestantStates state) {
        this.state = state;
    }

    public void rest() {
        strength += 1;
    }

    public void play() {
        strength -= 1;
    }

    public void pullTheRope() {
        try {sleep((long)(1+100*Math.random()));}
        catch (InterruptedException e) {}
    }

    @Override
    public void run() {
        // short called;
        // while (true) {
        //     called = contestantsBench.seat_at_the_bench();
        //     switch (called) {
        //         case 0: return; // match is over; close thread
        //         case 1: rest(); continue; // player was not called; rest and start again
        //         case 2: break; // player was called; execute the rest of the code
        //         default: throw new Exception("Invalid return value received by contestant");
        //     }
        //     contestantsBench.followCoachAdvice();
        //     playground.stand_in_position();
        //     playground.getReady();
        //     playground.pullTheRope();
        //     playground.amDone();
        //     playground.do_your_best();
        //     play();
        //     contestantsBench.sitDown();
        // }
    }

    @Override
    public int compareTo(Contestant b) {
        return Short.compare(strength, b.getStrength());
    }
}
