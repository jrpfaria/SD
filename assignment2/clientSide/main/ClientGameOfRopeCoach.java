package clientSide.main;

import clientSide.entities.*;
import clientSide.stubs.*;
import genclass.GenericIO;

/**
 * Main class (type 2) for the coach entity.
 * Initializes the coach entity and starts its lifecycle.
 */
public class ClientGameOfRopeCoach {
    /**
     * Main method.
     * @param args runtime arguments
     *      args[0] - General Repository server hostname
     *      args[1] - General Repository server port number
     *      args[2] - RefereeSite server hostname
     *      args[3] - RefereeSite server port number
     *      args[4] - Playground server hostname
     *      args[5] - Playground server port number
     *      args[6] - ContestantsBench server hostname
     *      args[7] - ContestantsBench server port number
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
         * RefereeSite server hostname
         */
        String refereeSiteServerHostName;

        /**
         * RefereeSite server port number
         */
        int refereeSiteServerPortNumb = -1;

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
         * Coach entities
         */
        Coach[] coach = new Coach[2];

        /**
         * General Repository stub
         */
        GeneralReposStub genReposStub;

        /**
         * RefereeSite stub
         */
        RefereeSiteStub refereeSiteStub;

        /**
         * Playground stub
         */
        PlaygroundStub playgroundStub;

        /**
         * ContestantsBench stub
         */
        ContestantsBenchStub contestantsBenchStub;

        if (args.length != 8) {
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

        refereeSiteServerHostName = args[2];
        try {
            refereeSiteServerPortNumb = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[3] is not a number!");
            System.exit(1);
        }
        if ((refereeSiteServerPortNumb < 4000) || (refereeSiteServerPortNumb >= 65536)) {
            GenericIO.writelnString("args[3] is not a valid port number!");
            System.exit(1);
        }

        playgroundServerHostName = args[4];
        try {
            playgroundServerPortNumb = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[5] is not a number!");
            System.exit(1);
        }
        if ((playgroundServerPortNumb < 4000) || (playgroundServerPortNumb >= 65536)) {
            GenericIO.writelnString("args[5] is not a valid port number!");
            System.exit(1);
        }

        contestantsBenchServerHostName = args[6];
        try {
            contestantsBenchServerPortNumb = Integer.parseInt(args[7]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[7] is not a number!");
            System.exit(1);
        }
        if ((contestantsBenchServerPortNumb < 4000) || (contestantsBenchServerPortNumb >= 65536)) {
            GenericIO.writelnString("args[7] is not a valid port number!");
            System.exit(1);
        }

        genReposStub = new GeneralReposStub(genReposServerHostName, genReposServerPortNumb);
        refereeSiteStub = new RefereeSiteStub(refereeSiteServerHostName, refereeSiteServerPortNumb);
        playgroundStub = new PlaygroundStub(playgroundServerHostName, playgroundServerPortNumb);
        contestantsBenchStub = new ContestantsBenchStub(contestantsBenchServerHostName, contestantsBenchServerPortNumb);
        for (int i = 0; i < 2; i++) {
            coach[i] = new Coach(i, Math.random() < 0.5, refereeSiteStub, playgroundStub, contestantsBenchStub);
        }

        for (int i = 0; i < 2; i++) {
            coach[i].start();
        }

        GenericIO.writelnString();
        for (int i = 0; i < 2; i++) {
            try {
                coach[i].join();
            } catch (InterruptedException ignored) {
            }
            GenericIO.writelnString("Coach " + (i + 1) + " has terminated.");
        }

        GenericIO.writelnString();
        refereeSiteStub.shutdown();
        playgroundStub.shutdown();
        contestantsBenchStub.shutdown();
        genReposStub.shutdown();
    }
}
