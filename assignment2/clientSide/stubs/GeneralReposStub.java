package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 * Stub to the general repository
 * <p>
 * It instantiates a remote reference to the general repository.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposStub {
    /**
     * Name of the host where the server is located
     */
    private final String serverHostName;
    /**
     * Number of the listening port of the server
     */
    private final int serverPortNumb;

    /**
     * General Repository Stub instantiation
     *
     * @param serverHostName name of the host where the server is located
     * @param serverPortNumb number of the listening port of the server
     */
    public GeneralReposStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * General Repository: initSimul
     *
     * @param fileName          name of the log file
     * @param contestantStength array with the contestant strength
     */
    public void initSimul(String fileName, int[][] contestantStength) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.SETNFIC);
        try {
            outMessage.setLogFileName(fileName).setContestantStrength(contestantStength);
        } catch (MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }
        com.writeObject(outMessage);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.NFICDONE) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    /**
     * General Repository: setRefereeState
     *
     * @param state the new referee state
     */
    public void setRefereeState(RefereeStates state) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STREFST);
        outMessage.setRefereeState(state);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    /**
     * General Repository: setCoachState
     *
     * @param team  the coach team number
     * @param state the new coach state
     */
    public void setCoachState(int team, CoachStates state) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STCOAST);
        try {
            outMessage.setTeam(team).setCoachState(state);
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

    /**
     * General Repository: setContestantState
     *
     * @param team   the contestant team number
     * @param number the contestant number
     * @param state  the new contestant state
     */
    public void setContestantState(int team, int number, ContestantStates state) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STCONTST);
        try {
            outMessage.setTeam(team).setNumber(number).setContestantState(state);
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

    /**
     * General Repository: setContestantStrength
     *
     * @param team     the contestant team number
     * @param number   the contestant number
     * @param strength the new contestant strength
     */
    public void setContestantStrength(int team, int number, int strength) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STCONTSTR);
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

    /**
     * General Repository: addContestant
     *
     * @param team   the contestant team number
     * @param number the contestant number
     */
    public void addContestant(int team, int number) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.ADDCONT);
        try {
            outMessage.setTeam(team).setNumber(number);
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

    /**
     * General Repository: removeContestant
     *
     * @param team   the contestant team number
     * @param number the contestant number
     */
    public void removeContestant(int team, int number) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.RMCONT);
        try {
            outMessage.setTeam(team).setNumber(number);
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

    /**
     * General Repository: setRopePosition
     *
     * @param position the new rope position
     */
    public void setRopePosition(int position) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STRP);
        outMessage.setPosition(position);
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

    /**
     * General Repository: callTrial
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
    }

    /**
     * General Repository: startGame
     */
    public void startGame() {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.STG);
        com.writeObject(outMessage);

        inMessage = (Message) com.readObject();
        if (inMessage.getMsgType() != MessageType.ACK) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }

    /**
     * General Repository: endGame
     *
     * @param position the position of the rope
     * @param knockout true if the game ended by knockout
     */
    public void endGame(int position, boolean knockout) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.EOG);
        outMessage.setPosition(position).setKnockout(knockout);
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

    /**
     * General Repository: endMatch
     *
     * @param score1 score of team 1
     * @param score2 score of team 2
     */
    public void endMatch(int score1, int score2) {
        ClientCom com;
        Message outMessage, inMessage;

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        outMessage = new Message(MessageType.EOM);
        try {
            outMessage.setScore1(score1).setScore2(score2);
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

    /**
     * Shutdown GeneralReposStub
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
