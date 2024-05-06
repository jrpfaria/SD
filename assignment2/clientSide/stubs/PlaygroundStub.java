package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class PlaygroundStub {
    private final String serverHostName;
    private final int serverPortNumb;

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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.STT);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    public void wait_for_trial_conclusion() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.WTC);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        ((Referee) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
    }

    public int assertTrialDecision() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.ATD);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        return inMessage.getPosition();
    }

    public int declareGameWinner() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.DGW);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        ((Referee) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());

        return inMessage.getPosition();
    }

    // Coach

    public void assemble_team() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        Coach t = (Coach) Thread.currentThread();
        int team = t.getTeam();
        outMessage = new Message(MessageType.ASTM);
        outMessage.setTeam(team);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        t.setCoachState(inMessage.getCoachState());
    }

    public void watch_trial() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.WATL);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    // Contestant

    public void followCoachAdvice() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        Contestant t = (Contestant) Thread.currentThread();
        int team = t.getTeam();
        outMessage = new Message(MessageType.FCA);
        outMessage.setTeam(team);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    public void stand_in_position() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        Contestant t = (Contestant) Thread.currentThread();
        int team = t.getTeam();
        int number = t.getNumber();
        outMessage = new Message(MessageType.SIP);
        outMessage.setTeam(team).setNumber(number);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        t.setContestantState(inMessage.getContestantState());
    }

    public void getReady() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        Contestant t = (Contestant) Thread.currentThread();
        int team = t.getTeam();
        int number = t.getNumber();
        int strength = t.getStrength();
        outMessage = new Message(MessageType.GR);
        outMessage.setTeam(team).setNumber(number).setStrength(strength);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        t.setContestantState(inMessage.getContestantState());
    }

    public void amDone() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.AD);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    public void shutdown() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.SHUT);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.SHUTDONE) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
