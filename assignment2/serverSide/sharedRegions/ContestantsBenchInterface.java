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
                outMessage = new Message(MessageType.ACK, t.getRefereeState());
                break;
            case DMW:
                contestantsBench.declareMatchWinner(inMessage.getScore1(), inMessage.getScore2());
                outMessage = new Message(MessageType.ACK, t.getContestantState());
                break;
            case RVN:
                t.setTeam(inMessage.getTeam());
                Pair<Integer, Integer> r = contestantsBench.reviewNotes();
                outMessage = new Message(MessageType.ACK, r);
                break;
            case WFRC:
                t.setTeam(inMessage.getTeam());
                r = contestantsBench.wait_for_referee_command();
                outMessage = new Message(MessageType.ACK, t.getCoachState(), r);
                break;
            case CLC:
                t.setTeam(inMessage.getTeam());
                contestantsBench.callContestants(t.getRoster());
                outMessage = new Message(MessageType.ACK);
                break;
            case SAB: // unsure if this is correct, might be missing something
                t.setTeam(inMessage.getTeam());
                t.setNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                int r = contestantsBench.seatAtBench();
                outMessage = new Message(MessageType.ACK, t.getContestantStrength(), r);
                break;
            case SD:
                t.setTeam(inMessage.getTeam());
                t.setNumber(inMessage.getNumber());
                t.setContestantStrength(inMessage.getStrength());
                contestantsBench.seatDown();
                outMessage = new Message(MessageType.ACK, t.getContestantStrength());
                break;            
            case SHUT:
                contestantsBench.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
        }

        return outMessage;
    }
}
