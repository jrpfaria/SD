package assignment1;

import assignment1.entities.*;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Contestant[] players = new Contestant[5];
        for (int i = 0; i < players.length; i++) players[i] = new Contestant((short)1, (short)1);
        Contestant[] temp = players.clone();
        Arrays.sort(temp, Collections.reverseOrder());
        for (Contestant player: temp) System.out.println(player.getStrength());
    }
}
