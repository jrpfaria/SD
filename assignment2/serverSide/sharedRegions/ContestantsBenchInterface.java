package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

public class ContestantsBenchInterface {
    
    private final ContestantsBench contestantsBench;
    private ContestantsBenchClientProxy t;

    public ContestantsBenchInterface(ContestantsBench contestantsBench) {
        this.contestantsBench = contestantsBench;
        t = (ContestantsBenchClientProxy)Thread.currentThread();
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case CLT:
                break;
            case DMW:
                contestantsBench.declareMatchWinner(inMessage.getScore1(), inMessage.getScore2());
                outMessage = new Message(MessageType.ACK, t.getContestantState());
                break;
            case RVN:
                break;
            case WFRC:
                break;
            case CLC:
                break;
            case SAB:
                break;
            case SD:
                break;            
            case SHUT:
                contestantsBench.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
