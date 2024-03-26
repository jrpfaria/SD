package assignment1.sharedRegions;

import assignment1.entities.*;

public class RefereeSite {
    private GeneralRepos repos;
    private int ready;

    public RefereeSite(GeneralRepos repos) {
        this.repos = repos;
    }

    //Referee

    public synchronized void announceNewGame() {
        repos.startGame();
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.START_OF_A_GAME);
        repos.setRefereeState(RefereeStates.START_OF_A_GAME);
    }

    public synchronized void teams_ready() {
        while (ready<2) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        ready = 0;
    }

    //Coach

    public synchronized void informReferee() {
        ready++;
        notifyAll();
    }
}
