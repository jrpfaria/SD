package serverSide.sharedRegions;

import commInfra.*;
import serverSide.entities.*;

/**
 * Interface to the Referee Site.
 * It processes the received messages and replies the corresponding ones.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class RefereeSiteInterface {
    /**
     * Reference to the Referee Site.
     */
    private final RefereeSite refereeSite;

    /**
     * Instantiation of the interface to the Referee Site.
     *
     * @param refereeSite Reference to the Referee Site.
     */
    public RefereeSiteInterface(RefereeSite refereeSite) {
        this.refereeSite = refereeSite;
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
            case ANG:
                refereeSite.announceNewGame();
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(((RefereeSiteClientProxy) Thread.currentThread()).getRefereeState());
                break;
            case TRY:
                refereeSite.teams_ready();
                outMessage = new Message(MessageType.ACK);
                break;
            case INFR:
                refereeSite.informReferee();
                outMessage = new Message(MessageType.ACK);
                break;
            case SHUT:
                refereeSite.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        return outMessage;
    }
}
