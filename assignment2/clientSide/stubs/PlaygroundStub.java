package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 * Stub to the playground
 * <p>
 * It instantiates a remote reference to the playground.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class PlaygroundStub {
    /**
     * Name of the host where the server is located
     */
    private final String serverHostName;
    /**
     * Number of the listening port of the server
     */
    private final int serverPortNumb;

    /**
     * Playground Stub instantiation
     *
     * @param serverHostName Name of the host where the server is located
     * @param serverPortNumb Number of the listening port of the server
     */
    public PlaygroundStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    // Referee

    /**
     * Referee: startTrial
     */
    public void startTrial() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STT);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    /**
     * Referee: wait_for_trial_conclusion
     */
    public void wait_for_trial_conclusion() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.WTC);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        try {
            ((Referee) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
    }

    /**
     * Referee: assertTrialDecision
     *
     * @return position of the rope after the trial
     */
    public int assertTrialDecision() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.ATD);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        return inMessage.getPosition();
    }

    /**
     * Referee: declareGameWinner
     *
     * @return position of the rope after the match
     */
    public int declareGameWinner() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.DGW);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        try {
            ((Referee) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        return inMessage.getPosition();
    }

    // Coach

    /**
     * Coach: assemble_team
     */
    public void assemble_team() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        Coach t = (Coach) Thread.currentThread();
        int team = t.getTeam();
        outMessage = new Message(MessageType.ASTM);
        try {
            outMessage.setTeam(team);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        try {
            t.setCoachState(inMessage.getCoachState());
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
    }

    /**
     * Coach: watch_trial
     */
    public void watch_trial() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.WATL);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    // Contestant

    /**
     * Contestants: followCoachAdvice
     */
    public void followCoachAdvice() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        Contestant t = (Contestant) Thread.currentThread();
        int team = t.getTeam();
        outMessage = new Message(MessageType.FCA);
        try {
            outMessage.setTeam(team);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    /**
     * Contestants: stand_in_position
     */
    public void stand_in_position() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        Contestant t = (Contestant) Thread.currentThread();
        int team = t.getTeam();
        int number = t.getNumber();
        outMessage = new Message(MessageType.SIP);
        try {
            outMessage.setTeam(team).setNumber(number);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        try {
            t.setContestantState(inMessage.getContestantState());
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
    }

    /**
     * Contestants: getReady
     */
    public void getReady() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        Contestant t = (Contestant) Thread.currentThread();
        int team = t.getTeam();
        int number = t.getNumber();
        int strength = t.getStrength();
        outMessage = new Message(MessageType.GR);
        try {
            outMessage.setTeam(team).setNumber(number).setStrength(strength);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        try {
            t.setContestantState(inMessage.getContestantState());
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
    }

    /**
     * Contestants: amDone
     */
    public void amDone() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.AD);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    /**
     * Shutdown PlaygroundStub
     */
    public void shutdown() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.SHUT);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.SHUTDONE) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + " | Error from Playground: Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
