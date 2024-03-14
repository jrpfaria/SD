package assignment1.main;

import assignment1.entities.*;
import assignment1.sharedRegions.*;
import genclass.GenericIO;
import genclass.FileOp;

public class GameOfRope {
    public static void main(String[] args) {
        Referee referee;
        Coach[] coaches = new Coach[2];
        Contestant[] contestants = new Contestant[10];
        GeneralRepos repos;
        RefereeSite site;
        Playground playground;
        ContestantsBench bench;
        String fileName;
        char opt;
        boolean success;

        GenericIO.writelnString("\n" + "Game of Rope");
        do {
            GenericIO.writeString("Logging file name? ");
            fileName = GenericIO.readlnString();
            if (FileOp.exists(".", fileName)) {
                do {
                    GenericIO.writeString(("There is already a file with this name. Delete it (y - yes; n - no)? "));
                    opt = GenericIO.readlnChar();
                } while ((opt != 'y') && (opt != 'n'));
                if (opt == 'y') success = true;
                else success = false;
            }
            else success = true;
        } while (!success);

        /*
        repos = new GeneralRepos();
        site = new RefereeSite();
        playground = new Playground();
        bench = new ContestantsBench();
        referee = new Referee();
        for (int i = 0; i < 2; i++) {
            coaches[i] = new Coach();
        }
        for (int i = 0; i < 10; i++) {
            constestant[i] = new Contestant();
        }
        referee.start();
        for (int i = 0; i < 2; i++) {
            coaches[i].start();
        }
        for (int i = 0; i < 10; i++) {
            constestant[i].start();
        try {referee.join();}
        catch (INterruptedException e) {}
        referee.join();
        for (int i = 0; i < 2; i++) {
            try {coaches[i].join();}
            catch (INterruptedException e) {}
        }
        for (int i = 0; i < 10; i++) {
            try {contestants[i].join();}
            catch (INterruptedException e) {}
        }
        */
    }
}
