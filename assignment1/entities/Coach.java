package assignment1.entities;

import assignment1.main.SimulPar;
import assignment1.sharedRegions.*;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Coach extends Thread {
    private CoachStates state;
    private short team;
    private Contestant[] players;
    private RefereeSite refereeSite;
    private Playground playground;
    private ContestantsBench contestantsBench;
    private boolean method; //  Randomly chosen: sweaty if true, gambler's dream if false
    private short gameCounter = 0; // Added to keep track of the number of games played in the gambler's dream method

    public Coach(short team, Contestant[] players, RefereeSite refereeSite, Playground playground, ContestantsBench contestantsBench) {
        super(String.format("Coach-%d", team+1));
        this.state = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        this.team = team;
        this.players = players;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
        this.method = true; //Math.random() < 0.5;
    }

    public void setCoachState(CoachStates state) {
        this.state = state;
    }

    public short[] selectPlayers() {
        if (method) // Sweaty
            return selectPlayersSweaty();
        else // Gamblers Dream
            return selectPlayersGamblersDream();
    }

    public short[] selectPlayersSweaty() {
        Contestant[] sorted = players.clone();
        Arrays.sort(sorted, Collections.reverseOrder());
        short[] roster = new short[SimulPar.NP];
        for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[i].getNumber();
        return roster;
    }
    
    public short[] selectPlayersGamblersDream() {
        Contestant[] sorted = players.clone();
        Arrays.sort(sorted);
        short[] roster = new short[SimulPar.NP];
        if (gameCounter++ < SimulPar.NG*SimulPar.NT) {
            for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[i].getNumber();
        }
        else for (int i = 0; i < SimulPar.NP; i++) roster[i] = sorted[SimulPar.NP-1].getNumber();
        return roster;
    }

    public short[] reviewNotes(){
        short[] selectedPlayers = selectPlayers();
        return selectedPlayers;
        // Inform the selected players that they are about to play
        // we do that by adding it to the Bench shared memory area
    }

    @Override
    public void run() {
        short currentGame;
        short currentTrial;
        byte orders;
        short[] roster = reviewNotes();
        while (true) {
            orders = contestantsBench.wait_for_referee_command(team);
            if (orders==0) return;
            contestantsBench.callContestants(team, roster);
            playground.assemble_team(team);
            refereeSite.informReferee();
            playground.watch_trial(team);
            roster = reviewNotes();
        }
    }
}
