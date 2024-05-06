package serverSide.sharedRegions;

import clientSide.entities.*;
import commInfra.*;
import serverSide.entities.*;
import serverSide.main.*;

public class RefereeSiteInterface {

    private final RefereeSite refereeSite;
    private RefereeSiteClientProxy t;

    public RefereeSiteInterface(RefereeSite refereeSite) {
        this.refereeSite = refereeSite;
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO

        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case ANG:
                break;
            case TRY:
                break;
            case INFR:
                break;
            case SHUT:
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

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
                outMessage = new Message(MessageType.ERR);
        }

        return outMessage;
    }
}
