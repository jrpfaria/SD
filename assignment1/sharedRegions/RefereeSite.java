package assignment1.sharedRegions;

import assignment1.entities.*;

public class RefereeSite {
    private GeneralRepos repos;
    private byte ready;

    public RefereeSite(GeneralRepos repos) {
        this.repos = repos;
    }

    //Referee

    public synchronized void announceNewGame() {
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.START_OF_A_GAME);
        repos.setRefereeState(RefereeStates.START_OF_A_GAME);
        repos.startGame();
    }

    public synchronized void teams_ready() {
        while (ready<2) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    //Coach
    
    public synchronized void informReferee() {
        ready++;
        notifyAll();
    }
}
