package assignment1.entities;

import assignment1.main.SimulPar;
import assignment1.sharedRegions.*;
import assignment1.commonInfra.Pair;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Coach extends Thread {
    private CoachStates state;
    private int team;
    private RefereeSite refereeSite;
    private Playground playground;
    private ContestantsBench contestantsBench;
    private boolean method; // Randomly chosen: sweaty if true, gambler's dream if false
    private int gameCounter = 0; // Added to keep track of the number of games played in the gambler's dream method

    public Coach(int team, RefereeSite refereeSite, Playground playground, ContestantsBench contestantsBench) {
        super(String.format("Coach-%d", team+1));
        this.state = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        this.team = team;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
        this.method = true; //Math.random() < 0.5;
    }

    public int getTeam() {
        return this.team;
    }

    public void setCoachState(CoachStates state) {
        this.state = state;
    }

    public int[] selectPlayers(Pair<Integer, Integer>[] contestants) {
        Pair<Integer, Integer>[] sorted = contestants.clone();
        Arrays.sort(sorted, Collections.reverseOrder());
        if (method) // Sweaty
            return selectPlayersSweaty(sorted);
        else // Gamblers Dream
            return selectPlayersGamblersDream(sorted);
    }

    public int[] selectPlayersSweaty(Pair<Integer, Integer>[] sorted) {
        int[] roster = new int[SimulPar.NP];
        for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[i].getKey();
        return roster;
    }
    
    public int[] selectPlayersGamblersDream(Pair<Integer, Integer>[] sorted) {
        int[] roster = new int[SimulPar.NP];
        if (gameCounter++ < SimulPar.NG*SimulPar.NT) {
            for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[i].getKey();
        }
        else for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[SimulPar.NP-1].getKey();
        return roster;
    }

    @Override
    public void run() {
        int orders;
        Pair<Integer, Integer>[] contestants = contestantsBench.reviewNotes(team);
        int[] roster = selectPlayers(contestants);
        while (true) {
            orders = contestantsBench.wait_for_referee_command(team);
            if (orders==0) return;
            contestantsBench.callContestants(team, roster);
            playground.assemble_team(team);
            refereeSite.informReferee();
            playground.watch_trial(team);
            contestants = contestantsBench.reviewNotes(team);
            roster = selectPlayers(contestants);
        }
    }
}
