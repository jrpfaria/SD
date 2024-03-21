package assignment1.sharedRegions;

public class RefereeSite {
    private GeneralRepos repos;
    private boolean newGame = false;
    private boolean callTrial = false;

    public RefereeSite(GeneralRepos repos) {
        this.repos = repos;
    }

    public synchronized void announceNewGame() {
        newGame = true;
        notifyAll();
    }

    public synchronized void callTrial() {
        callTrial = true;
        notifyAll();
    }
}
