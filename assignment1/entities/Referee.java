package assignment1.entities;

import assignment1.main.SimulPar;
import assignment1.sharedRegions.*;

public class Referee extends Thread {

    private RefereeStates state;
    private RefereeSite refereeSite;
    private Playground playground;
    private ContestantsBench contestantsBench;
    private short score1;
    private short score2;

    public Referee(RefereeSite refereeSite, Playground playground, ContestantsBench contestantsBench) {
        super("Referee");
        this.state = RefereeStates.START_OF_THE_MATCH;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
    }

    public void setRefereeState(RefereeStates state){
        this.state = state;
    }

    @Override
    public void run() {
        short currentGame;
        short currentTrial;
        short ropePosition;
        short strengthDifference;
        for (currentGame = 1; currentGame <= SimulPar.NG; currentGame++) {
            refereeSite.announceNewGame();
            for (currentTrial = 1; currentTrial <= SimulPar.NT; currentTrial++) {
                contestantsBench.callTrial();
                refereeSite.teams_ready();
                playground.startTrial();
                playground.wait_for_trial_conclusion();
                strengthDifference = playground.assertTrialDecision();
                if (Math.abs(strengthDifference)>=SimulPar.KT) break;
            }
            ropePosition = playground.declareGameWinner();
            if (ropePosition<0) score1++;
            if (ropePosition>0) score2++;
        }
        contestantsBench.declareMatchWinner(score1, score2);
    }
}
