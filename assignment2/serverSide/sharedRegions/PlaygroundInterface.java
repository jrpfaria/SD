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
            case STT: // unsure if this is correct
                playground.startTrial();
                outMessage = new Message(MessageType.ACK);
                break;
            case WTC:
                playground.wait_for_trial_conclusion();
                outMessage = new Message(MessageType.ACK, t.getRefereeState());
            case ATD:
                int r = playground.assertTrialDecision();
                outMessage = new Message(MessageType.ACK, r);
                break;
            case DGW:
                int r = playground.declareGameWinner();
                outMessage = new Message(MessageType.ACK, t.getRefereeState(), r);
                break;
            case ASTM:
                t.setTeam(inMessage.getTeam());
                playground.assemble_team();
                outMessage = new Message(MessageType.ACK, t.getCoachState());
                break;
            case WATL:
                playground.waitForTrial();
                outMessage = new Message(MessageType.ACK);
                break;
            case FCA:
                t.setTeam(inMessage.getTeam());
                plaground.followCoachAdvice();
                outMessage = new Message(MessageType.ACK);
                break;
            case SIP:
                t.setTeam(inMessage.getTeam());
                t.setNumber(inMessage.getNumber());
                plaground.stand_in_position();
                outMessage = new Message(MessageType.ACK, t.getContestantState());
                break;
            case GR:
                t.setTeam(inMessage.getTeam());
                t.setNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                playground.getReady();
                outMessage = new Message(MessageType.ACK, t.getContestantState());
                break;
            case AD:
                playground.amDone();
                outMessage = new Message(MessageType.ACK);
                break;
            case SHUT:
                playground.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
