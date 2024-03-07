package assignment1.sharedRegions;

public class RefereeSite {
    private boolean newGame = false;
    private boolean callTrial = false;

    public synchronized void announceNewGame() {
        newGame = true;
        notifyAll();
    }

    public synchronized void callTrial() {
        callTrial = true;
        notifyAll();
    }
}
