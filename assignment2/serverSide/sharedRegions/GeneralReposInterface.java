package serverSide.sharedRegions;

import commInfra.*;

/**
 * Interface to the General Repository.
 * <p>
 * It processes the received messages and replies the corresponding ones.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposInterface {
    /**
     * Reference to the General Repository.
     */
    private final GeneralRepos repos;

    /**
     * Instantiation of the interface to the General Repository.
     *
     * @param repos Reference to the General Repository.
     */
    public GeneralReposInterface(GeneralRepos repos) {
        this.repos = repos;
    }

    /**
     * Processing of the received messages and generation of the corresponding response.
     *
     * @param inMessage incoming message with the request
     * @return outgoing message with the reply
     * @throws MessageException if the message contains an invalid request
     */
    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage;

        switch (inMessage.getMsgType()) {
            case SETNFIC:
                repos.initSimul(inMessage.getLogFileName(), inMessage.getContestantStrength());
                outMessage = new Message(MessageType.NFICDONE);
                break;
            case STREFST:
                repos.setRefereeState(inMessage.getRefereeState());
                outMessage = new Message(MessageType.ACK);
                break;
            case STCOAST:
                repos.setCoachState(inMessage.getTeam(), inMessage.getCoachState());
                outMessage = new Message(MessageType.ACK);
                break;
            case STCONTST:
                repos.setContestantState(inMessage.getTeam(), inMessage.getNumber(), inMessage.getContestantState());
                outMessage = new Message(MessageType.ACK);
                break;
            case STCONTSTR:
                repos.setContestantStrength(inMessage.getTeam(), inMessage.getNumber(), inMessage.getStrength());
                outMessage = new Message(MessageType.ACK);
                break;
            case ADDCONT:
                repos.addContestant(inMessage.getTeam(), inMessage.getNumber());
                outMessage = new Message(MessageType.ACK);
                break;
            case RMCONT:
                repos.removeContestant(inMessage.getTeam(), inMessage.getNumber());
                outMessage = new Message(MessageType.ACK);
                break;
            case STRP:
                repos.setRopePosition(inMessage.getPosition());
                outMessage = new Message(MessageType.ACK);
                break;
            case CLT:
                repos.callTrial();
                outMessage = new Message(MessageType.ACK);
                break;
            case STG:
                repos.startGame();
                outMessage = new Message(MessageType.ACK);
                break;
            case EOG:
                repos.endGame(inMessage.getPosition(), inMessage.isKnockout());
                outMessage = new Message(MessageType.ACK);
                break;
            case EOM:
                repos.endMatch(inMessage.getScore1(), inMessage.getScore2());
                outMessage = new Message(MessageType.ACK);
                break;
            case SHUT:
                repos.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        return outMessage;
    }
}
