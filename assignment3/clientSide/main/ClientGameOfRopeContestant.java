package clientSide.main;

import java.rmi.registry.*;
import java.rmi.*;
import clientSide.entities.*;
import interfaces.*;
import serverSide.main.SimulPar;
import genclass.GenericIO;

/**
 * Client side of the Game Of Rope (contestants).
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */

public class ClientGameOfRopeContestant {
    /**
     * Main method.
     *
     * @param args runtime arguments
     *             args[0] - name of the platform where is located the RMI
     *             registering service
     *             args[1] - port number where the registering service is listening
     *             to service requests
     *             args[2] - name of the logging file
     */

    public static void main(String[] args) {
        String rmiRegHostName; // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1; // port number where the registering service is listening to service requests
        String fileName; // name of the logging file

        /* getting problem runtime parameters */

        if (args.length != 3) {
            GenericIO.writelnString("Wrong number of parameters!");
            System.exit(1);
        }
        rmiRegHostName = args[0];
        try {
            rmiRegPortNumb = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[1] is not a number!");
            System.exit(1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            GenericIO.writelnString("args[1] is not a valid port number!");
            System.exit(1);
        }
        fileName = args[2];

        /* problem initialization */

        String nameEntryGeneralRepos = "GeneralRepository"; // public name of the general repository object
        GeneralReposInterface reposStub = null; // remote reference to the general repository object
        String nameEntryPlayground = "Playground"; // public name of the playground object
        PlaygroundInterface playgroundStub = null; // remote reference to the playground object
        String nameEntryContestantsBench = "ContestantsBench"; // public name of the contestants bench object
        ContestantsBenchInterface contestantsBenchStub = null; // remote reference to the contestants bench object
        Registry registry = null; // remote reference for registration in the RMI registry service
        Contestant[][] contestant = new Contestant[2][SimulPar.NC]; // array of contestant threads
        int[][] contestantStrength = new int[2][SimulPar.NC]; // contestants strength

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            GenericIO.writelnString("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            reposStub = (GeneralReposInterface) registry.lookup(nameEntryGeneralRepos);
        } catch (RemoteException e) {
            GenericIO.writelnString("GeneralRepos lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("GeneralRepos not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            playgroundStub = (PlaygroundInterface) registry.lookup(nameEntryPlayground);
        } catch (RemoteException e) {
            GenericIO.writelnString("Playground lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("Playground not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            contestantsBenchStub = (ContestantsBenchInterface) registry.lookup(nameEntryContestantsBench);
        } catch (RemoteException e) {
            GenericIO.writelnString("ContestantsBench lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("ContestantsBench not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++)
                contestantStrength[i][j] = (int) ((SimulPar.MAXS - SimulPar.MINS + 1) * Math.random() + SimulPar.MINS);
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestant[i][j] = new Contestant(i, j, contestantStrength[i][j], playgroundStub, contestantsBenchStub);
            }
        }

        /* start of the simulation */

        try {
            reposStub.initSimul(fileName, contestantStrength);
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant generator remote exception on initSimul: " + e.getMessage());
            System.exit(1);
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                contestant[i][j].start();
            }
        }

        /* waiting for the end of the simulation */

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulPar.NC; j++) {
                try {
                    contestant[i][j].join();
                } catch (InterruptedException e) {
                }
                GenericIO.writelnString("The contestant " + (i + 1) + " has terminated.");
                GenericIO.writelnString();
            }
        }

        try {
            playgroundStub.shutdown();
        } catch (RemoteException e) {
            GenericIO.writelnString("Contestant generator remote exception on Playground shutdown: " + e.getMessage());
            System.exit(1);
        }
        try {
            contestantsBenchStub.shutdown();
        } catch (RemoteException e) {
            GenericIO.writelnString(
                    "Contestant generator remote exception on ContestantsBench shutdown: " + e.getMessage());
            System.exit(1);
        }
        try {
            reposStub.shutdown();
        } catch (RemoteException e) {
            GenericIO
                    .writelnString("Contestant generator remote exception on GeneralRepos shutdown: " + e.getMessage());
            System.exit(1);
        }
    }
}
