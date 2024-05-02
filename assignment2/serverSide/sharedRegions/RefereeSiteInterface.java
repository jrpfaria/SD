package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

public class RefereeSiteInterface {
    
    private final RefereeSite refereeSite;
    private RefereeSiteClientProxy t;

    public RefereeSiteInterface(RefereeSite refereeSite) {
        this.refereeSite = refereeSite;
        t = (RefereeSiteClientProxy)Thread.currentThread();
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case ANG: break;
            case INFR: break;
            case TRY: break;
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case SHUT:
                refereeSite.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
