package serverSide.entities;

import commInfra.*;
import genclass.GenericIO;
import serverSide.sharedRegions.*;

/**
 * Service provider agent for access to the General Repository shared region.
 *     Implementation of the client-server model type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposClientProxy extends Thread {
    /**
     * Number of instances of the General Repository client proxy.
     */
    private static int nProxy = 0;
    /**
     * Communication channel.
     */
    private final ServerCom sconi;
    /**
     * General Repository Interface.
     */
    private final GeneralReposInterface reposInter;

    /**
     * Instantiation of the General Repository client proxy.
     * @param sconi Communication channel
     * @param reposInter General Repository Interface
     */
    public GeneralReposClientProxy(ServerCom sconi, GeneralReposInterface reposInter) {
        super("GeneralRepos_" + GeneralReposClientProxy.getProxyId());
        this.sconi = sconi;
        this.reposInter = reposInter;
    }

    /**
     * Generation of the instantiation id.
     * @return Instantiation id
     */
    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("serverSide.entities.GeneralReposClientProxy");
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString("Data type GeneralReposClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (cl) {
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }

    /**
     * Life cycle of the service provider agent.
     */
    @Override
    public void run() {
        Message inMessage, outMessage = null;

        inMessage = (Message) sconi.readObject();

        try {
            GenericIO.writelnString("Thread " + getName() + " received: " + inMessage + "!");
            outMessage = reposInter.processAndReply(inMessage);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        sconi.writeObject(outMessage);
        GenericIO.writelnString("Thread " + getName() + " replied: " + outMessage + "!");
        //sconi.close();
    }
}
