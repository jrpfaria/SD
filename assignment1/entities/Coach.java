package assignment1.entities;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Coach extends Thread {
    private short team;
    private Contestant[] players;
    private boolean method; //  Randomly chosen: sweaty if true, lazy if false
    private int selected = 0; // Added to keep track of the last selected player in the lazy method
    
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
            return selectPlayersLazy();
    }

    public Contestant[] selectPlayersSweaty() {
        Contestant[] sorted = players.clone();
        Arrays.sort(sorted, Collections.reverseOrder());
        return new Contestant[]{sorted[0], sorted[1], sorted[2]};
    }

    public Contestant[] selectPlayersLazy() {
        selected %= players.length;
        return new Contestant[]{
            players[selected++ % players.length],
            players[selected++ % players.length],
            players[selected++ % players.length]
        };
    }

    @Override
    public void run() {

    }
}
