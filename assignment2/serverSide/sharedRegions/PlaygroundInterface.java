package serverSide.sharedRegions;

import commInfra.*;
import serverSide.entities.*;

/**
 * Interface to the Playground.
 * <p>
 * It processes the received messages and replies the corresponding ones.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class PlaygroundInterface {
    /**
     * Reference to the Playground.
     */
    private final Playground playground;

    /**
     * Instantiation of the interface to the Playground.
     *
     * @param playground Reference to the Playground.
     */
    public PlaygroundInterface(Playground playground) {
        this.playground = playground;
    }

    /**
     * Processing of the received messages and generation of the corresponding response.
     *
     * @param inMessage incoming message with the request
     * @return outgoing message with the reply
     * @throws MessageException if the message contains an invalid request
     */
    public Message processAndReply(Message inMessage) throws MessageException { // TODO

        Message outMessage;

        int position;
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
                position = playground.assertTrialDecision();
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
                throw new MessageException("Invalid message type!", inMessage);
        }

        return outMessage;
    }
}
