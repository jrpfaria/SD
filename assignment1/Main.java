package assignment1;

import assignment1.entities.*;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Player[] players = new Player[5];
        for (int i = 0; i < players.length; i++) players[i] = new Player((short)1, (short)1);
        Player[] temp = players.clone();
        Arrays.sort(temp, Collections.reverseOrder());
        for (Player player: temp) System.out.println(player.getStrength());
    }
}
