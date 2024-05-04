package clientSide.main;

import clientSide.entities.*;
import clientSide.stubs.*;
import genclass.GenericIO;

public class ClientGameOfRopeCoach {
    public static void main(String[] args) {
        String genReposServerHostName;
        int genReposServerPortNumb = -1;   
        String refereeSiteServerHostName;
        int refereeSiteServerPortNumb = -1;
        String playgroundServerHostName;
        int playgroundServerPortNumb = -1;
        String contestantsBenchServerHostName;
        int contestantsBenchServerPortNumb = -1;
        Coach[] coach = new Coach[2];
        GeneralReposStub genReposStub;
        RefereeSiteStub refereeSiteStub;
        PlaygroundStub playgroundStub;
        ContestantsBenchStub contestantsBenchStub;

        if (args.length != 8) {
            GenericIO.writelnString("Wrong number of parameters!");
            System.exit(1);
        }

        genReposServerHostName = args[0];
        try {
            genReposServerPortNumb = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
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
        }
        catch (NumberFormatException e) {
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
        }
        catch (NumberFormatException e) {
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
        }
        catch (NumberFormatException e) {
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
            }
            catch (InterruptedException e) {}
            GenericIO.writelnString("Coach " + (i+1) + " has terminated.");
        }

        GenericIO.writelnString();
        refereeSiteStub.shutdown();
        playgroundStub.shutdown();
        contestantsBenchStub.shutdown();
        genReposStub.shutdown();
    }
}
