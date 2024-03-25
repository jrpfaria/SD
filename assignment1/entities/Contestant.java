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

    public Contestant(short team, short number, short strength, RefereeSite refereeSite, Playground playground, ContestantsBench contestantsBench) {
        super(String.format("Contestant-%d-%d", team+1, number+1));
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

    public void increaseStrength() {
        this.strength++;
    }

    public void reduceStrength() {
        this.strength--;
    }

    @Override
    public void run() {
        byte orders;
        while (true) {
            orders = contestantsBench.seat_at_the_bench(team, number);
            switch (orders) {
                case 0: return; // match is over; close thread
                case 1: continue; // player was not called; rest and start again
                case 2: break; // player was called; execute the rest of the code
            }
            playground.followCoachAdvice(team);
            playground.stand_in_position(team, number);
            playground.getReady(team, number);
            playground.do_your_best(team, number, strength);
            contestantsBench.seatDown(team, number);
        }
    }

    @Override
    public int compareTo(Contestant b) {
        return Short.compare(strength, b.getStrength());
    }
}
