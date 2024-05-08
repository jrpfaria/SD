package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 * RefereeSite Stub
 *      It instantiates a remote reference to the RefereeSite
 *      Implementation of a client-server model of type 2 (server replication)
 *      Communication is based on a communication channel using the TCP protocol
 */
public class RefereeSiteStub {
    /**
     * Name of the host where the server is located
     */
    private final String serverHostName;
    /**
     * Number of the listening port of the server
     */
    private final int serverPortNumb;

    /**
     * RefereeSite Stub instantiation
     * @param serverHostName Name of the host where the server is located
     * @param serverPortNumb Number of the listening port of the server
     */
    public RefereeSiteStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    // Referee
    /**
     * Referee: announceNewGame
     */
    public void announceNewGame() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.ANG);
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
     * Referee: teams_ready
     */
    public void teams_ready() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.TRY);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    // Coach
    /**
     * Coach: informReferee
     */
    public void informReferee() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.INFR);
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
     * Shutdown RefereeSiteStub
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
