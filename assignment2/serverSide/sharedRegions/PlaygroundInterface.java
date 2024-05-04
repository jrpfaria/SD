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
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        t = (PlaygroundClientProxy)Thread.currentThread();

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
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(t.getRefereeState());
            case ATD:
                int position = playground.assertTrialDecision();
                outMessage = new Message(MessageType.ACK);
                outMessage.setPosition(position);
                break;
            case DGW:
                position = playground.declareGameWinner();
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(t.getRefereeState()).setPosition(position);
                break;
            case ASTM:
                t.setCoachTeam(inMessage.getTeam());
                playground.assemble_team();
                outMessage = new Message(MessageType.ACK);
                outMessage.setCoachState(t.getCoachState());
                break;
            case WATL:
                playground.watch_trial();
                outMessage = new Message(MessageType.ACK);
                break;
            case FCA:
                t.setContestantTeam(inMessage.getTeam());
                playground.followCoachAdvice();
                outMessage = new Message(MessageType.ACK);
                break;
            case SIP:
                t.setContestantTeam(inMessage.getTeam());
                t.setContestantNumber(inMessage.getNumber());
                playground.stand_in_position();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(t.getContestantState());
                break;
            case GR:
                t.setContestantTeam(inMessage.getTeam());
                t.setContestantNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                playground.getReady();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(t.getContestantState());
                break;
            case AD:
                playground.amDone();
                outMessage = new Message(MessageType.ACK);
                break;
            case SHUT:
                playground.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
            default:
                outMessage = new Message(MessageType.ERR);
        }

        return outMessage;
    }
}
