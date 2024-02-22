package assignment1.entities;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Coach extends Thread {
    private Player[] team;
    private boolean method; //  Randomly chosen: sweaty if true, lazy if false
    private int selected = 0; // Added to keep track of the last selected player in the lazy method
    
    public Coach(Player[] team) {
        super();
        this.team = team;
        this.method = Math.random() < 0.5;
    }

    public Player[] selectPlayers() {
        if (method) // Sweaty
            return selectPlayersSweaty();
        else // Lazy
            return selectPlayersLazy();
    }

    public Player[] selectPlayersSweaty() {
        Player[] sorted = team.clone();
        Arrays.sort(sorted, Collections.reverseOrder());
        return new Player[]{sorted[0], sorted[1], sorted[2]};
    }

    public Player[] selectPlayersLazy() {
        selected %= team.length;
        return new Player[]{
            team[selected++ % team.length],
            team[selected++ % team.length],
            team[selected++ % team.length]
        };
    }
}
