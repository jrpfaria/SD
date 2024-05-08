package serverSide.main;

import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

import java.net.SocketTimeoutException;

/**
 * Server side of the Contestants Bench.
 * <p>
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ServerGameOfRopeContestantsBench {
    /**
     * waitConnection flag.
     */
    public static boolean waitConnection;

    /**
     * Main program.
     *
     * @param args runtime arguments
     *             args[0] - port number for listening to service requests
     *             args[1] - General Repository server hostname
     *             args[2] - General Repository server port number
     */
    public static void main(String[] args) {
        ContestantsBench contestantsBench;
        ContestantsBenchInterface contestantsBenchInter;
        GeneralReposStub reposStub;
        ServerCom scon, sconi;
        int portNumb = -1;
        String reposServerName;
        int reposPortNumb = -1;

        if (args.length != 3) {
            GenericIO.writelnString("Wrong number of parameters!");
            System.exit(1);
        }
        try {
            portNumb = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[0] is not a number!");
            System.exit(1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536)) {
            GenericIO.writelnString("args[0] is not a valid port number!");
            System.exit(1);
        }
        reposServerName = args[1];
        try {
            reposPortNumb = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[2] is not a number!");
            System.exit(1);
        }
        if ((reposPortNumb < 4000) || (reposPortNumb >= 65536)) {
            GenericIO.writelnString("args[2] is not a valid port number!");
            System.exit(1);
        }

        reposStub = new GeneralReposStub(reposServerName, reposPortNumb);
        contestantsBench = new ContestantsBench(reposStub);
        contestantsBenchInter = new ContestantsBenchInterface(contestantsBench);
        scon = new ServerCom(portNumb);
        scon.start();
        GenericIO.writelnString("Service is established!");
        GenericIO.writelnString("Server is listening for service requests.");

        ContestantsBenchClientProxy cliProxy;

        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                cliProxy = new ContestantsBenchClientProxy(sconi, contestantsBenchInter);
                cliProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }
        scon.end();
        GenericIO.writelnString("Server was shutdown.");
    }
}
