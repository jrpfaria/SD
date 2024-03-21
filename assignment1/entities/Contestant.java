package assignment1.entities;

import assignment1.sharedRegions.*;

import java.lang.Math;

public class Contestant extends Thread implements Comparable<Contestant> {
    private ContestantState state;
    private short team;
    private short number;
    private short strength;
    private RefereeSite refereeSite;
    private Playground playground;
    private ContestantsBench contestantsBench;

    public Contestant(short team, short number, short strength, RefereeSite refereeSite, Playground playground, ContestantsBench contestantBench) {
        super(String.format("Contestant-%d-%d", team, number));
        this.state = ContestantState.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
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

    public void pullTheRope() {
        try
        { sleep ((long) (1 + 100 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    @Override
    public void run() {
        short current_game;
        short current_trial;
        short total_trials = 6;
        boolean knockout;
        for (current_game = 1; current_game <= 3; current_game++) {
            for (current_trial = 1; current_trial <= 6; current_trial++) {
                contestantsBench.seat_at_the_bench();
                contestantsBench.followCoachAdvice();
                playground.stand_in_position();
                playground.getReady();
                playground.pullTheRope(team, strength);
                refereeSite.amDone();
                knockout = refereeSite.do_your_best();
                contestantsBench.sitDown();
                if (knockout) break;
            }
        }
    }

    @Override
    public int compareTo(Contestant b) {
        return Short.compare(strength, b.getStrength());
    }
}
