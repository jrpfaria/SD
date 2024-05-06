package serverSide.main;

import commInfra.*;
import genclass.GenericIO;
import serverSide.entities.*;
import serverSide.sharedRegions.*;

import java.net.SocketTimeoutException;

public class ServerGameOfRopeGeneralRepos {

    public static boolean waitConnection;

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
            } catch (SocketTimeoutException e) {
            }
        }
        scon.end();
        GenericIO.writelnString("Server was shutdown.");
    }
}
