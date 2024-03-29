package assignment1.sharedRegions;

import assignment1.main.SimulPar;
import assignment1.entities.*;
import assignment1.commonInfra.Pair;

public class ContestantsBench {
    private GeneralRepos repos;
    private boolean matchOver;
    private int callTrial;
    private int[][] called;
    private int seated[];
    private Pair<Integer, Integer>[][] contestants;

    @SuppressWarnings("unchecked")
    public ContestantsBench(GeneralRepos repos) {
        this.repos = repos;
        called = new int[2][SimulPar.NC];
        seated = new int[2];
        contestants = (Pair<Integer, Integer>[][]) new Pair[2][];
        contestants[0] = (Pair<Integer, Integer>[]) new Pair[SimulPar.NC];
        contestants[1] = (Pair<Integer, Integer>[]) new Pair[SimulPar.NC];
        for (int i = 0; i < SimulPar.NC; i++) {
            contestants[0][i] = new Pair<Integer, Integer>(i, 0);
            contestants[1][i] = new Pair<Integer, Integer>(i, 0);
        }
    }

    //Referee

    public synchronized void callTrial() {
        callTrial = 2;
        notifyAll();
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.TEAMS_READY);
        repos.setRefereeState(RefereeStates.TEAMS_READY);
    }

    public synchronized void declareMatchWinner(int score1, int score2) {
        matchOver = true;
        notifyAll();
        repos.endMatch(score1, score2);
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.END_OF_THE_MATCH);
        repos.setRefereeState(RefereeStates.END_OF_THE_MATCH);
    }

    //Coach

    public synchronized Pair<Integer, Integer>[] reviewNotes(int team) {
        while (seated[team]<SimulPar.NC) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        return contestants[team];
    }

    public synchronized int wait_for_referee_command(int team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.WAIT_FOR_REFEREE_COMMAND);
        repos.setCoachState(team, CoachStates.WAIT_FOR_REFEREE_COMMAND);
        while (!matchOver && callTrial==0) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        if (matchOver) return 0;
        callTrial--;
        return 1;
    }

    public synchronized void callContestants(int team, int[] roster) {
        for (int i = 0; i < SimulPar.NC; i++) called[team][i] = 1;
        for (int number: roster) called[team][number] = 2;
        notifyAll();
    }

    //Contestants

    public int seat_at_the_bench(int team, int number) {
        Contestant c;
        synchronized (this) {
            c = (Contestant)Thread.currentThread();
            c.setContestantState(ContestantStates.SEAT_AT_THE_BENCH);
            repos.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
            contestants[team][number].setValue(c.getStrength());
            seated[team]++;
            notifyAll();
        }
        synchronized (this) {
            while (!matchOver && called[team][number]==0) {
                try {wait();}
                catch (InterruptedException e) {}
            }
            if (matchOver) return 0;
            seated[team]--;
            int orders = called[team][number];
            if (orders==1) {
                c.increaseStrength();
                repos.setContestantStrength(team, number, c.getStrength());
            }
            called[team][number] = 0;
            return orders;
        }
    }

    public synchronized void seatDown(int team, int number) {
        Contestant c = (Contestant)Thread.currentThread();
        c.reduceStrength();
        repos.removeContestant(team, number);
        repos.setContestantStrength(team, number, c.getStrength());
    }
}
