package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class GeneralReposStub {
    private String serverHostName;
    private int serverPortNumb;

    public GeneralReposStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    public void initSimul(String fileName, int[][] contestantStength) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.SETNFIC, fileName, contestantStength);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.NFICDONE) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void shutdown() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.SHUT);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.SHUTDONE) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }
}
