package assignment1.entities;

import java.lang.Math;

public class Contestant extends Thread implements Comparable<Contestant> {
    private short team;
    private short number;
    private short strength;

    public Contestant(short team, short number) {
        super(String.format("Contestant-%d-%d", team, number));
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

    @Override
    public int compareTo(Contestant b) {
        return Short.compare(strength, b.getStrength());
    }

    @Override
    public void run() {
        
    }
}
