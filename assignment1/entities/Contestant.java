package assignment1.entities;

import assignment1.sharedRegions.*;

public class Contestant extends Thread {
    private ContestantStates state;
    private int team;
    private int number;
    private int strength;
    private Playground playground;
    private ContestantsBench contestantsBench;

    public Contestant(int team, int number, int strength, Playground playground, ContestantsBench contestantsBench) {
        super(String.format("Contestant-%d-%d", team+1, number+1));
        this.state = ContestantStates.SEAT_AT_THE_BENCH;
        this.team = team;
        this.number = number;
        this.strength = strength;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
    }

    public ContestantStates getContestantState() {
        return state;
    }

    public int getTeam() {
        return team;
    }

    public int getNumber() {
        return number;
    }

    public int getStrength() {
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
        int orders;
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
}
