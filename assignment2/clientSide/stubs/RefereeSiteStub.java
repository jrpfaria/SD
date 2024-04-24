package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class RefereeSiteStub {
    private String serverHostName;
    private int serverPortNumb;

    public RefereeSiteStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    // Referee

    public void announceNewGame() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)10);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.ANG);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void teams_ready() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)10);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.TRY);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    // Coach
    public void informReferee() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)10);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.INFR);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    //

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
