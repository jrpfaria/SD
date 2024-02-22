package assignment1;
import java.lang.Math;

public class Player{
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
}