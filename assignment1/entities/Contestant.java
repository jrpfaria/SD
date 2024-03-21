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
        boolean called;
        while (true) {
            called = contestantsBench.seat_at_the_bench();
            if (called) {
                if (!contestantsBench.checkRoster(team, number)) {
                    rest();
                    continue;
                }
            }
            else if (refereeSite.matchOver()) {
                return;
            }
            
            contestantsBench.followCoachAdvice();
            playground.stand_in_position();
            playground.getReady();
            playground.pullTheRope(team, strength);
            refereeSite.amDone();
            refereeSite.do_your_best();
            play();
            contestantsBench.sitDown();
        }
    }

    @Override
    public int compareTo(Contestant b) {
        return Short.compare(strength, b.getStrength());
    }
}
