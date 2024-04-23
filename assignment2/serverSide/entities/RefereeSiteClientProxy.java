package serverSide.entities;

import serverSide.sharedRegions.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class RefereeSiteClientProxy extends Thread { // TODO
    
    private static int nProxy = 0;
    private ServerCom sconi;
    private RefereeSiteInterface refereeSiteInter;
    
    public RefereeSiteClientProxy(ServerCom sconi, RefereeSiteInterface refereeSiteInter) {
        super("RefereeSite_" + RefereeSiteClientProxy.getProxyId());
        this.sconi = sconi;
        this.refereeSiteInter = refereeSiteInter;
    }

    private static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {cl = Class.forName("serverSide.entities.RefereeSiteClientProxy");}
        catch (ClassNotFoundException e) {
            GenericIO.writelnString("Data type RefereeSiteClientProxy was not found!");
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

        try {outMessage = refereeSiteInter.processAndReply(inMessage);}
        catch (MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        sconi.writeObject(outMessage);
        sconi.close();
    }
}
