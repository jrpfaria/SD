package assignment1.entities;

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
        super(String.format("Coach-%d", team));
        this.state = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        this.team = team;
        this.players = players;
        this.refereeSite = refereeSite;
        this.playground = playground;
        this.contestantsBench = contestantsBench;
        this.method = Math.random() < 0.5;
    }

    public CoachStates getCoachState() {
        return state;
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
        return new short[]{sorted[0].getNumber(), sorted[1].getNumber(), sorted[2].getNumber()};
    }

    public short[] selectPlayersGamblersDream() {
        Contestant[] sorted = players.clone();
        Arrays.sort(sorted);
        if (gameCounter++ < 9) {
            return new short[]{sorted[0].getNumber(), sorted[1].getNumber(), sorted[2].getNumber()};
        }
        return new short[]{sorted[sorted.length-1].getNumber(), sorted[sorted.length-2].getNumber(), sorted[sorted.length-3].getNumber()};
    }

    @Override
    public void run() {
        // short current_game;
        // short current_trial;
        // short total_trials = 6;
        // boolean knockout;
        short[] roster = reviewNotes();
        // for (current_game = 1; current_game <= 3; current_game++) {
        //     for (current_trial = 1; current_trial <= 6; current_trial++) {
                refereeSite.wait_for_referee_command();
                contestantsBench.callContestants(team, roster);
        //         playground.assemble_team();
        //         refereeSite.informReferee();
        //         knockout = playground.watch_trial();
        //         roster = reviewNotes();
        //         if (knockout) break;
        //     }
        // }
    }

    public short[] reviewNotes(){
        short[] selectedPlayers = selectPlayers();
        return selectedPlayers;
        // Inform the selected players that they are about to play
        // we do that by adding it to the Bench shared memory area
    }
}
