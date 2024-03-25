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

    public RefereeStates getRefereeState(){
        return state;
    }

    public void setRefereeState(RefereeStates state){
        this.state = state;
    }

    @Override
    public void run() {
        short currentGame;
        short currentTrial;
        short scoreDifference;
        for (currentGame = 1; currentGame <= SimulPar.NG; currentGame++) {
            refereeSite.announceNewGame();
            for (currentTrial = 1; currentTrial <= SimulPar.NT; currentTrial++) {
                contestantsBench.callTrial();
                refereeSite.teams_ready();
                playground.startTrial();
                playground.wait_for_trial_conclusion();
                scoreDifference = playground.assertTrialDecision();
                if (scoreDifference<0) score1++;
                if (scoreDifference>0) score2++;
                if (scoreDifference>=SimulPar.KT) break;
            }
            playground.declareGameWinner();
        }
        contestantsBench.declareMatchWinner(score1, score2);
    }
}
