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
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        t = (ContestantsBenchClientProxy)Thread.currentThread();
        
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case CLT: break;
            case DMW: break;
            case RVN: break;
            case WFRC: break;
            case CLC: break;
            case SAB: break;
            case SD: break;
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case CLT:
                contestantsBench.callTrial();
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(t.getRefereeState());
                break;
            case DMW:
                contestantsBench.declareMatchWinner(inMessage.getScore1(), inMessage.getScore2());
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(t.getContestantState());
                break;
            case RVN:
                t.setCoachTeam(inMessage.getTeam());
                Pair<Integer, Integer>[] roster = contestantsBench.reviewNotes();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestants(roster);
                break;
            case WFRC:
                t.setCoachTeam(inMessage.getTeam());
                int orders = contestantsBench.wait_for_referee_command();
                outMessage = new Message(MessageType.ACK);
                outMessage.setCoachState(t.getCoachState()).setValue(orders);
                break;
            case CLC:
                t.setCoachTeam(inMessage.getTeam());
                contestantsBench.callContestants(inMessage.getRoster());
                outMessage = new Message(MessageType.ACK);
                break;
            case SAB: // unsure if this is correct, might be missing something
                t.setContestantTeam(inMessage.getTeam());
                t.setContestantNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                orders = contestantsBench.seat_at_the_bench();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(t.getContestantState()).setValue(orders);
                break;
            case SD:
                t.setContestantTeam(inMessage.getTeam());
                t.setContestantNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                contestantsBench.seatDown();
                outMessage = new Message(MessageType.ACK);
                break;            
            case SHUT:
                contestantsBench.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
            default:
                outMessage = new Message(MessageType.ERR);
        }

        return outMessage;
    }
}
