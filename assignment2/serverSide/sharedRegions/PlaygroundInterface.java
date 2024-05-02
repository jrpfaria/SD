package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

public class PlaygroundInterface {
    
    private final Playground playground;
    private PlaygroundClientProxy t;

    public PlaygroundInterface(Playground playground) {
        this.playground = playground;
        t = (PlaygroundClientProxy)Thread.currentThread();
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case STT: break;
            case WTC: break;
            case ATD: break;
            case DGW: break;
            case ASTM: break;
            case WATL: break;
            case FCA: break;
            case SIP: break;
            case GR: break;
            case AD: break;
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case DGW:
                int r = playground.declareGameWinner();
                outMessage = new Message(MessageType.ACK, t.getContestantState(), r);
            case GR:
                t.setContestantTeam(inMessage.getTeam());
                t.setContestantNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                playground.getReady();
                outMessage = new Message(MessageType.ACK, t.getContestantState());
            case AD:
                playground.amDone();
                outMessage = new Message(MessageType.ACK);
            case SHUT:
                playground.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
