package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 * Stub to the contestants bench
 * <p>
 * It instantiates a remote reference to the contestants bench.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ContestantsBenchStub {
    /**
     * Name of the host where the server is located
     */
    private final String serverHostName;
    /**
     * Number of the listening port of the server
     */
    private final int serverPortNumb;

    /**
     * ContestantsBench Stub instantiation
     *
     * @param serverHostName Name of the host where the server is located
     * @param serverPortNumb Number of the listening port of the server
     */
    public ContestantsBenchStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    // Referee

    /**
     * Referee: callTrial
     */
    public void callTrial() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.CLT);
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

    /**
     * Referee: declareMatchWinner
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public void declareMatchWinner(int score1, int score2) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.DMW);
        try {
            outMessage.setScore1(score1).setScore2(score2);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
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

    // Coach

    /**
     * Coach: reviewNotes
     *
     * @return Array with all the contestants' numbers and their strength
     */
    public Pair<Integer, Integer>[] reviewNotes() {
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
        outMessage = new Message(MessageType.RVN);
        try {
            outMessage.setTeam(team);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        return inMessage.getContestants();
    }

    /**
     * Coach: wait_for_referee_command
     *
     * @return Referee command
     */
    public int wait_for_referee_command() {
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
        outMessage = new Message(MessageType.WFRC);
        try {
            outMessage.setTeam(team);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        t.setCoachState(inMessage.getCoachState());

        return inMessage.getValue();
    }

    /**
     * Coach: callContestants
     *
     * @param roster Array with the contestants' numbers
     */
    public void callContestants(int[] roster) {
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
        outMessage = new Message(MessageType.CLC);
        try {
            outMessage.setTeam(team).setRoster(roster);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    // Contestants

    /**
     * Contestants: seat_at_the_bench
     *
     * @return Orders to execute by the contestant
     */
    public int seat_at_the_bench() {
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
        outMessage = new Message(MessageType.SAB);
        try {
            outMessage.setTeam(team).setNumber(number).setStrength(strength);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();

        ((Contestant) Thread.currentThread()).setContestantState(inMessage.getContestantState());

        return inMessage.getValue();
    }

    /**
     * Contestants: seatDown
     */
    public void seatDown() {
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
        outMessage = new Message(MessageType.SD);
        try {
            outMessage.setTeam(team).setNumber(number).setStrength(strength);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    //

    /**
     * Shutdown ContestantsBenchStub
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
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
