package assignment1;

import java.lang.Math;

public class Coach {
    private Player[] team;
    private boolean method;
    private int selected = 0;
    
    public Coach(Player[] team){
        this.team = team;
        this.method = Math.random() < 0.5;
    }

    public Player[] selectPlayers(){
        if (method)
            return selectPlayersSweaty();
        else
            return selectPlayersLazy();
    }

    public Player[] selectPlayersSweaty(){
        Player[] sorted = new Player[team.length];

        for (int i = 0; i < team.length; i++){
            sorted[i] = team[i];
        }

        for (int i = 0; i < sorted.length; i++){
            for (int j = i + 1; j < sorted.length; j++){
                if (sorted[i].getStrength() < sorted[j].getStrength()){
                    Player temp = sorted[i];
                    sorted[i] = sorted[j];
                    sorted[j] = temp;
                }
            }
        }

        return new Player[]{sorted[0], sorted[1], sorted[2]};
    }

    public Player[] selectPlayersLazy(){
        selected %= team.length;
        return new Player[]{
            team[selected++ % team.length],
            team[selected++ % team.length],
            team[selected++ % team.length]
        };
    }
}
