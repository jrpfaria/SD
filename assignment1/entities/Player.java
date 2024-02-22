package assignment1.entities;

import java.lang.Math;

public class Player extends Thread implements Comparable<Player> {
    private short team;
    private short number;
    private short strength;

    public Player(short team, short number) {
        super();
        this.team = team;
        this.number = number;
        this.strength = (short)(Math.random() * 5 + 6);
    }

    public short getTeam() {
        return team;
    }

    public short getNumber() {
        return number;
    }

    public short getStrength() {
        return strength;
    }

    public void rest() {
        strength += 1;
    }

    public void play() {
        strength -= 1;
    }

    public int compareTo(Player b) {
        return Short.compare(strength, b.getStrength());
    }
}
