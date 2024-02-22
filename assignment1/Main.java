package assignment1;

import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Player[] players = new Player[5];
        for (int i = 0; i < players.length; i++) players[i] = new Player();
        Arrays.sort(players, Collections.reverseOrder());
        for (Player player: players) System.out.println(player.getStrength());
    }
}
