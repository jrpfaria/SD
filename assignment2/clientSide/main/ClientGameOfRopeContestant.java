package clientSide.main;

import clientSide.entities.*;
import clientSide.stubs.*;
import genclass.GenericIO;
import serverSide.main.*;

/**
 * Client side of the Game Of Rope (contestants).
 * <p>
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ClientGameOfRopeContestant {
    /**
     * Main method.
     *
     * @param args runtime arguments
     *             args[0] - General Repository server hostname
     *             args[1] - General Repository server port number
     *             args[2] - Playground server hostname
     *             args[3] - Playground server port number
     *             args[4] - ContestantsBench server hostname
     *             args[5] - ContestantsBench server port number
     *             args[6] - file name
     */
    public static void main(String[] args) {
        /**
         * General Repository server hostname
         */
        String genReposServerHostName;

        /**
         * General Repository server port number
         */
        int genReposServerPortNumb = -1;

        /**
         * Playground server hostname
         */
        String fileName;

        /**
         * Playground server hostname
         */
        String playgroundServerHostName;

        /**
         * Playground server port number
         */
        int playgroundServerPortNumb = -1;

        /**
         * ContestantsBench server hostname
         */
        String contestantsBenchServerHostName;

        /**
         * ContestantsBench server port number
         */
        int contestantsBenchServerPortNumb = -1;

        /**
         * Contestant entities
         */
        Contestant[][] contestant = new Contestant[2][SimulPar.NC];

        /**
         * Contestants strengths
         */
        int[][] contestantStrength = new int[2][SimulPar.NC];

        /**
         * General Repository stub
         */
        GeneralReposStub genReposStub;

        /**
         * Playground stub
         */
        PlaygroundStub playgroundStub;

        /**
         * ContestantsBench stub
         */
        ContestantsBenchStub contestantsBenchStub;

        if (args.length != 7) {
            GenericIO.writelnString("Wrong number of parameters!");
            System.exit(1);
        }

        genReposServerHostName = args[0];
        try {
            genReposServerPortNumb = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[1] is not a number!");
            System.exit(1);
        }
        if ((genReposServerPortNumb < 4000) || (genReposServerPortNumb >= 65536)) {
            GenericIO.writelnString("args[1] is not a valid port number!");
            System.exit(1);
        }

        playgroundServerHostName = args[2];
        try {
            playgroundServerPortNumb = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[3] is not a number!");
            System.exit(1);
        }
        if ((playgroundServerPortNumb < 4000) || (playgroundServerPortNumb >= 65536)) {
            GenericIO.writelnString("args[3] is not a valid port number!");
            System.exit(1);
        }

        contestantsBenchServerHostName = args[4];
        try {
            contestantsBenchServerPortNumb = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[5] is not a number!");
            System.exit(1);
        }
        if ((contestantsBenchServerPortNumb < 4000) || (contestantsBenchServerPortNumb >= 65536)) {
            GenericIO.writelnString("args[5] is not a valid port number!");
            System.exit(1);
        }

        fileName = args[6];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++)
                contestantStrength[i][j] = (int) ((SimulPar.MAXS - SimulPar.MINS + 1) * Math.random() + SimulPar.MINS);
        }

        genReposStub = new GeneralReposStub(genReposServerHostName, genReposServerPortNumb);
        genReposStub.initSimul(fileName, contestantStrength);
        playgroundStub = new PlaygroundStub(playgroundServerHostName, playgroundServerPortNumb);
        contestantsBenchStub = new ContestantsBenchStub(contestantsBenchServerHostName, contestantsBenchServerPortNumb);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestant[i][j] = new Contestant(i, j, contestantStrength[i][j], playgroundStub, contestantsBenchStub);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestant[i][j].start();
            }
        }

        GenericIO.writelnString();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                try {
                    contestant[i][j].join();
                } catch (InterruptedException ignored) {
                }
                GenericIO.writelnString("Contestant " + (i + 1) + "-" + (j + 1) + " has terminated.");
            }
        }

        GenericIO.writelnString();

        playgroundStub.shutdown();
        contestantsBenchStub.shutdown();
        genReposStub.shutdown();
    }
}
