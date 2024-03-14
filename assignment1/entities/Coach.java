package assignment1.entities;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Coach extends Thread {
    private short team;
    private CoachState state;
    private Contestant[] players;
    private boolean method; //  Randomly chosen: sweaty if true, lazy if false
    private short gameCounter = 0; // Added to keep track of the number of games played in the gamblers dream method

    public Coach(ThreadGroup group, short team, Contestant[] players) {
        super(String.format("Coach-%d", team));
        this.state = CoachState.WAIT_FOR_REFEREE_COMMAND;
        this.team = team;
        this.players = players;
        this.method = Math.random() < 0.5;
    }

    public CoachState getCoachState() {
        return state;
    }

    public short getTeam() {
        return team;
    }

    public Contestant[] selectPlayers() {
        if (method) // Sweaty
            return selectPlayersSweaty();
        else // Gamblers Dream
            return selectPlayersGamblersDream();
    }

    public Contestant[] selectPlayersSweaty() {
        Contestant[] sorted = players.clone();
        Arrays.sort(sorted, Collections.reverseOrder());
        return new Contestant[]{sorted[0], sorted[1], sorted[2]};
    }

    public Contestant[] selectPlayersGamblersDream() {
        Contestant[] sorted = players.clone();
        Arrays.sort(sorted);
        if (gameCounter++ < 9) {
            return new Contestant[]{sorted[0], sorted[1], sorted[2]};
        }
        return new Contestant[]{sorted[sorted.length-1], sorted[sorted.length-2], sorted[sorted.length-3]};
    }

    @Override
    public void run() {
        // while(!endOfMatches)
        // {
        //     reviewNotes();
        //     callContestants();
        //     informReferee();
        // }
    }

    public void reviewNotes(){
        // Contestant[] selectedPlayers = selectPlayers();
        // // Inform the selected players that they are about to play
        // // we do that by adding it to the Bench shared memory area

        state = CoachState.WAIT_FOR_REFEREE_COMMAND;
    }
}
