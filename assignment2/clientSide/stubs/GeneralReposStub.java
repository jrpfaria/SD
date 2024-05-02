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

    public void setRefereeState(RefereeStates state) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STREFST, state);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void setCoachState(int team, CoachStates state) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STCOAST, team, state);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void setContestantState(int team, int number, ContestantStates state) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STCONTST, team, number, state);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void setContestantStrength(int team, int number, int strength) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STCONTSTR, team, number, strength);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void addContestant(int team, int number) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.ADDCONT, team, number);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void removeContestant(int team, int number) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.RMCONT, team, number);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void setRopePosition(int position) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STRP, position);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void callTrial() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.CLT);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void startGame() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.STG);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void endGame(int position, boolean knockout) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.EOG, position, knockout);
        com.writeObject(outMessage);
        
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType()!=MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
    }

    public void endMatch(int score1, int score2) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {Thread.sleep((long)1000);}
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.EOM, score1, score2);
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
