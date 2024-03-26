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
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.TEAMS_READY);
        repos.setRefereeState(RefereeStates.TEAMS_READY);
    }

    public synchronized void declareMatchWinner(short score1, short score2) {
        matchOver = true;
        notifyAll();
        repos.endMatch(score1, score2);
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.END_OF_THE_MATCH);
        repos.setRefereeState(RefereeStates.END_OF_THE_MATCH);
    }

    //Coach

    public synchronized byte wait_for_referee_command(short team) {
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

    public synchronized void callContestants(short team, short[] roster) {
        for (int i = 0; i < SimulPar.NC; i++) called[team][i] = 1;
        for (short number: roster) called[team][number] = 2;
        notifyAll();
    }

    //Contestants

    public synchronized byte seat_at_the_bench(short team, short number) {
        Contestant c = (Contestant)Thread.currentThread();
        c.setContestantState(ContestantStates.SEAT_AT_THE_BENCH);
        repos.setContestantState(team, number, ContestantStates.SEAT_AT_THE_BENCH);
        while (!matchOver && called[team][number]==0) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        if (matchOver) return 0;
        byte orders = called[team][number];
        if (orders==1) {
            c.increaseStrength();
            repos.setContestantStrength(team, number, c.getStrength());
        }
        called[team][number] = 0;
        return orders;
    }

    public synchronized void seatDown(short team, short number) {
        Contestant c = (Contestant)Thread.currentThread();
        c.reduceStrength();
        repos.removeContestant(team, number);
        repos.setContestantStrength(team, number, c.getStrength());
    }
}
