package serverSide.main;

import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

import java.net.SocketTimeoutException;

/**
 * Server side of the General Repository of Information.
 * <p>
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ServerGameOfRopeGeneralRepos {
    /**
     * waitConnection flag.
     */
    public static boolean waitConnection;

    /**
     * Main program.
     *
     * @param args runtime arguments
     *             args[0] - port number for listening to service requests
     */
    public static void main(String[] args) {
        GeneralRepos repos;
        GeneralReposInterface reposInter;
        ServerCom scon, sconi;
        int portNumb = -1;

        if (args.length != 1) {
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

        repos = new GeneralRepos();
        reposInter = new GeneralReposInterface(repos);
        scon = new ServerCom(portNumb);
        scon.start();
        GenericIO.writelnString("Service is established!");
        GenericIO.writelnString("Server is listening for service requests.");

        GeneralReposClientProxy cliProxy;

        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                cliProxy = new GeneralReposClientProxy(sconi, reposInter);
                cliProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }
        scon.end();
        GenericIO.writelnString("Server was shutdown.");
    }
}
