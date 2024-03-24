package assignment1.sharedRegions;

import assignment1.entities.*;

public class ContestantsBench {
    private GeneralRepos repos;
    private boolean[][] called;

    public ContestantsBench(GeneralRepos repos) {
        this.repos = repos;
        called = new boolean[2][5];
    }

    public synchronized void callContestants(short team, short[] roster) {
        ((Coach)Thread.currentThread()).setCoachState(CoachStates.ASSEMBLE_TEAM);
        repos.setCoachState(team, CoachStates.ASSEMBLE_TEAM);
        for (short number: roster) called[team][number] = true;
    }
}
