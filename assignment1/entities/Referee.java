package assignment1.entities;
import assignment1.sharedRegions.RefereeSite;
import assignment1.sharedRegions.Playground;

public class Referee extends Thread {

    private RefereeState state;
    private RefereeSite refereeSite;
    private Playground playground;

    public Referee(RefereeSite refereeSite, Playground playground) {
        super("Referee");
        this.state = RefereeState.START_OF_THE_MATCH;
        this.refereeSite = refereeSite;
        this.playground = playground;
    }

    public RefereeState getRefereeState(){
        return state;
    }

    public void setRefereeState(RefereeState state){
        this.state = state;
    }

    @Override
    public void run() {
        short current_game;
        short current_trial;
        short total_trials = 6;
        boolean knockout = false;
        for (current_game = 1; current_game <= 3; current_game++) {
            refereeSite.announceNewGame();
            for (current_trial = 1; current_trial <= total_trials; current_trial++) {
                refereeSite.callTrial();
                refereeSite.teams_ready();
                playground.startTrial();
                refereeSite.wait_for_trial_conclusion();
                knockout = playground.assertTrialDecision();
                if (knockout) break;
            }
            playground.declareGameWinner();
        }
        playground.declareMatchWinner();
    }
}
