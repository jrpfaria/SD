package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class PlaygroundStub {
    private String serverHostName;
    private int serverPortNumb;

    public PlaygroundStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    // Referee

    public void startTrial() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STT);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void wait_for_trial_conclusion() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.WTC);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public int assertTrialDecision() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.ATD);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        return inMessage.getValue();
    }

    public int declareGameWinner() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.DGW);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        return inMessage.getValue();
    }

    // Coach

    public void assemble_team(int team) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.ASTM, team);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void watch_trial(int team) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.WATL, team);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    // Contestant

    public void followCoachAdvice(int team, int number) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.FCA, team, number);
        com.writeObject(outMessage);

        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
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
