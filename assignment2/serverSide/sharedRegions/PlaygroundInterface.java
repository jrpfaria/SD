package serverSide.sharedRegions;

import commInfra.*;
import serverSide.entities.*;

public class PlaygroundInterface {

    private final Playground playground;

    public PlaygroundInterface(Playground playground) {
        this.playground = playground;
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO

        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case STT:
                break;
            case WTC:
                break;
            case ATD:
                break;
            case DGW:
                break;
            case ASTM:
                break;
            case WATL:
                break;
            case FCA:
                break;
            case SIP:
                break;
            case GR:
                break;
            case AD:
                break;
            case SHUT:
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case STT: // unsure if this is correct
                playground.startTrial();
                outMessage = new Message(MessageType.ACK);
                break;
            case WTC:
                playground.wait_for_trial_conclusion();
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(((PlaygroundClientProxy) Thread.currentThread()).getRefereeState());
            case ATD:
                int position = playground.assertTrialDecision();
                outMessage = new Message(MessageType.ACK);
                outMessage.setPosition(position);
                break;
            case DGW:
                position = playground.declareGameWinner();
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(((PlaygroundClientProxy) Thread.currentThread()).getRefereeState()).setPosition(position);
                break;
            case ASTM:
                ((PlaygroundClientProxy) Thread.currentThread()).setCoachTeam(inMessage.getTeam());
                playground.assemble_team();
                outMessage = new Message(MessageType.ACK);
                outMessage.setCoachState(((PlaygroundClientProxy) Thread.currentThread()).getCoachState());
                break;
            case WATL:
                playground.watch_trial();
                outMessage = new Message(MessageType.ACK);
                break;
            case FCA:
                ((PlaygroundClientProxy) Thread.currentThread()).setContestantTeam(inMessage.getTeam());
                playground.followCoachAdvice();
                outMessage = new Message(MessageType.ACK);
                break;
            case SIP:
                ((PlaygroundClientProxy) Thread.currentThread()).setContestantTeam(inMessage.getTeam());
                ((PlaygroundClientProxy) Thread.currentThread()).setContestantNumber(inMessage.getNumber());
                playground.stand_in_position();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(((PlaygroundClientProxy) Thread.currentThread()).getContestantState());
                break;
            case GR:
                ((PlaygroundClientProxy) Thread.currentThread()).setContestantTeam(inMessage.getTeam());
                ((PlaygroundClientProxy) Thread.currentThread()).setContestantNumber(inMessage.getNumber());
                ((PlaygroundClientProxy) Thread.currentThread()).setContestantStrength(inMessage.getStrength());
                playground.getReady();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(((PlaygroundClientProxy) Thread.currentThread()).getContestantState());
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
