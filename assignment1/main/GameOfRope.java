package assignment1.main;

import assignment1.entities.*;
import assignment1.sharedRegions.*;
import genclass.GenericIO;
import genclass.FileOp;

public class GameOfRope {
    public static void main(String[] args) {
        Referee referee;
        Coach[] coach = new Coach[2];
        Contestant[][] contestant = new Contestant[2][5];
        short[][] contestantStrength = new short[2][5];
        GeneralRepos repos;
        RefereeSite refereeSite;
        Playground playground;
        ContestantsBench contestantsBench;
        String fileName;
        char opt;
        boolean success;

        //read file name from stdin
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

        //generate contestant strength
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) contestantStrength[i][j] = (short)(4*Math.random()+6);
        }

        //instanciate shared areas
        repos = new GeneralRepos(fileName, contestantStrength);
        refereeSite = new RefereeSite(repos);
        playground = new Playground(repos);
        contestantsBench = new ContestantsBench(repos);

        //instanciate threads
        referee = new Referee(refereeSite, playground);
        for (short i = 0; i < 2; i++) {
            for (short j = 0; j < 5; j++) {
                contestant[i][j] = new Contestant(i, j, contestantStrength[i][j], refereeSite, playground, contestantsBench);
            }
        }
        for (short i = 0; i < 2; i++) {
            coach[i] = new Coach(i, contestant[i], refereeSite, playground, contestantsBench);
        }

        //start threads
        referee.start();
        for (int i = 0; i < 2; i++) {
            coach[i].start();
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                contestant[i][j].start();
            }
        }

        //join threads
        try {referee.join();}
        catch (InterruptedException e) {}
        for (int i = 0; i < 2; i++) {
            try {coach[i].join();}
            catch (InterruptedException e) {}
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                try {contestant[i][j].join();}
                catch (InterruptedException e) {}
            }
        }
    }
}
