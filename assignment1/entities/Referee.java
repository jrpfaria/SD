package assignment1.entities;

import assignment1.main.SimulPar;
import assignment1.sharedRegions.RefereeSite;
import assignment1.sharedRegions.Playground;

public class Referee extends Thread {

    private RefereeStates state;
    private RefereeSite refereeSite;
    private Playground playground;

    public Referee(RefereeSite refereeSite, Playground playground) {
        super("Referee");
        this.state = RefereeStates.START_OF_THE_MATCH;
        this.refereeSite = refereeSite;
        this.playground = playground;
    }

    public RefereeStates getRefereeState(){
        return state;
    }

    public void setRefereeState(RefereeStates state){
        this.state = state;
    }

    @Override
    public void run() {
        // short currentGame;
        // short currentTrial;
        // boolean knockout = false;
        // for (currentGame = 1; currentGame <= SimulPar.NG; currentGame++) {
            refereeSite.announceNewGame();
        //     for (currentTrial = 1; currentTrial <= SimulPar.NT; currentTrial++) {
                refereeSite.callTrial();
        //         refereeSite.teams_ready();
        //         playground.startTrial();
        //         refereeSite.wait_for_trial_conclusion();
        //         knockout = playground.assertTrialDecision();
        //         if (knockout) break;
        //     }
        //     playground.declareGameWinner();
        // }
        // playground.declareMatchWinner();
    }
}
