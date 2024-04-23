package serverSide.entities;

import serverSide.sharedRegions.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class GeneralReposClientProxy extends Thread {
    
    private static int nProxy = 0;
    private ServerCom sconi;
    private GeneralReposInterface reposInter;
    
    public GeneralReposClientProxy(ServerCom sconi, GeneralReposInterface reposInter) {
        super("GeneralRepos_" + GeneralReposClientProxy.getProxyId());
        this.sconi = sconi;
        this.reposInter = reposInter;
    }

    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {cl = Class.forName("serverSide.entities.GeneralReposClientProxy");}
        catch (ClassNotFoundException e) {
            GenericIO.writelnString("Data type GeneralReposClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized(cl) {
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }

    @Override
    public void run() {
        Message inMessage = null, outMessage = null;

        inMessage = (Message)sconi.readObject();

        try {outMessage = reposInter.processAndReply(inMessage);}
        catch (MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        sconi.writeObject(outMessage);
        sconi.close();
    }
}
