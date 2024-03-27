package assignment1.main;

import assignment1.entities.*;
import assignment1.sharedRegions.*;
import genclass.GenericIO;
import genclass.FileOp;

public class GameOfRope {
    public static void main(String[] args) {
        Referee referee;
        Coach[] coach = new Coach[2];
        Contestant[][] contestant = new Contestant[2][SimulPar.NC];
        int[][] contestantStrength = new int[2][SimulPar.NC];
        GeneralRepos repos;
        RefereeSite refereeSite;
        Playground playground;
        ContestantsBench contestantsBench;

        String fileName;
        char opt;
        boolean success;
        //read file name from stdin
        GenericIO.writelnString("\n" + "Game of Rope");
        // do {
        //     GenericIO.writeString("Logging file name? ");
        //     fileName = GenericIO.readlnString();
        //     if (FileOp.exists(".", fileName)) {
        //         do {
        //             GenericIO.writeString(("There is already a file with this name. Delete it (y - yes; n - no)? "));
        //             opt = GenericIO.readlnChar();
        //         } while ((opt != 'y') && (opt != 'n'));
        //         if (opt == 'y') success = true;
        //         else success = false;
        //     }
        //     else success = true;
        // } while (!success);
        fileName = "report.txt";

        //generate contestant strength
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) contestantStrength[i][j] = (int)(5*Math.random()+6);
        }

        //instanciate shared areas
        repos = new GeneralRepos(fileName, contestantStrength);
        refereeSite = new RefereeSite(repos);
        playground = new Playground(repos);
        contestantsBench = new ContestantsBench(repos);

        //instanciate threads
        referee = new Referee(refereeSite, playground, contestantsBench);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestant[i][j] = new Contestant(i, j, contestantStrength[i][j], playground, contestantsBench);
            }
        }
        for (int i = 0; i < 2; i++) {
            coach[i] = new Coach(i, refereeSite, playground, contestantsBench);
        }

        //start threads
        referee.start();
        System.out.println("Referee has started.");
        for (int i = 0; i < 2; i++) {
            coach[i].start();
            System.out.printf("Coach-%d has started.\n", i+1);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestant[i][j].start();
                System.out.printf("Contestant-%d-%d has started.\n", i+1, j+1);
            }
        }

        //join threads
        try {referee.join();}
        catch (InterruptedException e) {}
        System.out.println("Referee has ended.");
        for (int i = 0; i < 2; i++) {
            try {coach[i].join();}
            catch (InterruptedException e) {}
            System.out.printf("Coach-%d has ended.\n", i+1);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                try {contestant[i][j].join();}
                catch (InterruptedException e) {}
                System.out.printf("Contestant-%d-%d has ended.\n", i+1, j+1);
            }
        }
    }
}
