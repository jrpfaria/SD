package assignment1.sharedRegions;

import assignment1.main.SimulPar;
import assignment1.entities.*;

public class Playground {
    private GeneralRepos repos;
    private boolean startTrial;
    private boolean endTrial;
    private short amDone = 0;
    private short[] inPosition;
    private short ropePosition;
    private short strengthDifference;

    public Playground(GeneralRepos repos) {
        this.repos = repos;
        this.inPosition = new short[2];
    }

    //Referee

    public synchronized void startTrial() {
        repos.setRopePosition((short)(ropePosition+strengthDifference));
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

    public synchronized short assertTrialDecision() {
        startTrial = false;
        endTrial = true;
        notifyAll();
        return strengthDifference;
    }

    public synchronized void declareGameWinner() {
        boolean knockout = false;
        if (Math.abs(strengthDifference)>=4) knockout = true;
        repos.endGame(ropePosition, knockout);
        ropePosition = 0;
    }

    //Coach

    public synchronized void assemble_team(short team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.ASSEMBLE_TEAM);
        repos.setCoachState(team, CoachStates.ASSEMBLE_TEAM);
        while (inPosition[team]!=SimulPar.NP) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        inPosition[team] = 0;
    }

    public synchronized void watch_trial(short team) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.WATCH_TRIAL);
        repos.setCoachState(team, CoachStates.WATCH_TRIAL);
        while (!endTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        amDone = 0;
    }

    //Contestant

    public synchronized void followCoachAdvice(short team) {
        inPosition[team]++;
        notifyAll();
    }

    public synchronized void stand_in_position(short team, short number) {
        ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.STAND_IN_POSITION);
        repos.setContestantState(team, number, ContestantStates.STAND_IN_POSITION);
        while (!startTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    public void getReady(short team, short number) {
        repos.addContestant(team, number);
    }

    public void do_your_best(short team, short number, short strength) {
        ((Contestant)Thread.currentThread()).setContestantState(ContestantStates.DO_YOUR_BEST);
        repos.setContestantState(team, number, ContestantStates.DO_YOUR_BEST);
        pullTheRope(team, strength);
        try {Thread.currentThread().sleep((long)(1+100*Math.random()));}
        catch (InterruptedException e) {}
        amDone();
        synchronized (this) {
            while (!endTrial) {
                try {wait();}
                catch (InterruptedException e) {}
            }
        }
    }

    public synchronized void pullTheRope(short team, short strength) {
        if (team==0) strengthDifference -= strength;
        else strengthDifference += strength;
    }

    public synchronized void amDone() {
        amDone++;
        notifyAll();
    }
}
