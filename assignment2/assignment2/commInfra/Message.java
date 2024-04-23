package commInfra;

import clientSide.entities.*;
import java.io.*;

import genclass.GenericIO;

public class Message implements Serializable { // TODO
    private static final long serialVersionUID = 2021L;
    
    private MessageType msgType;
    private int team;
    private int number;

    public Message(MessageType msgType) {
        this.msgType = msgType;
    }

    public Message(MessageType msgType, int team) {
        this.msgType = msgType;
        this.team = team;
    }

    public Message(MessageType msgType, int team, int number) {
        this.msgType = msgType;
        this.team = team;
        this.number = number;
    }

    public MessageType getMsgType() {
        return this.msgType;
    }
}
