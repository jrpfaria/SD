package assignment1.sharedRegions;

import assignment1.main.SimulPar;
import assignment1.entities.*;

public class Playground {
    private GeneralRepos repos;
    private boolean startTrial;
    private boolean endTrial;
    private int amDone = 0;
    private int[] inPosition;
    private int ropePosition;
    private int strengthDifference;

    public Playground(GeneralRepos repos) {
        this.repos = repos;
        this.inPosition = new int[2];
    }

    //Referee

    public synchronized void startTrial() {
        repos.setRopePosition(ropePosition);
        repos.startTrial();
        strengthDifference = 0;
        endTrial = false;
        startTrial = true;
        notifyAll();
    }

    public synchronized void wait_for_trial_conclusion() {
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        repos.setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        while (amDone<2*SimulPar.NP) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        amDone = 0;
    }

    public synchronized int assertTrialDecision() {
        startTrial = false;
        endTrial = true;
        notifyAll();
        ropePosition += strengthDifference;
        return strengthDifference;
    }

    public synchronized int declareGameWinner() {
        boolean knockout = false;
        if (Math.abs(strengthDifference)>=4) knockout = true;
        repos.endGame(ropePosition, knockout);
        int ropePosition = this.ropePosition;
        this.ropePosition = 0;
        repos.setRopePosition(0);
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.END_OF_A_GAME);
        repos.setRefereeState(RefereeStates.END_OF_A_GAME);
        return ropePosition;
    }

    //Coach

    public synchronized void assemble_team(int team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.ASSEMBLE_TEAM);
        repos.setCoachState(team, CoachStates.ASSEMBLE_TEAM);
        while (inPosition[team]<SimulPar.NP) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        inPosition[team] = 0;
    }

    public synchronized void watch_trial(int team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.WATCH_TRIAL);
        repos.setCoachState(team, CoachStates.WATCH_TRIAL);
        while (!endTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    //Contestant

    public synchronized void followCoachAdvice(int team) {
        inPosition[team]++;
        notifyAll();
    }

    public synchronized void stand_in_position(int team, int number) {
        ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.STAND_IN_POSITION);
        repos.setContestantState(team, number, ContestantStates.STAND_IN_POSITION);
        while (!startTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    public synchronized void getReady(int team, int number) {
        repos.addContestant(team, number);
    }

    public void do_your_best(int team, int number, int strength) {
        synchronized (this) {
        ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.DO_YOUR_BEST);
            repos.setContestantState(team, number, ContestantStates.DO_YOUR_BEST);
            pullTheRope(team, strength);
        }
        try {Thread.currentThread().sleep((long)(1+100*Math.random()));}
        catch (InterruptedException e) {}
        synchronized (this) {
            amDone();
            while (!endTrial) {
                try {wait();}
                catch (InterruptedException e) {}
            }
        }
    }

    public synchronized void pullTheRope(int team, int strength) {
        if (team==0) strengthDifference -= strength;
        else strengthDifference += strength;
    }

    public synchronized void amDone() {
        amDone++;
        notifyAll();
    }
}
