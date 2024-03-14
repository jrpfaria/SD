package assignment1.entities;

public class Referee extends Thread {

    private RefereeState state;

    public Referee() {
        super("Referee");
        this.state = RefereeState.START_OF_THE_MATCH;
    }

    public RefereeState getRefereeState(){
        return state;
    }

    public void setRefereeStates(RefereeState state){
        this.state = state;
    }

    @Override
    public void run() {
        short current_trial;
        short total_trials = 6;
        // announceNewGame();
        for(current_trial = 0; current_trial < total_trials; current_trial++){
            // do{
            //  callTrial();
            //  startTrial();
            // }while()
        }
        // declareGameWinner();
    }
}
