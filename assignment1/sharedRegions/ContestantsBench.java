package assignment1.sharedRegions;

import assignment1.main.SimulPar;
import assignment1.entities.*;

public class ContestantsBench {
    private GeneralRepos repos;
    private boolean matchOver;
    private byte callTrial;
    private byte[][] called;

    public ContestantsBench(GeneralRepos repos) {
        this.repos = repos;
        called = new byte[2][SimulPar.NC];
    }

    //Referee

    public synchronized void callTrial() {
        callTrial = 2;
        notifyAll();
    }

    public synchronized void declareMatchWinner(short score1, short score2) {
        repos.endMatch(score1, score2);
        matchOver = true;
        notifyAll();
    }

    //Coach

    public synchronized byte wait_for_referee_command() {
        while (!matchOver && callTrial==0) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        if (matchOver) return 0;
        callTrial--;
        return 1;
    }

    public synchronized void callContestants(short team, short[] roster) {
        for (int i = 0; i < SimulPar.NC; i++) called[team][i] = 1;
        for (short number: roster) called[team][number] = 2;
        notifyAll();
    }

    //Contestants

    public synchronized byte seat_at_the_bench(short team, short number) {
        ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.SEAT_AT_THE_BENCH);
        repos.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
        while (!matchOver && called[team][number]==0) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        if (matchOver) return 0;
        return called[team][number];
    }

    public synchronized void seatDown(short team, short number) {
        repos.removeContestant(team, number);
    }
}
