package assignment1.sharedRegions;

import assignment1.entities.Contestant;
import assignment1.entities.ContestantState;

public class Playground {
    private GeneralRepos repos;
    private short team_A_strength = 0;
    private short team_B_strength = 0;
    private boolean startTrial = false;
    private short amDone = 0;

    public Playground(GeneralRepos repos) {
        this.repos = repos;
    }

    public synchronized void startTrial() {
        //mudar estado do referee
        //comunicar a mudança ao repositório
        startTrial = true;
        notifyAll();
        while (amDone<6) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        amDone = 0;
    }

    // "define"s to improve readability
    private static final short team_A = 1;
    private static final short team_B = -1;
    private static final short draw = 0;
    
    public synchronized short assertTrialDecision() {
        short result = (short)(team_A_strength - team_B_strength);
        
        if (result == 0) return draw;
        short winner = (result > 0) ? team_A : team_B;

        // Missing KO condition
        return winner;
    }

    public synchronized void getReady() {
        Contestant contestant = (Contestant)Thread.currentThread();

        if (contestant.getTeam() == 1)
            team_A_strength += contestant.getStrength();
        else
            team_B_strength += contestant.getStrength();
    }

    public synchronized void amDone() {
        Contestant contestant = (Contestant)Thread.currentThread();
        contestant.pullTheRope();
        contestant.setContestantState(ContestantState.SEAT_AT_THE_BENCH);
        amDone++;
        notifyAll();
    }
}
