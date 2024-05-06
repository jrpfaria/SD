package serverSide.main;

import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

import java.net.SocketTimeoutException;

public class ServerGameOfRopeContestantsBench {

    public static boolean waitConnection;

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
