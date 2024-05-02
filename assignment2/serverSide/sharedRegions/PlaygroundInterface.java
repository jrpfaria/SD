package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

public class PlaygroundInterface {
    
    private final Playground playground;

    public PlaygroundInterface(Playground playground) {
        this.playground = playground;
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case MessageType.STT: break;
            case MessageType.WTC: break;
            case MessageType.ATD: break;
            case MessageType.DGW: break;
            case MessageType.ASTM: break;
            case MessageType.WATL: break;
            case MessageType.FCA: break;
            case MessageType.SIP: break;
            case MessageType.GR: break;
            case MessageType.AD: break;
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case SHUT:
                playground.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
