package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

public class GeneralReposInterface {
    
    private final GeneralRepos repos;

    public GeneralReposInterface(GeneralRepos repos) {
        this.repos = repos;
    }

    public Message processAndReply(Message inMessage) throws MessageException { // TODO
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case SETNFIC: break;
            case NFICDONE: break;
            case STREFST: break;
            case STCOAST: break;
            case STCONTST: break;
            case STCONTSTR: break;
            case ADDCONT: break;
            case RMCONT: break;
            case STRP: break;
            case STG: break;
            case EOG: break;
            case EOM: break;
            case SHUT: break;
            default: throw new MessageException("Invalid message type!", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case SHUT:
                repos.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);
                break;
            default:
                outMessage = new Message(MessageType.ERR);
        }

        return outMessage;
    }
}
