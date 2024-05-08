package serverSide.sharedRegions;

import commInfra.*;
import serverSide.entities.*;

/**
 * Interface for the Contestants Bench shared region.
 * It processes the received messages and replies the corresponding ones.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class ContestantsBenchInterface {
    /**
     * Reference to the contestants Bench.
     */
    private final ContestantsBench contestantsBench;

    /**
     * Instantiation of the interface to the Contestants Bench.
     * @param contestantsBench Reference to the Contestants Bench.
     */
    public ContestantsBenchInterface(ContestantsBench contestantsBench) {
        this.contestantsBench = contestantsBench;
    }

    /**
     * Processing of the received messages and generation of the corresponding response.
     * @param inMessage incoming message with the request
     * @return outgoing message with the reply
     * @throws MessageException if the message contains an invalid request
     */
    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage;

        switch (inMessage.getMsgType()) {
            case CLT:
                break;
            case DMW:
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
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        int orders;
        switch (inMessage.getMsgType()) {
            case CLT:
                contestantsBench.callTrial();
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(((ContestantsBenchClientProxy) Thread.currentThread()).getRefereeState());
                break;
            case DMW:
                contestantsBench.declareMatchWinner(inMessage.getScore1(), inMessage.getScore2());
                outMessage = new Message(MessageType.ACK);
                outMessage.setRefereeState(((ContestantsBenchClientProxy) Thread.currentThread()).getRefereeState());
                break;
            case RVN:
                ((ContestantsBenchClientProxy) Thread.currentThread()).setCoachTeam(inMessage.getTeam());
                Pair<Integer, Integer>[] roster = contestantsBench.reviewNotes();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestants(roster);
                break;
            case WFRC:
                ((ContestantsBenchClientProxy) Thread.currentThread()).setCoachTeam(inMessage.getTeam());
                orders = contestantsBench.wait_for_referee_command();
                outMessage = new Message(MessageType.ACK);
                outMessage.setCoachState(((ContestantsBenchClientProxy) Thread.currentThread()).getCoachState()).setValue(orders);
                break;
            case CLC:
                ((ContestantsBenchClientProxy) Thread.currentThread()).setCoachTeam(inMessage.getTeam());
                contestantsBench.callContestants(inMessage.getRoster());
                outMessage = new Message(MessageType.ACK);
                break;
            case SAB: // unsure if this is correct, might be missing something
                ((ContestantsBenchClientProxy) Thread.currentThread()).setContestantTeam(inMessage.getTeam());
                ((ContestantsBenchClientProxy) Thread.currentThread()).setContestantNumber(inMessage.getNumber());
                ((ContestantsBenchClientProxy) Thread.currentThread()).setContestantStrength(inMessage.getStrength());
                if (((ContestantsBenchClientProxy) Thread.currentThread()).getContestantTeam() != inMessage.getTeam()) {
                    throw new MessageException("ERROR: different team", inMessage);
                }
                if (((ContestantsBenchClientProxy) Thread.currentThread()).getContestantNumber() != inMessage.getNumber()) {
                    throw new MessageException("ERROR: different number", inMessage);
                }
                orders = contestantsBench.seat_at_the_bench();
                outMessage = new Message(MessageType.ACK);
                outMessage.setContestantState(((ContestantsBenchClientProxy) Thread.currentThread()).getContestantState()).setValue(orders);
                break;
            case SD:
                ((ContestantsBenchClientProxy) Thread.currentThread()).setContestantTeam(inMessage.getTeam());
                ((ContestantsBenchClientProxy) Thread.currentThread()).setContestantNumber(inMessage.getNumber());
                ((ContestantsBenchClientProxy) Thread.currentThread()).setContestantStrength(inMessage.getStrength());
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
