package assignment1.sharedRegions;

public class Playground {
    private int[] strength = new int[]{0, 0};
    private boolean startTrial = false;
    private int amDone = 0;

    public synchronized void startTrial() {
        startTrial = true;
        notifyAll();
        while (amDone!=6) {
            try {wait();}
            catch (InterruptedException e) {}
        }
        amDone = 0;
    }

    public synchronized int assertTrialDecision() {
        return strength[0]-strength[1];
    }

    public synchronized void getReady() {
        
    }

    public synchronized void pullTheRope(short team, short strength) {
        this.strength[team] += strength;
    }

    public synchronized void amDone() {
        amDone++;
        notifyAll();
    }
}
