package serverSide.main;

import serverSide.entities.*;
import serverSide.sharedRegions.*;
import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;
import java.net.*;

public class ServerGameOfRopePlayground {

    public static boolean waitConnection;

    public static void main(String[] args) {
        Playground playground;
        PlaygroundInterface playgroundInter;
        GeneralReposStub reposStub;
        ServerCom scon, sconi;
        int portNumb = -1;
        String reposServerName;
        int reposPortNumb = -1;

        if (args.length != 1) {
            GenericIO.writelnString("Wrong number of parameters!");
            System.exit(1);
        }
        try {portNumb = Integer.parseInt(args[0]);}
        catch (NumberFormatException e) {
            GenericIO.writelnString("args[0] is not a number!");
            System.exit(1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536)) {
            GenericIO.writelnString("args[0] is not a valid port number!");
            System.exit(1);
        }
        reposServerName = args[1];

        reposStub = new GeneralReposStub(reposServerName, reposPortNumb);
        playground = new Playground(reposStub);
        playgroundInter = new PlaygroundInterface(playground);
        scon = new ServerCom(portNumb);
        scon.start();
        GenericIO.writelnString("Service is established!");
        GenericIO.writelnString("Server is listening for service requests.");

        PlaygroundClientProxy cliProxy;

        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                cliProxy = new PlaygroundClientProxy(sconi, playgroundInter);
                cliProxy.start();
            }
            catch (SocketTimeoutException e) {}
        }
        scon.end();
        GenericIO.writelnString("Server was shutdown.");
    }
}
