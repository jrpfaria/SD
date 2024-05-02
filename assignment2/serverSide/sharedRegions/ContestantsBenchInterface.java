package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

public class ContestantsBenchInterface {
    
    private final ContestantsBench contestantsBench;

    public ContestantsBenchInterface(ContestantsBench contestantsBench) {
        this.contestantsBench = contestantsBench;
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case MessageType.CLT:
                break;
            case MessageType.DMW:
                break;
            case MessageType.RVN:
                break;
            case MessageType.WFRC:
                break;
            case MessageType.CLC:
                break;
            case MessageType.SAB:
                break;
            case MessageType.SD:
                break;            
            case SHUT:
                contestantsBench.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
