package assignment1.sharedRegions;

import assignment1.entities.*;

public class RefereeSite {
    private GeneralRepos repos;
    private boolean newGame = false;
    private boolean callTrial = false;

    public RefereeSite(GeneralRepos repos) {
        this.repos = repos;
    }

    public synchronized void announceNewGame() {
        ((Referee)Thread.currentThread()).setRefereeState(RefereeStates.START_OF_A_GAME);
        repos.setRefereeState(RefereeStates.START_OF_A_GAME);
        newGame = true;
        notifyAll();
    }

    public synchronized void wait_for_referee_command() {
        while (!callTrial) {
            try {wait();}
            catch (InterruptedException e) {}
        }
    }

    public synchronized void callTrial() {
        callTrial = true;
        notifyAll();
    }
}
