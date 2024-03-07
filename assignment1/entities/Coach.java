package assignment1.entities;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Coach extends Thread {
    private short team;
    private Contestant[] players;
    private boolean method; //  Randomly chosen: sweaty if true, lazy if false
    private short gameCounter = 0; // Added to keep track of the number of games played in the gamblers dream method

    public Coach(ThreadGroup group, short team, Contestant[] players) {
        super(String.format("Coach-%d", team));
        this.team = team;
        this.players = players;
        this.method = Math.random() < 0.5;
    }

    public short getTeam() {
        return team;
    }

    public Contestant[] selectPlayers() {
        if (method) // Sweaty
            return selectPlayersSweaty();
        else // Lazy
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
        if (gameCounter++ < 3) {
            gameCounter %= 6;
            return new Contestant[]{sorted[0], sorted[1], sorted[2]};
        }
        return new Contestant[]{sorted[sorted.length-1], sorted[sorted.length-2], sorted[sorted.length-3]};
    }

    @Override
    public void run() {

    }
}
