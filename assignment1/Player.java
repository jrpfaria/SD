package assignment1;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Player implements Comparable<Player> {
    private short strength;

    public Player(){
        strength = (short)(Math.random() * 5 + 6);
    }

    public short getStrength(){
        return strength;
    }

    public void rest(){
        strength += 1;
    }

    public void play(){
        strength -= 1;
    }

    public int compareTo(Player b) {
        return Short.compare(strength, b.getStrength());
    }
}
